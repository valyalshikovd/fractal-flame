package backend.academy.fractalFlame;

import java.awt.image.BufferedImage;

public class ImageCreatorImpl implements ImageCreator {

    private final ColorMapper colorMapper = new ColorMapperImpl();

    @Override
    public BufferedImage createImage(Plot plot) {


        BufferedImage image = new BufferedImage(plot.sizeX(), plot.sizeY(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < plot.sizeX(); x++) {
            for (int y = 0; y < plot.sizeX(); y++) {
                image.setRGB(x, y, colorMapper.mapColor(colorMapper.mapColor(plot.arr()[x][y].position().getZ())));
            }
        }

        return image;

    }
}
