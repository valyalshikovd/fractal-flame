package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class HeartTransform implements Transformation {

    double weight;

    public HeartTransform(double weight) {
        this.weight = weight;
    }


    @Override
    public Vector2D transform(Vector2D point) {

        return new Vector2D(
            Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())
                * Math.sin(Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())
                * Math.atan(point.getY() / point.getX())),
            -Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())
                * Math.cos(Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY())
                * Math.atan(point.getY() / point.getX()))
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
