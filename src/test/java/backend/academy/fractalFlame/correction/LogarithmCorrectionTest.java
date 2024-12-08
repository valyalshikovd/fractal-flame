package backend.academy.fractalFlame.correction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class LogarithmCorrectionTest {


    @Test
    public  void  logarithmCorrectionTest1() {
        Correction correction = new LogarithmCorrection(0.001);
        double brightValue = 42;
        System.out.println(correction.correct(brightValue));
        assertTrue(Math.abs(correction.correct(brightValue) - 10.49) < 0.01);
    }

    @Test
    public  void  logarithmCorrectionTest2() {
        Correction correction = new LogarithmCorrection(0.001);
        double brightValue = 0;
        System.out.println(correction.correct(brightValue));
        assertEquals(correction.correct(brightValue), 0.0);
    }

    @Test
    public  void  logarithmCorrectionTest3() {
        Correction correction = new LogarithmCorrection(0.001);
        double brightValue = -1;
        assertEquals(0, correction.correct(brightValue));
    }

    @Test
    public  void  logarithmCorrectionTest4() {
        Correction correction = new LogarithmCorrection(0.001);
        double brightValue = 255;
        System.out.println(correction.correct(brightValue));
        assertTrue(Math.abs(correction.correct(brightValue) - 57.91) < 0.01);
    }

    @Test
    public  void  logarithmCorrectionTest5() {
        Correction correction = new LogarithmCorrection(0.001);
        double brightValue = 300;
        System.out.println(correction.correct(brightValue));
        assertTrue(Math.abs(correction.correct(brightValue) - 66.9) < 0.01);
    }

}
