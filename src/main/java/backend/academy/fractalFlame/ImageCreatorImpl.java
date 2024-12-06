package backend.academy.fractalFlame;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ImageCreatorImpl implements ImageCreator {

    @Override
    public BufferedImage createImage(Plot plot) {

        BufferedImage image = new BufferedImage(plot.sizeX(), plot.sizeY(), BufferedImage.TYPE_INT_ARGB);

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        forkJoinPool.invoke(new ImageWriteFork(
            0,
            0,
            plot.sizeX(),
            plot.sizeY(),
            plot.sizeX() / 10,
            plot.sizeY() / 10,
            plot,
            image));

//
//        for (int x = 0; x < plot.sizeX(); x++) {
//            for (int y = 0; y < plot.sizeY(); y++) {
//
//                if (plot.getPoint(x, y).countPoints() <= 3) {
//                    image.setRGB(x, y, Color.BLACK.getRGB());
//                } else {
//                    Pixel pixel = plot.getPoint(x, y);
//
//                    image.setRGB(x, y, new Color((int) pixel.r(), (int) pixel.g(), (int) pixel.b(),
//                        (int) gammaCorrection(plot.getPoint(x, y).countPoints())).getRGB());
//                }
//            }
//        }



        forkJoinPool.shutdown();

        return image;

    }

    private class ImageWriteFork extends RecursiveTask<Void> {

        private final int xStart;
        private final int yStart;
        private final int xEnd;
        private final int yEnd;
        private final int sizeX;
        private final int sizeY;
        private final Plot plot;
        private final BufferedImage image;

        private ImageWriteFork(int xStart, int yStart, int xEnd, int yEnd, int sizeX, int sizeY, Plot plot, BufferedImage image
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
            if(
                xEnd - xStart < sizeX && yEnd - yStart < sizeY
            ) {
                try {
                    for (int x = xStart; x < xEnd; x++) {
                        for (int y = yStart; y < yEnd; y++) {
                            if (plot.getPoint(x, y).countPoints() <= 3) {
                                image.setRGB(x, y, Color.BLACK.getRGB());
                            } else {
                                Pixel pixel = plot.getPoint(x, y);

                                if(x >= 0) {
                                    image.setRGB(x, y, new Color((int) pixel.r(), (int) pixel.g(), (int) pixel.b(),
                                        (int) gammaCorrection(plot.getPoint(x, y).countPoints())).getRGB());
                                }
                            }
                        }
                    }
                }catch (Exception e) {

                }
            }else {
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

    private double gammaCorrection(double bright) {

        double GAMMA = 2;
        bright = bright / 255.0;

        bright = Math.pow(bright, 1 / GAMMA);

        return bright * 255;
    }
}
