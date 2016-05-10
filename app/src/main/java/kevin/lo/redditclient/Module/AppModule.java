package kevin.lo.redditclient.Module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kevin.lo.redditclient.communication.Api;


@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Api provideApi() {
        return new Api(application.getApplicationContext());
    }

}
