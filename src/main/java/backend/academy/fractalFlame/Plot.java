package backend.academy.fractalFlame;

import lombok.Getter;

@Getter
public class Plot {

    private final int sizeX;
    private final int sizeY;
    private final PlotSegment[][] arr;
    private final int numSegments;
    private final int scalingCoefficient = 6;

    public Plot(int sizeX, int sizeY, int numSegments) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numSegments = numSegments;
        this.arr = new PlotSegment[sizeX / this.numSegments][sizeY / this.numSegments];

        for (int i = 0; i <  sizeX / this.numSegments; i++) {
            for (int j = 0; j <  sizeY / this.numSegments; j++) {
                this.arr[i][j] = new PlotSegment(this.numSegments, this.numSegments);
            }
        }
    }

    public double toOneX(int x) {
        return (x - (double) sizeX / 2) / ((double) sizeX / scalingCoefficient);
    }

    public double toOneY(int y) {
        return (y - (double) sizeY / 2) / ((double) sizeY / scalingCoefficient);
    }

    public double toFullX(double x) {
        return x * ((double) sizeX / scalingCoefficient) + ((double) sizeX / 2);
    }

    public double toFullY(double y) {
        return y * ((double) sizeY / scalingCoefficient) + ((double) sizeY / 2);
    }



    public Pixel getPoint(int x, int y) {

        PlotSegment seg = arr[x / numSegments][y / numSegments];



        return seg.getPixel(x  % numSegments, y  % numSegments);
    }
}
