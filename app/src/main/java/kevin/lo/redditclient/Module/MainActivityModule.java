package kevin.lo.redditclient.Module;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import kevin.lo.redditclient.Presenter.MainActivityPresentable;
import kevin.lo.redditclient.Presenter.MainActivityPresenter;
import kevin.lo.redditclient.activity.MainActivityPage;
import kevin.lo.redditclient.communication.Api;
import rx.subscriptions.CompositeSubscription;


@Module
public class MainActivityModule {

    private MainActivityPage page;

    @Inject
    public MainActivityModule(MainActivityPage page) {
        this.page = page;
    }

    @Provides
    public MainActivityPresentable provideMainActivityPresentable() {
        return new MainActivityPresenter(page);
    }

    @Provides
    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
