package backend.academy.fractalFlame.cmdReader;

import backend.academy.fractalFlame.PrintWriteShell;
import backend.academy.fractalFlame.Processor;
import backend.academy.fractalFlame.correction.Correction;
import backend.academy.fractalFlame.correction.CorrectionDto;
import backend.academy.fractalFlame.correction.CorrectionFactory;
import backend.academy.fractalFlame.transformation.TransformationDTO;
import com.beust.jcommander.JCommander;

@SuppressWarnings("MultipleStringLiterals")
public class Reader {

    private Args args;

    public Reader(String[] args) {

        Args analyzerArgs = new Args();
        JCommander jCommander = JCommander.newBuilder()
            .addObject(analyzerArgs)
            .build();
        try {
            jCommander.parse(args);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            jCommander.usage();
        }

        this.args = analyzerArgs;

    }

    public void start() {
        long startTime = System.nanoTime();

        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder =
            Processor.ProcessorConfiguration.builder();

        addPicSizeX(builder, args.picSizeX());
        addPicSizeY(builder, args.picSizeY());
        addNumStartedPoint(builder, args.numStartedPoints());
        addNumAffineTransforms(builder, args.numAffineTransforms());
        addNumTransforms(builder, args.numTransforms());
        addNumThreads(builder, args.numThreads());

        if (args.items() != null) {
            builder.nonLinearTransforms(
                args.items().stream().map(s -> {
                    String[] sArr = s.split(":");
                    return new TransformationDTO(
                        sArr[0],
                        Double.parseDouble(sArr[1]),
                        Double.parseDouble(sArr[2])
                    );
                }).toArray(TransformationDTO[]::new)
            );
        }

        addSymmetryParam(builder, args.symmetryParam());
        addPath(builder, args.path());
        addFileName(builder, args.filename());
        addCorrection(builder, args.correction());
        addNumRenderThreads(builder, args.numThreads());

        Processor processor = builder.build().createProcessor();
        processor.applyTransformations();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        final int nanosecondsToMilliseconds = 1_000_000;

        PrintWriteShell.println("Время генерации: " + duration / nanosecondsToMilliseconds);

        long startTimeWrite = System.nanoTime();

        processor.render();

        long endTimeWrite = System.nanoTime();
        long durationWrite = endTimeWrite - startTimeWrite;

        PrintWriteShell.println("Время отрисовки: " + durationWrite / nanosecondsToMilliseconds);
    }

    private void addPicSizeX(Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, Integer picSizeX) {

        final int INITIAL_PIC_SIZE_VALUE = 1000;

        if (picSizeX != null) {
            builder.pictureSizeX(picSizeX);
        } else {
            builder.pictureSizeX(INITIAL_PIC_SIZE_VALUE);
        }
    }

    private void addPicSizeY(Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, Integer picSizeY) {

        final int INITIAL_PIC_SIZE_VALUE = 1000;

        if (picSizeY != null) {
            builder.pictureSizeY(picSizeY);
        } else {
            builder.pictureSizeY(INITIAL_PIC_SIZE_VALUE);
        }
    }

    private void addNumStartedPoint(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder,
        Integer numStartedPoint
    ) {

        final int INITIAL_NUM_STARTED_POINTS_VALUE = 1000;

        if (numStartedPoint != null) {
            builder.numStartedPoints(numStartedPoint);
        } else {
            builder.numStartedPoints(INITIAL_NUM_STARTED_POINTS_VALUE);
        }
    }

    private void addNumAffineTransforms(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder,
        Integer numAffineTransforms
    ) {

        final int INITIAL_NUM_AFFINE_TRANSFORMS_VALUE = 20;

        if (numAffineTransforms != null) {
            builder.numAffineTransform(numAffineTransforms);
        } else {
            builder.numAffineTransform(INITIAL_NUM_AFFINE_TRANSFORMS_VALUE);
        }
    }

    private void addNumTransforms(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, Integer numTransforms
    ) {

        final int INITIAL_NUM_TRANSFORMS_VALUE = 300000;

        if (numTransforms != null) {
            builder.numTransformation(numTransforms);
        } else {
            builder.numTransformation(INITIAL_NUM_TRANSFORMS_VALUE);
        }
    }

    private void addNumThreads(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, Integer numThreads
    ) {

        final int INITIAL_NUM_THREADS_VALUE = 1;

        if (numThreads != null) {
            builder.numberOfThreads(numThreads);
        } else {
            builder.numberOfThreads(INITIAL_NUM_THREADS_VALUE);
        }
    }

    private void addSymmetryParam(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, Integer symmetryParam
    ) {

        final int INITIAL_SYMMETRY_PARAM_VALUE = 1;

        if (symmetryParam != null) {
            builder.symmetryParam(symmetryParam);
        } else {
            builder.symmetryParam(INITIAL_SYMMETRY_PARAM_VALUE);
        }
    }

    private void addPath(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, String path
    ) {

        final String INITIAL_PATH_VALUE = "";

        if (path != null) {
            builder.path(path);
        } else {
            builder.path(INITIAL_PATH_VALUE);
        }
    }

    private void addFileName(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, String filename
    ) {

        final String INITIAL_FILENAME_VALUE = "output";

        if (filename != null) {
            builder.fileName(filename);
        } else {
            builder.fileName(INITIAL_FILENAME_VALUE);
        }
    }

    private void addCorrection(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder, String correctionString
    ) {
        Correction correction;
        try {
            String[] arrString = correctionString.split(":");
            correction = CorrectionFactory.getCorrection(
                new CorrectionDto(arrString[0], Double.parseDouble(arrString[1]))
            );
        } catch (Exception e) {
            final double gammaDefaultValue = 5.0;
            builder.correction(CorrectionFactory.getCorrection(
                new CorrectionDto("gamma", gammaDefaultValue))
            );
            return;
        }
        builder.correction(correction);
    }

    private void addNumRenderThreads(
        Processor.ProcessorConfiguration.ProcessorConfigurationBuilder builder,
        Integer numRenderThreads
    ) {
        final int INITIAL_NUM_RENDER_THREADS_VALUE = 1;

        if (numRenderThreads != null) {
            builder.numRenderThreads(numRenderThreads);
        } else {
            builder.numRenderThreads(INITIAL_NUM_RENDER_THREADS_VALUE);
        }
    }
}
