package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Color;

public class SinusoidalTransform implements Transformation {

    double weight;

    AffineTransformation affineTransformation;

    public SinusoidalTransform(double weight, AffineTransformation affineTransformation) {
        this.weight = weight;
        this.affineTransformation = affineTransformation;

    }

    @Override
    public Vector2D transform( Vector2D point) {



            point = new Vector2D(Math.sin(point.getX()), -Math.sin(point.getY()));

        return point;
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
