package backend.academy.fractalFlame;

import java.awt.image.BufferedImage;

public class RendererImpl implements Renderer {

    private final ImageCreator imageCreator = new ImageCreatorImpl();
    private final ImageWriter imageWriter = new DefaultPngImageWriter();


    @Override
    public void render(Plot plot) {


        BufferedImage image = imageCreator.createImage(plot);

        imageWriter.writeImage(image, "output", "");

    }
}
