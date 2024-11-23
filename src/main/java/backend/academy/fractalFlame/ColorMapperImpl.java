package backend.academy.fractalFlame;

import java.awt.Color;

public class ColorMapperImpl implements ColorMapper {



    @Override
    public Color mapColor(int value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Значение должно быть в диапазоне от 0 до 1.");
        }

        int red = (int) (value * 255);
        int blue = (int) ((1 - value) * 255);
        int green = 0;

        return new Color(red, green, blue);
    }
}
