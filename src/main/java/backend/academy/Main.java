package backend.academy;

import backend.academy.fractalFlame.transformation.AffineTransformation;
import backend.academy.fractalFlame.Plot;
import backend.academy.fractalFlame.Processor;
import backend.academy.fractalFlame.transformation.DiscTransform;
import lombok.experimental.UtilityClass;
import java.awt.Color;
import java.util.Random;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        Plot plot = new Plot(1000, 1000);
        Processor processor = new Processor(plot, 1, 1);
        processor.genAffineTransformation(20);
        processor.addTransformation(new DiscTransform(3, 3));
        processor.getStartedPoint(1000);
        processor.applyTransformations(10000);
        processor.render("outputG");

        }
}
