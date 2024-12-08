package backend.academy.fractalFlame.correction;

public class LogarithmCorrection implements Correction {

    private final double coeff;

    public LogarithmCorrection(double coeff) {
        this.coeff = coeff;
    }

    @Override
    public double correct(double bright) {
        double res = Math.log(1 + coeff * bright) * RGB_BYTES_NUM;
        if (res > RGB_BYTES_NUM) {
            res = RGB_BYTES_NUM;
        }
        return res;
    }
}
