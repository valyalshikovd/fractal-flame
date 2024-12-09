package backend.academy.fractalFlame;

import lombok.Getter;

/**
 * Двумерный массив точек для генерации изображения.
 * Представляет из себя двумерный массив PlotSegment
 * Количество PlotSegment зависит от числа потоков.
 * Сделано это для того, чтобы синхронизировать доступ
 * к пикселю на уровне сегмента, и не синхронизировать
 * весь массив точек.
 */
@Getter
public class Plot {

    private final int sizeX;
    private final int sizeY;

    @Getter
    private final PlotSegment[][] arr;
    private final int numSegments;
    private final int scalingCoefficient = 6;
    private int countSegments;

    public Plot(int sizeX, int sizeY, int numSegments) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numSegments = numSegments;
        countSegments = (int) Math.round(Math.sqrt(numSegments));

        this.arr = new PlotSegment[countSegments][countSegments];

        for (int i = 0; i < countSegments; i++) {
            for (int j = 0; j < countSegments; j++) {
                this.arr[i][j] = new PlotSegment(sizeX / countSegments, sizeY / countSegments);
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

        PlotSegment seg = arr[x / (sizeX / countSegments)][y / (sizeY / countSegments)];

        return seg.getPixel(x % (sizeX / countSegments), y % (sizeX / countSegments));
    }

    public Pixel readpoint(int x, int y) {
        PlotSegment seg = arr[x / (sizeX / countSegments)][y / (sizeY / countSegments)];
        return seg.readPixel(x % (sizeX / countSegments), y % (sizeX / countSegments));
    }
}
