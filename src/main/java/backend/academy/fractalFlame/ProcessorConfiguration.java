package backend.academy.fractalFlame;

import backend.academy.fractalFlame.simmetryParameter.RadialSymmetryParam;
import backend.academy.fractalFlame.transformation.TransformFactory;
import backend.academy.fractalFlame.transformation.Transformation;
import backend.academy.fractalFlame.transformation.TransformationDTO;
import lombok.Builder;

@Builder
public class ProcessorConfiguration {

    private int pictureSizeX = 1000;
    private int pictureSizeY = 1000;
    private int numberOfThreads = 1;
    private int symmetryParam = 1;
    private int numStartedPoints = 1000;
    private int numAffineTransform = 20;
    private int numTransformation = 300000;
    private TransformationDTO[] nonLinearTransforms;
    private final int borderCalculatingAreaX = 1;
    private final int borderCalculatingAreaY = 1;
    private final String fileName;
    private final String path;

    public Processor createProcessor() {

        Plot plot = new Plot(pictureSizeX, pictureSizeY, numberOfThreads);
        Processor processor = new Processor(plot, borderCalculatingAreaX, borderCalculatingAreaY, numberOfThreads, new RendererImpl(), fileName);
        processor.genAffineTransformation(numAffineTransform);

        if (symmetryParam > 1) {
            processor.addSymmetryParam(new RadialSymmetryParam(symmetryParam));
        }

        for (TransformationDTO transformationDTO : nonLinearTransforms) {
            Transformation transformation = TransformFactory.getTransform(transformationDTO);
            if (transformation == null) {
                continue;
            }
            processor.addTransformation(transformation);
        }

        processor.getStartedPoint(numStartedPoints);
        processor.applyTransformations(numTransformation);

        return processor;
    }

}
