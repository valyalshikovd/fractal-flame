package backend.academy.fractalFlame;

import backend.academy.fractalFlame.transformation.Transformation;
import backend.academy.fractalFlame.util.RandomShell;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
public class Point {

    private Vector3D position;
    private int countOfPoints;


    public void applyTransformation(Transformation transformation) {
        position = transformation.transform(this.position);
    }

    public Point deepCopy(){
        return new Point(new Vector3D(position.getX(), position.getY(), position.getZ()), countOfPoints);
    }

    @Override public String toString() {
        return "Point{" +
            "position=" + position +
            ", countOfPoints=" + countOfPoints +
            '}';
    }

    public static Point genRandomPoint(double MAX_X, double MAX_Y, RandomShell shell) {
        return new Point(new Vector3D(-MAX_X + (2 * shell.getDouble()), -MAX_Y + (2 * shell.getDouble()), 1), 0);
    }
}



