package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Color;

public class SinusoidalTransform implements Transformation {

    double weight;

    public SinusoidalTransform(double weight) {
        this.weight = weight;
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
