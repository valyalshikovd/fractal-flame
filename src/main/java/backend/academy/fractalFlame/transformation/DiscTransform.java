package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class DiscTransform implements Transformation {

    double phi;
    double weight;

    public DiscTransform(double phi, double weight) {
        this.phi = phi;
        this.weight = weight;
    }

    @Override
    public Vector2D transform(Vector2D point) {

        double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
        return new Vector2D(
            phi / Math.PI * Math.atan(point.getY() / point.getX()) * Math.sin(Math.PI * r),
            phi / Math.PI * Math.atan(point.getY() / point.getX())  * Math.cos(Math.PI * r));
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
