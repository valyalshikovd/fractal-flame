package backend.academy.fractalFlame;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("ParameterNumber")
public class ImageCreatorImpl implements ImageCreator {

    private final static int NUM_THREADS = 10;
    private final static int IGNORED_POINT = 0;

    @Override
    public BufferedImage createImage(Plot plot) {

        BufferedImage image = new BufferedImage(plot.sizeX(), plot.sizeY(), BufferedImage.TYPE_INT_ARGB);

        ForkJoinPool forkJoinPool = new ForkJoinPool(NUM_THREADS);

        forkJoinPool.invoke(new ImageWriteFork(
            0,
            0,
            plot.sizeX(),
            plot.sizeY(),
            plot.sizeX() / NUM_THREADS,
            plot.sizeY() / NUM_THREADS,
            plot,
            image));

        forkJoinPool.shutdown();
        return image;

    }

    private double gammaCorrection(double bright) {
        final double GAMMA = 2;
        final double rgbBytesNum = 255.0;
        double res = bright / rgbBytesNum;
        res = Math.pow(res, 1 / GAMMA);
        return res * rgbBytesNum;
    }

    private final class ImageWriteFork extends RecursiveTask<Void> {

        private final int xStart;
        private final int yStart;
        private final int xEnd;
        private final int yEnd;
        private final int sizeX;
        private final int sizeY;
        private final Plot plot;
        private final BufferedImage image;

        private ImageWriteFork(
            int xStart, int yStart,
            int xEnd, int yEnd,
            int sizeX, int sizeY,
            Plot plot, BufferedImage image
        ) {
            this.xStart = xStart;
            this.yStart = yStart;
            this.xEnd = xEnd;
            this.yEnd = yEnd;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.plot = plot;
            this.image = image;
        }

        @Override
        protected Void compute() {
            if (
                xEnd - xStart < sizeX && yEnd - yStart < sizeY
            ) {
                try {
                    for (int x = xStart; x < xEnd; x++) {
                        for (int y = yStart; y < yEnd; y++) {
                            if (plot.getPoint(x, y).countPoints() <= IGNORED_POINT) {
                                image.setRGB(x, y, Color.BLACK.getRGB());
                            } else {
                                Pixel pixel = plot.getPoint(x, y);

                                if (x >= 0) {
                                    image.setRGB(x, y, new Color((int) pixel.r(), (int) pixel.g(), (int) pixel.b(),
                                        (int) gammaCorrection(plot.getPoint(x, y).countPoints())).getRGB());
                                }
                            }
                        }
                    }
                } catch (Exception e) {

                }
            } else {
                int midX = (xStart + xEnd) / 2;
                int midY = (yStart + yEnd) / 2;
                ImageWriteFork leftUpTask = new ImageWriteFork(xStart, yStart, midX, midY, sizeX, sizeY, plot, image);
                ImageWriteFork rightUpTask = new ImageWriteFork(xStart, midY, midX, yEnd, sizeX, sizeY, plot, image);
                ImageWriteFork rightDownTask = new ImageWriteFork(midX, midY, xEnd, yEnd, sizeX, sizeY, plot, image);
                ImageWriteFork leftDownTask = new ImageWriteFork(midX, yStart, xEnd, midY, sizeX, sizeY, plot, image);
                leftUpTask.fork();
                rightUpTask.compute();
                rightDownTask.compute();
                leftUpTask.join();
                leftDownTask.compute();
            }
            return null;
        }
    }
}
