package backend.academy.fractalFlame;


public class ColorMapperImpl implements ColorMapper {

    @Override
    public int mapColor(double color) {

        final double SHIFT_TO_NEGATIVE_AREA = 0.5;
        final int MULTIPLIER_TO_INTEGER = 2;

        return (int)((SHIFT_TO_NEGATIVE_AREA - color) * MULTIPLIER_TO_INTEGER * Integer.MAX_VALUE);
    }
}
