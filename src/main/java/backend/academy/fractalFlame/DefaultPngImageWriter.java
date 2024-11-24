package backend.academy.fractalFlame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DefaultPngImageWriter implements ImageWriter {

    private static final String EXTENSION = "png";

    @Override
    public void writeImage(BufferedImage image, String filename, String path) {
        try {
            File outputFile = new File(filename + "." + EXTENSION);
            ImageIO.write(image, EXTENSION, outputFile);
        } catch (IOException e) {
            //todo
        }
    }
}
