package backend.academy.fractalFlame;

import backend.academy.fractalFlame.correction.Correction;
import backend.academy.fractalFlame.simmetryParameter.RadialSymmetryParam;
import backend.academy.fractalFlame.simmetryParameter.SymmetryParam;
import backend.academy.fractalFlame.transformation.AffineTransformation;
import backend.academy.fractalFlame.transformation.TransformFactory;
import backend.academy.fractalFlame.transformation.Transformation;
import backend.academy.fractalFlame.transformation.TransformationDTO;
import backend.academy.fractalFlame.util.RandomShell;
import backend.academy.fractalFlame.util.RandomShellImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

@SuppressWarnings("ParameterNumber")
@SuppressFBWarnings("PREDICTABLE_RANDOM")
public final class Processor {

    private final Plot plot;
    private final Renderer renderer;
    private final RandomShell shell = new RandomShellImpl();
    private final List<Point> points = new ArrayList<>();
    private final List<Transformation> affineTransformations = new ArrayList<>();
    private final List<Transformation> transformations = new ArrayList<>();
    private final double maxX;
    private final double maxY;
    private int countThreads = 1;
    private SymmetryParam symmetryParam;
    private String file;
    private String path;
    private final int numIterations;

    private Processor(
        Plot plot, double maxX,
        double maxY, int countThreads,
        Renderer renderer, String file,
        int numIterations, String path) {
        this.plot = plot;
        this.maxX = maxX;
        this.maxY = maxY;
        this.countThreads = countThreads;
        this.renderer = renderer;
        this.file = file;
        this.path = path;
        this.numIterations = numIterations;
    }

    public void getStartedPoint(int count) {

        for (int i = 0; i < count; i++) {
            points.add(Point.genRandomPoint(maxX, maxY, shell));
        }
    }

    public void genAffineTransformation(int count) {
        for (int i = 0; i < count; i++) {
            affineTransformations.add(AffineTransformation.genRandomAffineTransformation(shell));
        }
    }

    public void addTransformation(Transformation transformation) {
        transformations.add(transformation);
    }

    public void addSymmetryParam(SymmetryParam symmetryParam) {
        this.symmetryParam = symmetryParam;
    }

    public void applyTransformations() {

        ForkJoinPool forkJoinPool = new ForkJoinPool(countThreads);
        List<ForkJoinTask<?>> tasks = new ArrayList<>(points.size());

        for (int i = 0; i < points.size(); i++) {
            int finalI = i;
            ForkJoinTask<?> task = forkJoinPool.submit(() -> {
                for (int j = 0; j < numIterations; j++) {
                    try {
                        AffineTransformation affineTransformation =
                            (AffineTransformation) getRandomTransformation(affineTransformations);
                        points.get(finalI).position(affineTransformation.transform(points.get(finalI).position()));

                        Transformation transformation = getRandomTransformation(transformations);
                        points.get(finalI).position(transformation.transform(points.get(finalI).position()));

                        Color currentColor = affineTransformation.color();
                        plot.getPoint(
                            (int) (plot.toFullX(points.get(finalI).position().getX())),
                            (int) (plot.toFullY(points.get(finalI).position().getY()))
                        ).addHit(currentColor);

                        if (this.symmetryParam != null) {

                            Vector2D[] newPoses = symmetryParam.applySymmetry(points.get(finalI).position());

                            Arrays.stream(newPoses).forEach(
                                (v) -> {
                                    plot.getPoint(
                                        (int) (plot.toFullX(v.getX())),
                                        (int) (plot.toFullY(v.getY()))
                                    ).addHit(currentColor);
                                }
                            );

                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            });
            tasks.add(task);
        }



        for (ForkJoinTask<?> task : tasks) {
            task.join();
        }

        forkJoinPool.shutdown();
        try {
            if (!forkJoinPool.awaitTermination(1, TimeUnit.MINUTES)) {
                System.err.println("ForkJoinPool не завершил работу за отведенное время!");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void render() {
        renderer.render(plot, file, path);
    }

    private Transformation getRandomTransformation(List<Transformation> transformationList) {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        double totalWeight = transformationList.stream().mapToDouble(Transformation::getWeight).sum();
        double[] cumulativeWeights = new double[transformationList.size()];
        cumulativeWeights[0] = transformationList.getFirst().getWeight() / totalWeight;
        for (int i = 1; i < cumulativeWeights.length; i++) {
            cumulativeWeights[i] = cumulativeWeights[i - 1] + transformationList.get(i).getWeight() / totalWeight;
        }

        double randomValue = random.nextDouble();
        int index = 0;
        while (index < cumulativeWeights.length && randomValue > cumulativeWeights[index]) {
            index++;
        }

        return transformationList.get(index);
    }


    @Builder
    public static class ProcessorConfiguration {

        private int pictureSizeX;
        private int pictureSizeY;
        private int numberOfThreads;
        private int symmetryParam;
        private int numStartedPoints;
        private int numAffineTransform;
        private int numTransformation;
        private TransformationDTO[] nonLinearTransforms;
        private final int borderCalculatingAreaX = 1;
        private final int borderCalculatingAreaY = 1;
        private final String fileName;
        private final String path;
        private final int numRenderThreads;
        private final Correction correction;

        public Processor createProcessor() {

            Plot plot = new Plot(pictureSizeX, pictureSizeY, numberOfThreads);
            Processor processor = new Processor(
                plot,
                borderCalculatingAreaX,
                borderCalculatingAreaY,
                numberOfThreads,
                new RendererImpl(
                    new ImageCreatorImpl(numRenderThreads, correction),
                    new DefaultPngImageWriter()),
                fileName,
                numTransformation,
                path
            );
            processor.genAffineTransformation(numAffineTransform);

            if (symmetryParam > 1) {
                processor.addSymmetryParam(new RadialSymmetryParam(symmetryParam));
            }

            for (TransformationDTO transformationDTO : nonLinearTransforms) {
                Transformation transformation = TransformFactory.getTransform(transformationDTO);
                if (transformation == null) {
                    continue;
                }
                processor.addTransformation(transformation);
            }

            processor.getStartedPoint(numStartedPoints);
            return processor;
        }

    }

}
