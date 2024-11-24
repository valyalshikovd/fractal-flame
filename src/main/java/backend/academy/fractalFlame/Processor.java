package backend.academy.fractalFlame;

public class Processor {

    private final Plot plot;
    private final Renderer renderer = new RendererImpl();

    public Processor(Plot plot) {
        this.plot = plot;
    }


}
