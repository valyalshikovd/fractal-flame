package backend.academy.fractalFlame;

import backend.academy.fractalFlame.correction.Correction;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"ParameterNumber", "NestedForDepth"})
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


        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < plot.arr().length; i++) {
            for (int j = 0; j < plot.arr()[i].length; j++) {

                int finalI = i;
                int finalJ = j;
                executorService.execute(
                    () -> {
                        int plotSegmentSizeX = plot.arr()[finalI][finalJ].xSize();
                        int plotSegmentSizeY = plot.arr()[finalI][finalJ].ySize();


                        for (int x = 0; x < plotSegmentSizeX; x++) {
                            for (int y = 0; y < plotSegmentSizeY; y++) {

                                int currX = x + finalI * plotSegmentSizeX;
                                int currY = y + finalJ * plotSegmentSizeY;


                                if (plot.readpoint(currX, currY).countPoints() <= IGNORED_POINT) {
                                    image.setRGB(currX, currY, Color.BLACK.getRGB());
                                } else {
                                    Pixel pixel = plot.getPoint(currX, currY);

                                    image.setRGB(
                                        currX,
                                        currY,
                                        new Color((int) pixel.r(), (int) pixel.g(), (int) pixel.b(),
                                        (int) correction.correct(
                                            plot.readpoint(
                                                currX,
                                                currY
                                            ).countPoints())).getRGB());
                                }
                            }
                        }
                    }
                );
            }
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        return image;
    }
}
