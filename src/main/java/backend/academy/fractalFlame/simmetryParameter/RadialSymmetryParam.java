package backend.academy.fractalFlame.simmetryParameter;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class RadialSymmetryParam implements SymmetryParam {

    private final int numSegments;
    private final double angle;

    public RadialSymmetryParam(int numSegments) {
        this.numSegments = numSegments;
        this.angle = 2 * Math.PI / numSegments;
    }

    @Override
    public Vector2D[] applySymmetry(Vector2D point) {

        Vector2D[] result = new Vector2D[numSegments - 1];

        double x;
        double y;

        for (int i = 0; i < numSegments - 1; i++) {

            x = point.getX() * Math.cos(angle * (i + 1)) - point.getY() * Math.sin(angle * (i + 1));
            y = point.getX() * Math.sin(angle * (i + 1)) + point.getY() * Math.cos(angle * (i + 1));

            result[i] = new Vector2D(x, y);
        }

        return result;

    }
}
