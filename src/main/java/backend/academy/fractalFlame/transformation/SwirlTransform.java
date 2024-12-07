package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class SwirlTransform implements Transformation {

    double weight;

    public SwirlTransform(double weight) {
        this.weight = weight;
    }

    @Override
    public Vector2D transform(Vector2D point) {

        double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());

        return new Vector2D(
            point.getX() * Math.sin(r * r) - point.getY() * Math.cos(r * r),
            point.getX() * Math.cos(r * r) + point.getY() * Math.sin(r * r)
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
