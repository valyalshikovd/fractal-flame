package backend.academy.fractalFlame.correction;

public class GammaCorrection implements Correction {

    private final double gamma;

    public GammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public double correct(double bright) {
        double res = bright / RGB_BYTES_NUM;
        res = Math.pow(res, 1 / gamma) * RGB_BYTES_NUM;
        if (res > RGB_BYTES_NUM) {
            res = RGB_BYTES_NUM;
        }
        return res;
    }
}
