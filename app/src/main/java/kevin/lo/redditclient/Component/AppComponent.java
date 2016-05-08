package kevin.lo.redditclient.Component;


import javax.inject.Singleton;

import dagger.Component;
import kevin.lo.redditclient.Module.AppModule;
import kevin.lo.redditclient.Module.MainActivityModule;

@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {

    MainActivityComponent with(MainActivityModule module);

}