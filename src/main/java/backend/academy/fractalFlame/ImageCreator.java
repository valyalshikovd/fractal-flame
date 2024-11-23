package backend.academy.fractalFlame;

import java.awt.image.BufferedImage;

public interface ImageCreator {


    BufferedImage createImage(int sizeX, int sizeY, Point[][] point);

}
