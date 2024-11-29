package backend.academy.fractalFlame.transformation;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.awt.Color;

public interface Transformation {


    Vector3D transform(Vector3D point);
    double getWeight();


}
