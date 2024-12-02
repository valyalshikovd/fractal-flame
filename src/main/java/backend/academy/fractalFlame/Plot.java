package backend.academy.fractalFlame;

import lombok.Getter;

@Getter
public class Plot {

    private final int sizeX;
    private final int sizeY;
    private final PlotSegment[][] arr;
    private final int NUM_SEGMENTS = 200;

    public Plot(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.arr = new PlotSegment[sizeX / NUM_SEGMENTS][sizeY / NUM_SEGMENTS];

        for (int i = 0; i <  sizeX / NUM_SEGMENTS; i++) {
            for (int j = 0; j <  sizeY / NUM_SEGMENTS; j++) {
                this.arr[i][j] = new PlotSegment( NUM_SEGMENTS ,  NUM_SEGMENTS);
            }
        }
    }

    public double toOneX(int x){

        return (x - (double) sizeX / 2) / ((double) sizeX / 6);
    }

    public double toOneY(int y){

        return (y - (double) sizeY / 2) / ((double) sizeY / 6);
    }

    public double toFullX(double x){
        return x * ((double) sizeX / 6) + ((double) sizeX / 2);
    }

    public double toFullY(double y){
        return y * ((double) sizeY / 6) + ((double) sizeY / 2);
    }



    public Pixel getPoint(int x, int y) {

        PlotSegment seg = arr[x / NUM_SEGMENTS ][y / NUM_SEGMENTS ];



        return seg.getPixel(x  % NUM_SEGMENTS, y  % NUM_SEGMENTS);
    }
}
