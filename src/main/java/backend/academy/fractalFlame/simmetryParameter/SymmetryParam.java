package backend.academy.fractalFlame.simmetryParameter;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public interface SymmetryParam {


    Vector2D[] applySymmetry(Vector2D plot);

}
