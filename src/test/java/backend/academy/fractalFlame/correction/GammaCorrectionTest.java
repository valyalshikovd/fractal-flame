package backend.academy.fractalFlame.correction;

import org.junit.jupiter.api.Test;
import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.*;

class GammaCorrectionTest {

    @Test
    public void gammaCorrectionTest1() {
        Correction gammaCorrection = new GammaCorrection(2);
        double brightValue = 3;
        assertTrue(Math.abs(gammaCorrection.correct(brightValue) - 27.6) < 0.1);
    }

    @Test
    public void gammaCorrectionTest2() {
        Correction gammaCorrection = new GammaCorrection(2);
        double brightValue = -1;
        assertEquals(NaN, gammaCorrection.correct(brightValue));
    }

    @Test
    public void gammaCorrectionTest3() {
        Correction gammaCorrection = new GammaCorrection(2);
        double brightValue = 0;
        assertEquals(0.0, gammaCorrection.correct(brightValue));
    }

    @Test
    public void gammaCorrectionTest4() {
        Correction gammaCorrection = new GammaCorrection(2);
        double brightValue = 255;
        System.out.println(gammaCorrection.correct(brightValue));
        assertTrue(Math.abs(gammaCorrection.correct(brightValue) - 255.0) < 0.1);
    }

    @Test
    public void gammaCorrectionTest5() {
        Correction gammaCorrection = new GammaCorrection(2);
        double brightValue = 300;
        System.out.println(gammaCorrection.correct(brightValue));
        assertTrue(Math.abs(gammaCorrection.correct(brightValue) - 255.0) < 0.1);
    }

}
