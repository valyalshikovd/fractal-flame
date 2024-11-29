package backend.academy.fractalFlame.transformation;

import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.awt.Color;

public class CosineTransform {

    AffineTransformation affineTransformation;
    @Getter double weight;
    @Getter Color color;

    public CosineTransform(AffineTransformation affineTransformation, double weight) {
        this.affineTransformation = affineTransformation;
        this.weight = weight;
    }


    public Vector3D transform(Vector3D point) {

        point = affineTransformation.transform(point);

        double x = Math.cos(Math.PI * point.getX()) * Math.cosh(point.getY());
        double y = Math.sin(Math.PI * point.getX()) * Math.sinh(point.getY());

        return new Vector3D(x, -y, 0);
    }

}