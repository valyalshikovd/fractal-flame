package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class ExponentTransform implements Transformation {

    double weight;

    public ExponentTransform(double weight) {
        this.weight = weight;
    }

    @Override
    public Vector2D transform(Vector2D point) {

        double expFactor = Math.exp(point.getX() - 1);
        double newX = expFactor * Math.cos(Math.PI * point.getY());
        double newY = expFactor * Math.sin(Math.PI * point.getY());
        return new Vector2D(newX, newY);
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
