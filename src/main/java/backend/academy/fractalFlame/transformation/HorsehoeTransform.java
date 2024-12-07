package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class HorsehoeTransform implements Transformation {


    double weight;

    public HorsehoeTransform(double weight) {

        this.weight = weight;
    }


    @Override
    public Vector2D transform(Vector2D point) {

        double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());

        return new Vector2D(
            1 / r * (point.getX() - point.getY()) * (point.getX() + point.getY()),
            1 / r * 2 * point.getX() * point.getY()
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
