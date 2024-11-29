package backend.academy.fractalFlame.transformation;

import backend.academy.fractalFlame.util.RandomShell;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.awt.Color;


public class AffineTransformation implements Transformation {

    private static Color[] colors = new Color[] {
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
        Color.YELLOW,
        Color.ORANGE,
        Color.PINK
    };

    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private Color color;
    private double weight;



    public AffineTransformation(double a, double b, double c, double d, double e, double f, Color color, double weight) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.color = color;
        this.weight = weight;
    }

    @Override
    public Vector3D transform(Vector3D point) {
        return new Vector3D(
            a * point.getX() + b * point.getX() + c,
            d * point.getY() + e * point.getY() + f,
            0
        );
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }


    public static AffineTransformation genRandomAffineTransformation(RandomShell randomShell) {
        double min = -1.5;
        double max = 1.5;

        boolean valid = false;

        double a = 0, b = 0, d = 0, e = 0;

        while (!valid) {
            a = min + (max - min) * randomShell.getDouble();
            b = min + (max - min) * randomShell.getDouble();
            d = min + (max - min) * randomShell.getDouble();
            e = min + (max - min) * randomShell.getDouble();


            if (a * a + d * d < 1 && b * b + e * e < 1 &&
                a * a + b * b + d * d + e * e < 1 + (a * e - b * d) * (a * e - b * d)) {
                valid = true;
            }
        }
        return new AffineTransformation(
            a,
            b,
            min + 2*(max - min) * randomShell.getDouble(),
            d,
            e,
            min + 2* (max - min) * randomShell.getDouble(),
            colors[randomShell.get(colors.length)],
            randomShell.getDouble());
    }


}