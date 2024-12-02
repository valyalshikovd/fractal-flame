package backend.academy.fractalFlame;

import backend.academy.fractalFlame.simmetryParameter.SymmetryParam;
import backend.academy.fractalFlame.transformation.AffineTransformation;
import backend.academy.fractalFlame.transformation.Transformation;
import backend.academy.fractalFlame.util.RandomShell;
import backend.academy.fractalFlame.util.RandomShellImpl;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Processor {

    private final Plot plot;
    private final Renderer renderer = new RendererImpl();
    private final RandomShell shell = new RandomShellImpl();
    private final List<Point> points = new ArrayList<>();
    private final List<Transformation> affineTransformations = new ArrayList<>();
    private final List<Transformation> transformations = new ArrayList<>();
    private final double MAX_X;
    private final double MAX_Y;
    private int countThreads = 1;
    private SymmetryParam symmetryParam;

    public Processor(Plot plot, double maxX, double maxY, int countThreads) {
        this.plot = plot;
        MAX_X = maxX;
        MAX_Y = maxY;
        this.countThreads = countThreads;
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

    public void applyTransformations(int numIterations) {

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
                            for (Vector2D v : newPoses) {
                                plot.getPoint(
                                    (int) (plot.toFullX(v.getX())),
                                    (int) (plot.toFullY(v.getY()))
                                ).addHit(currentColor);
                            }

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

    public void render(String file) {
        renderer.render(plot, file);
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

}
