package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PolarTransform implements Transformation {

    double phi;
    double weight;

    public PolarTransform(double phi, double weight) {
        this.phi = phi;
        this.weight = weight;
    }

    @Override
    public Vector2D transform(Vector2D point) {

        double r = Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5);


        return new Vector2D( Math.atan2(point.getY(), point.getX()) / Math.PI, r - 1);
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
