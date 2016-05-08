package kevin.lo.redditclient.Component;


import dagger.Subcomponent;
import kevin.lo.redditclient.Module.MainActivityModule;
import kevin.lo.redditclient.Presenter.MainActivityPresenter;
import kevin.lo.redditclient.activity.MainActivity;


@Subcomponent(
        modules = {
                MainActivityModule.class
        }
)
public interface MainActivityComponent {

    MainActivity inject(MainActivity activity);

    MainActivityPresenter inject(MainActivityPresenter presenter);

}

