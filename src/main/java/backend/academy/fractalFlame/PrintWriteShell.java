package backend.academy.fractalFlame;



import backend.academy.fractalFlame.config.Config;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class PrintWriteShell {
    private PrintWriteShell() {
    }

    private static PrintWriter out;

    public static void println(String s) {
        checkWriter();

        out.println(s);
    }

    public static void println() {
        checkWriter();

        out.println();
    }

    private static void checkWriter() {
        if (out == null) {
            out = new PrintWriter(Config.OUT, true, StandardCharsets.UTF_8);
        }
    }
}
