package backend.academy.fractalFlame.util.juice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public final class ObjectFabric {

    private static Injector injector;

    @SuppressFBWarnings("LI_LAZY_INIT_UPDATE_STATIC")
    public static  <T> T getObject(Class<T> className) {

        if (injector == null) {
            injector  = Guice.createInjector(Stage.PRODUCTION, new GuiceModule());
            return injector.getInstance(className);
        }
        return injector.getInstance(className);
    }

    private ObjectFabric() {
    }
}
