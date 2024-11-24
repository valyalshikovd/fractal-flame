package backend.academy.fractalFlame;

import lombok.Getter;

@Getter
public class Plot {

    private final int sizeX;
    private final int sizeY;
    private final Point[][] arr;

    public Plot(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.arr = new Point[sizeX][sizeY];
    }
}
