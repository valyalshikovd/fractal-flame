package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class LinearTransformation implements Transformation {

    AffineTransformation affineTransformation;
    double weight;

    public LinearTransformation(AffineTransformation affineTransformation, double weight) {
        this.affineTransformation = affineTransformation;
        this.weight = weight;
    }

    @Override
    public Vector3D transform(Vector3D point) {
        point = affineTransformation.transform(point);

        return new Vector3D(point.getX(), point.getY(), point.getZ());
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
