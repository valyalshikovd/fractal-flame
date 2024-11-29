package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class SwirlTransform implements Transformation {


    double weight;


    public SwirlTransform( double weight) {
        this.weight = weight;
    }

    @Override
    public Vector3D transform(Vector3D point) {

        double r = Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5);


        return new Vector3D(point.getX() * Math.sin(r*r) - point.getY() * Math.cos(r*r), point.getX()*Math.cos(r*r) + point.getY() * Math.sin(r*r), point.getZ());
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
