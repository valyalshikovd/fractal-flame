package backend.academy.fractalFlame;

import lombok.Getter;

public class PlotSegment {
    @Getter
    private int xSize;
    @Getter
    private int ySize;
    private Pixel[][] arr;

    public PlotSegment(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.arr = new Pixel[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                this.arr[i][j] = new Pixel();
            }
        }
    }

    public Pixel getPixel(int x, int y) {
        synchronized (this) {
            return arr[x][y];
        }
    }

    public Pixel readPixel(int x, int y) {
            return arr[x][y];
    }
}
