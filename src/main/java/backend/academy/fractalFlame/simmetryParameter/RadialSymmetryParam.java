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

        double x = point.getX(), y = point.getY();

        for (int i = 0; i < numSegments - 1; i++) {
            x = x * Math.cos(angle) - y * Math.sin(angle);
            y = x * Math.sin(angle) + y * Math.cos(angle);

            result[i] = new Vector2D(x, y);
        }

        return result;

    }
}
