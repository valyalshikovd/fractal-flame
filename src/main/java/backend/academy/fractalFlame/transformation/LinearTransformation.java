package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class LinearTransformation implements Transformation {

    AffineTransformation affineTransformation;
    double weight;

    public LinearTransformation(AffineTransformation affineTransformation, double weight) {
        this.affineTransformation = affineTransformation;
        this.weight = weight;
    }

    @Override
    public Vector2D transform(Vector2D point) {
        Vector2D newPoint = affineTransformation.transform(point);

        return new Vector2D(newPoint.getX(), newPoint.getY());
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
