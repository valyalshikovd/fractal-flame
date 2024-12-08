package backend.academy.fractalFlame.correction;

public final class CorrectionFactory {

    private CorrectionFactory() {
    }

    public static Correction getCorrection(CorrectionDto correction) {
        return switch (correction.name()) {
            case "gamma" -> new GammaCorrection(correction.param());
            case "logarithm" -> new LogarithmCorrection(correction.param());
            default -> throw new IllegalStateException("Неизвестное значение: " + correction.name());
        };
    }
}
