package backend.academy.fractalFlame.cmdReader;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Args {

    @Parameter(names = "--picSizeX")
    private Integer picSizeX;

    @Parameter(names = "--picSizeY")
    private Integer picSizeY;

    @Parameter(names = "--numStartedPoints")
    private Integer numStartedPoints;

    @Parameter(names = "--numAffineTransforms")
    private Integer numAffineTransforms;

    @Parameter(names = "--numTransforms")
    private Integer numTransforms;

    @Parameter(names = "--numThreads")
    private Integer numThreads;

    @Parameter(
        names = {"--nonLinearTransform", "-i"},
        description = "List of items",
        variableArity = true,
        required = true
    )
    private List<String> items = new ArrayList<>();

    @Parameter(names = "--symmetryParam")
    private Integer symmetryParam;

    @Parameter(names = "--path")
    private String path;

    @Parameter(names = "--filename")
    private String filename;
}
