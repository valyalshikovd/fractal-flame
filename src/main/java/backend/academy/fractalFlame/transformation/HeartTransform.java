package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class HeartTransform implements Transformation {

    double weight;

    public HeartTransform( double weight) {
        this.weight = weight;
    }


    @Override
    public Vector2D transform(Vector2D point) {

        return new Vector2D(
            Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.sin(Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.atan(point.getY()/point.getX())),
            - Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.cos(Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.atan(point.getY()/point.getX()))
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
