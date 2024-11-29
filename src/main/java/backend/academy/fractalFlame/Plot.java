package backend.academy.fractalFlame;

import lombok.Getter;

@Getter
public class Plot {

    private final int sizeX;
    private final int sizeY;
    private final Pixel[][] arr;

    public Plot(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.arr = new Pixel[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.arr[i][j] = new Pixel();
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
        return arr[x][y];
    }
}
