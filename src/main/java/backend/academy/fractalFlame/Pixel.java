package backend.academy.fractalFlame;

import lombok.Getter;
import java.awt.Color;

@Getter
public class Pixel {

    private double r = 0;
    private double g = 0;
    private double b = 0;
    private int countPoints = 0;

    public void addHit(Color c){


        if(countPoints < 255){
            countPoints++;
        }

        r = (c.getRed() + r) / 2 ;
        g = (c.getGreen() + g) / 2;
        b = (c.getBlue() + b) / 2;

    }
}
