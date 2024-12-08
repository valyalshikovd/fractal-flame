package backend.academy.fractalFlame;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class PixelTest {

    @Test
    public void addColorTest(){


        Pixel p = new Pixel();

        p.addHit(Color.WHITE);

        assertEquals(255, p.b());
        assertEquals(255, p.r());
        assertEquals(255, p.g());
    }


    @Test
    public void addColorTest2(){


        Pixel p = new Pixel();

        p.addHit(Color.WHITE);

        p.addHit(Color.YELLOW);

        assertEquals(127.5, p.b());
        assertEquals(255, p.r());
        assertEquals(255, p.g());
    }


}
