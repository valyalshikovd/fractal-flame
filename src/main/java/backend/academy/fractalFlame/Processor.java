package backend.academy.fractalFlame;

import backend.academy.fractalFlame.simmetryParameter.RadialSymmetryParam;
import backend.academy.fractalFlame.simmetryParameter.SymmetryParam;
import backend.academy.fractalFlame.transformation.AffineTransformation;
import backend.academy.fractalFlame.transformation.TransformFactory;
import backend.academy.fractalFlame.transformation.Transformation;
import backend.academy.fractalFlame.transformation.TransformationDTO;
import backend.academy.fractalFlame.util.RandomShell;
import backend.academy.fractalFlame.util.RandomShellImpl;
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

public class Processor {

    private final Plot plot;
    private final Renderer renderer;
    private final RandomShell shell = new RandomShellImpl();
    private final List<Point> points = new ArrayList<>();
    private final List<Transformation> affineTransformations = new ArrayList<>();
    private final List<Transformation> transformations = new ArrayList<>();
    private final double MAX_X;
    private final double MAX_Y;
    private int countThreads = 1;
    private SymmetryParam symmetryParam;
    private String file;
    private String path;
    private final int numIterations;

    private Processor(Plot plot, double maxX, double maxY, int countThreads, Renderer renderer, String file, int numIterations) {
        this.plot = plot;
        MAX_X = maxX;
        MAX_Y = maxY;
        this.countThreads = countThreads;
        this.renderer = renderer;
        this.file = file;
        this.numIterations = numIterations;
    }

    public void getStartedPoint(int count) {

        for (int i = 0; i < count; i++) {
            points.add(Point.genRandomPoint(MAX_X, MAX_Y, shell));
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
        List<ForkJoinTask<?>> tasks = new ArrayList<>();

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

                        Color currentColor = affineTransformation.getColor();
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

        private int pictureSizeX = 1000;
        private int pictureSizeY = 1000;
        private int numberOfThreads = 1;
        private int symmetryParam = 1;
        private int numStartedPoints = 1000;
        private int numAffineTransform = 20;
        private int numTransformation = 300000;
        private TransformationDTO[] nonLinearTransforms;
        private final int borderCalculatingAreaX = 1;
        private final int borderCalculatingAreaY = 1;
        private final String fileName;
        private final String path;

        public Processor createProcessor() {

            Plot plot = new Plot(pictureSizeX, pictureSizeY, numberOfThreads);
            Processor processor = new Processor(
                plot,
                borderCalculatingAreaX,
                borderCalculatingAreaY,
                numberOfThreads,
                new RendererImpl(),
                fileName,
                numTransformation
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
