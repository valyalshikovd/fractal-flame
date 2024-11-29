package backend.academy.fractalFlame;


import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageCreatorImpl implements ImageCreator {

    @Override
    public BufferedImage createImage(Plot plot) {

        BufferedImage image = new BufferedImage(plot.sizeX(), plot.sizeY(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < plot.sizeX(); x++) {
            for (int y = 0; y < plot.sizeY(); y++) {

                if(plot.arr()[x][y].countPoints() <= 3){
                    image.setRGB(x,y, Color.BLACK.getRGB());
                }else{
                    Pixel pixel = plot.arr()[x][y];


                    image.setRGB(x, y, new Color((int) pixel.r(),(int) pixel.g(), (int)pixel.b(),
                        (int) gammaCorrection(plot.arr()[x][y].countPoints())).getRGB());
                }

            }
        }

        return image;

    }

    private double gammaCorrection(double bright) {

        double GAMMA = 2;
        bright = bright / 255.0;

        bright = Math.pow(bright, 1 / GAMMA);


        return bright * 255;
    }
}
