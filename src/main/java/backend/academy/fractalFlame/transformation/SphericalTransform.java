package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Color;

public class SphericalTransform implements Transformation {

    double weight;
    AffineTransformation affineTransformation;

    public SphericalTransform(double weight, AffineTransformation affineTransformation, Color color) {
        this.weight = weight;
        this.affineTransformation = affineTransformation;
    }

    @Override
    public Vector2D transform( Vector2D point) {

        double sqrt = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());

        return new Vector2D(point.getX() / (sqrt * sqrt), point.getY()/ (sqrt * sqrt));
    }

    @Override
    public double getWeight() {
        return weight;
    }



}
