package backend.academy.fractalFlame;

import java.awt.image.BufferedImage;

public class ImageCreatorImpl implements ImageCreator {

    @Override
    public BufferedImage createImage(int sizeX, int sizeY, Point[][] point) {


        BufferedImage image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);

        //todo

        return image;

    }
}
