package backend.academy.fractalFlame;

import java.awt.image.BufferedImage;

public class ImageCreatorImpl implements ImageCreator {

    private final ColorMapper colorMapper = new ColorMapperImpl();

    @Override
    public BufferedImage createImage(int sizeX, int sizeY, Point[][] point) {


        BufferedImage image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < sizeX; x++){
            for (int y = 0; y < sizeY; y++){
                image.setRGB(x, y, colorMapper.mapColor(colorMapper.mapColor(point[x][y].position().getZ())));
            }
        }

        return image;

    }
}
