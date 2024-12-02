package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class HeartTransform implements Transformation {

    double weight;

    public HeartTransform( double weight) {
        this.weight = weight;
    }


    @Override
    public Vector3D transform(Vector3D point) {

        return new Vector3D(
            Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.sin(Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.atan(point.getY()/point.getX())),
            - Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.cos(Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5) * Math.atan(point.getY()/point.getX())),
            0
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
