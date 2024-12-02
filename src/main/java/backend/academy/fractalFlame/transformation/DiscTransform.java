package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Color;

public class DiscTransform implements Transformation {

    double phi;
    double weight;

    public DiscTransform(double phi, double weight) {
        this.phi = phi;
        this.weight = weight;
    }

    @Override
    public Vector2D transform(Vector2D point) {

        double r = Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5);


        return new Vector2D(phi/Math.PI * Math.atan(point.getY() / point.getX()) * Math.sin(Math.PI * r), phi/Math.PI * Math.atan(point.getY() / point.getX())  * Math.cos(Math.PI * r));
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
