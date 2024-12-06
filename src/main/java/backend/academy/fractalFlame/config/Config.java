package backend.academy.fractalFlame.config;

import java.io.InputStream;
import java.io.PrintStream;



public final class Config {
    public static final PrintStream OUT = System.out;
    public static final InputStream IN = System.in;


    //потому что не должен иметь конструктор
    private Config() {
    }
}
