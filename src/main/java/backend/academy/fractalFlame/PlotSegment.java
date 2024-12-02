package backend.academy.fractalFlame;

import lombok.Getter;

public class PlotSegment {
    @Getter
    private int x_size;
    @Getter
    private int y_size;
    private Pixel[][] arr;

    public PlotSegment(int x_size, int y_size) {
        this.x_size = x_size;
        this.y_size = y_size;
        this.arr = new Pixel[x_size][y_size];
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                this.arr[i][j] = new Pixel();
            }
        }
    }

    public Pixel getPixel(int x, int y) {
        synchronized (this) {
            return arr[x][y];
        }
    }
}
