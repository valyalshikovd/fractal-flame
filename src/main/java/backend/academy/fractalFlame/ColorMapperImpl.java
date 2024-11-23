package backend.academy.fractalFlame;


public class ColorMapperImpl implements ColorMapper {

    @Override
    public int mapColor(double color) {
        return (int) (0.5 - color)* Integer.MAX_VALUE;
    }
}
