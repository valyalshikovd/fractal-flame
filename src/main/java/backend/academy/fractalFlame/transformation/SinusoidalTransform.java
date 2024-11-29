package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.awt.Color;

public class SinusoidalTransform implements Transformation {

    double weight;

    AffineTransformation affineTransformation;

    public SinusoidalTransform(double weight, AffineTransformation affineTransformation) {
        this.weight = weight;
        this.affineTransformation = affineTransformation;

    }

    @Override
    public Vector3D transform( Vector3D point) {



            point = new Vector3D(Math.sin(point.getX()), -Math.sin(point.getY()), point.getZ());

        return point;
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
