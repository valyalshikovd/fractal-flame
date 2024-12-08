package backend.academy.fractalFlame;

import java.awt.image.BufferedImage;

public class RendererImpl implements Renderer {

    private final ImageCreator imageCreator;
    private final ImageWriter imageWriter;

    public RendererImpl(ImageCreator imageCreator, ImageWriter imageWriter) {
        this.imageCreator = imageCreator;
        this.imageWriter = imageWriter;
    }

    @Override
    public void render(Plot plot, String filename, String path) {

        BufferedImage image = imageCreator.createImage(plot);
        imageWriter.writeImage(image, filename, path);

    }
}
