package backend.academy.fractalFlame.transformation;

import java.awt.Color;
import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class CosineTransform {

    @Getter double weight;
    @Getter Color color;

    public CosineTransform(double weight) {
        this.weight = weight;
    }

    public Vector2D transform(Vector2D point) {

        double x = Math.cos(Math.PI * point.getX()) * Math.cosh(point.getY());
        double y = Math.sin(Math.PI * point.getX()) * Math.sinh(point.getY());

        return new Vector2D(x, -y);
    }

}
