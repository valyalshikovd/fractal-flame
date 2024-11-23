package backend.academy.fractalFlame;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Transformation {


    Vector3D transform(Vector3D point);

}
