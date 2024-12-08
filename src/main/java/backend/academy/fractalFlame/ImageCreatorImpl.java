package backend.academy.fractalFlame;

import backend.academy.fractalFlame.correction.Correction;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("ParameterNumber")
public class ImageCreatorImpl implements ImageCreator {

    private final int numThreads;
    private final static int IGNORED_POINT = 0;
    private final Correction correction;

    public ImageCreatorImpl(int numThreads, Correction correction) {
        this.numThreads = numThreads;
        this.correction = correction;
    }

    @Override
    public BufferedImage createImage(Plot plot) {

        BufferedImage image = new BufferedImage(plot.sizeX(), plot.sizeY(), BufferedImage.TYPE_INT_ARGB);

        ForkJoinPool forkJoinPool = new ForkJoinPool(numThreads);

        forkJoinPool.invoke(new ImageWriteFork(
            0,
            0,
            plot.sizeX(),
            plot.sizeY(),
            plot.sizeX() / numThreads,
            plot.sizeY() / numThreads,
            plot,
            image));

        forkJoinPool.shutdown();
        return image;

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

                                image.setRGB(x, y, new Color((int) pixel.r(), (int) pixel.g(), (int) pixel.b(),
                                    (int) correction.correct(plot.getPoint(x, y).countPoints())).getRGB());

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
                leftUpTask.join();
                rightUpTask.fork();
                rightUpTask.join();
                rightDownTask.fork();
                rightDownTask.join();
                leftDownTask.fork();
                leftDownTask.join();
            }
            return null;
        }
    }
}
