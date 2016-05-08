package kevin.lo.redditclient.Component;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import kevin.lo.redditclient.Module.AppModule;


public class Components {

    private AppComponent appComponent;

    private static Components singleton;

    public static void initialize(@NonNull Application application) {
        if (singleton != null) {
            throw new RuntimeException("Components has already been initialized!");
        }
        singleton = new Components(application);
    }

    public static Components get() {
        return singleton;
    }

    private Components(Application application) {

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(application))
                .build();
    }

    @VisibleForTesting
    public static void setComponents(Components components) {
        singleton = components;
    }

    public AppComponent app() {
        return appComponent;
    }
}
