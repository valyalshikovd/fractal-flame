package backend.academy.fractalFlame.simmetryParameter;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RadialSymmetryParamTest {

    @Test
    public void radialSymmetryParamTest1() {
        SymmetryParam symmetryParam = new RadialSymmetryParam(2);
        Vector2D[] vector2DS = symmetryParam.applySymmetry(new Vector2D(1,1 ));
        assertTrue(Math.abs(-1 - vector2DS[0].getX()) < 0.1);
        assertTrue(Math.abs(-1 - vector2DS[0].getY()) < 0.1);
    }


    @Test
    public void radialSymmetryParamTest2() {
        SymmetryParam symmetryParam = new RadialSymmetryParam(3);
        Vector2D[] vector2DS = symmetryParam.applySymmetry(new Vector2D(1,1 ));



        assertTrue(Math.abs(-1.36 - vector2DS[0].getX()) < 0.01);
        assertTrue(Math.abs(0.36 - vector2DS[0].getY()) < 0.01);
        assertTrue(Math.abs(0.36 - vector2DS[1].getX()) < 0.01);
        assertTrue(Math.abs(-1.36 - vector2DS[1].getY()) < 0.01);
    }


}
