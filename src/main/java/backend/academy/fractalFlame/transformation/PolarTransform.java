package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PolarTransform implements Transformation {

    double weight;

    public PolarTransform(double weight) {

        this.weight = weight;
    }

    @Override
    public Vector2D transform(Vector2D point) {

        double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
        return new Vector2D(Math.atan2(point.getY(), point.getX()) / Math.PI, r - 1);
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
