package backend.academy.fractalFlame;

public class Processor {

    private final int sizeX;
    private final int sizeY;
    private final Point[][] arr;
    private final Renderer renderer = new RendererImpl();

    public Processor(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.arr = new Point[sizeX][sizeY];
    }


}
