package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class HandkerchiefTransformation implements Transformation {


    double weight;

    public HandkerchiefTransformation( double weight) {
        this.weight = weight;
    }


    @Override
    public Vector3D transform(Vector3D point) {


        double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
        double theta = Math.atan2(point.getY(), point.getX());
        return new Vector3D(
            r * Math.sin(theta + r),
            r * Math.cos(theta - r),
            0
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
