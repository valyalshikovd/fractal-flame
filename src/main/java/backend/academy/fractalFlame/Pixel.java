package backend.academy.fractalFlame;

import java.awt.Color;
import lombok.Getter;

@Getter
public class Pixel {
    private final double rgbBytesNum = 255.0;
    private double r = 0;
    private double g = 0;
    private double b = 0;
    private int countPoints = 0;

    public void addHit(Color c) {

        if (countPoints < rgbBytesNum) {
            countPoints++;
        }

        if (r == 0) {
            r = c.getRed();
        } else {
            r = (c.getRed() + r) / 2;
        }
        if (g == 0) {
            g = c.getGreen();
        } else {
            g = (c.getGreen() + g) / 2;
        }
        if (b == 0) {
            b = c.getBlue();
        } else {
            b = (c.getBlue() + b) / 2;
        }

    }

    public Pixel copy() {

        Pixel p = new Pixel();
        p.r = r;
        p.g = g;
        p.b = b;
        p.countPoints = countPoints;
        return p;

    }
}
