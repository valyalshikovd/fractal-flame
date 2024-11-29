package backend.academy.fractalFlame;

import backend.academy.fractalFlame.transformation.AffineTransformation;
import backend.academy.fractalFlame.transformation.Transformation;
import backend.academy.fractalFlame.util.RandomShell;
import backend.academy.fractalFlame.util.RandomShellImpl;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Processor {

    private final Plot plot;
    private final Renderer renderer = new RendererImpl();
    private final RandomShell shell = new RandomShellImpl();
    private final List<Point> points = new ArrayList<>();
    private final List<Transformation> affineTransformations = new ArrayList<>();
    private final List<Transformation> transformations = new ArrayList<>();
    private final double MAX_X;
    private final double MAX_Y;

    public Processor(Plot plot, double maxX, double maxY) {
        this.plot = plot;
        MAX_X = maxX;
        MAX_Y = maxY;
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

    public void  addTransformation(Transformation transformation) {
        transformations.add(transformation);
    }

    public void applyTransformations(int numIterations) {

        for (int i = 0; i < numIterations; i++) {

            for (int k = 0; k < points.size(); k++) {
                AffineTransformation affineTransformation = (AffineTransformation) getRandomTransformation(affineTransformations);
                points.get(k).position(affineTransformation.transform(points.get(k).position()));

                Transformation transformation = getRandomTransformation(transformations);
                points.get(k).position(transformation.transform(points.get(k).position()));

                Color currentColor = affineTransformation.getColor();
                try {
                    plot.arr()
                        [(int) (plot.toFullX(points.get(k).position().getX()))]
                        [(int) (plot.toFullY(points.get(k).position().getY()))].addHit(currentColor);
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
    }

    public void render(String file) {
        renderer.render(plot, file);
    }

    private Transformation getRandomTransformation(List<Transformation> transformationList) {
        double totalWeight = transformationList.stream().mapToDouble(Transformation::getWeight).sum();
        double[] cumulativeWeights = new double[transformationList.size()];
        cumulativeWeights[0] = transformationList.getFirst().getWeight() / totalWeight;
        for (int i = 1; i < cumulativeWeights.length; i++) {
            cumulativeWeights[i] = cumulativeWeights[i - 1] + transformationList.get(i).getWeight() / totalWeight;
        }

        double randomValue = shell.getDouble();
        int index = 0;
        while (index < cumulativeWeights.length && randomValue > cumulativeWeights[index]) {
            index++;
        }

        return transformationList.get(index);
    }

}
