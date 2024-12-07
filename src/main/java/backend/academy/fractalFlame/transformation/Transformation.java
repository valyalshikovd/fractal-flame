package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public interface Transformation {

    Vector2D transform(Vector2D point);

    double getWeight();
}
