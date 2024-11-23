package backend.academy.fractalFlame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;


@Getter
@Setter
@AllArgsConstructor
public class Point {

    private Vector3D position;
    private int countOfPoints;


    public void applyTransformation(Transformation transformation) {
        position = transformation.transform(this.position);
    }

}



