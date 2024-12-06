package backend.academy.fractalFlame.transformation;

public class TransformFactory {


    public static Transformation getTransform(TransformationDTO transformationDTO) {

        return switch (transformationDTO.name()){
            case "disc" -> new DiscTransform(transformationDTO.optionalParam(), transformationDTO.weight());
            case "exponent" -> new ExponentTransform(transformationDTO.weight());
            case "handkerchief" -> new HandkerchiefTransformation(transformationDTO.weight());
            case "heart" -> new HeartTransform(transformationDTO.weight());
            case "horsehoe" -> new HorsehoeTransform(transformationDTO.weight());
            case "polar" -> new PolarTransform(transformationDTO.optionalParam(), transformationDTO.weight());
            case "sin" -> new SinusoidalTransform(transformationDTO.weight());
            case "sperical" -> new SphericalTransform(transformationDTO.weight());
            case "swirl" -> new SwirlTransform(transformationDTO.weight());
            default -> null;
        };
    }
}
