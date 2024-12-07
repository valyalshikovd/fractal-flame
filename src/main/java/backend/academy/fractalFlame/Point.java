package backend.academy.fractalFlame;

import backend.academy.fractalFlame.transformation.Transformation;
import backend.academy.fractalFlame.util.RandomShell;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

@Getter
@Setter
@AllArgsConstructor
public class Point {

    private Vector2D position;
    private int countOfPoints;


    public void applyTransformation(Transformation transformation) {
        position = transformation.transform(this.position);
    }

    public Point deepCopy() {
        return new Point(new Vector2D(position.getX(), position.getY()), countOfPoints);
    }

    @Override public String toString() {
        return "Point{"
            + "position=" + position
            + ", countOfPoints=" + countOfPoints
            + '}';
    }

    public static Point genRandomPoint(double maxX, double maxY, RandomShell shell) {
        return new Point(new Vector2D(-maxX + (2 * shell.getDouble()), -maxY + (2 * shell.getDouble())), 0);
    }
}



