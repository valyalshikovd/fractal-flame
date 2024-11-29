package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class HorsehoeTransform implements Transformation {

    AffineTransformation affineTransformation;
    double weight;

    public HorsehoeTransform(AffineTransformation affineTransformation, double weight) {
        this.affineTransformation = affineTransformation;
        this.weight = weight;
    }


    @Override
    public Vector3D transform(Vector3D point) {

        double r = Math.pow(point.getX() * point.getX() + point.getY() * point.getY(), 0.5);

        return new Vector3D(
            1/r * (point.getX() - point.getY()) * (point.getX() + point.getY()),
            1/r * 2 * point.getX() * point.getY(),
            point.getZ()
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
