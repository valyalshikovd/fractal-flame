package backend.academy.fractalFlame.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.security.SecureRandom;


public class RandomShellImpl implements RandomShell {

    private SecureRandom random;

    @SuppressFBWarnings("DMI_RANDOM_USED_ONLY_ONCE")
    public int get(int size) {
        if (random == null) {
            this.random = new SecureRandom();
        }
        return random.nextInt(size);
    }

    @Override
    public double getDouble() {
        if (random == null) {
            this.random = new SecureRandom();
        }
        return random.nextDouble();
    }
}
