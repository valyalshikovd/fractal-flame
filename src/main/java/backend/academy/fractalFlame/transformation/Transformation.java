package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Color;

public interface Transformation {


    Vector2D transform(Vector2D point);
    double getWeight();


}
