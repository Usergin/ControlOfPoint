package dagger;

import com.gluonhq.ignite.dagger.DaggerContext;
import dagger.application.AppModule;
import dagger.network.NetworkModule;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by OldMan on 19.06.2017.
 */
public class Injector {
    private static  DaggerContext context;

    public static void setContext(DaggerContext context) {

    }

    public static void inject(Object object,  List<Object> modules) {
        new DaggerContext(object, () -> modules).init();
    }
}
