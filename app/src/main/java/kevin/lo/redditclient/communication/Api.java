package kevin.lo.redditclient.communication;


import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import kevin.lo.redditclient.BuildConfig;

import kevin.lo.redditclient.reddis.TopListing;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Api {

    private static final String REDDIT_URL = "http://www.reddit.com";

    private RedditApi redditApi;

    private Context appContext;

    private OkHttpClient httpClient;

    public Api(Context context) {
        this.appContext = context.getApplicationContext();
        this.httpClient = OkHttpClientFactory.provideOkHttpClientWithCache(appContext);

        buildRedditApi();
    }

    private void buildRedditApi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(REDDIT_URL)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setClient(new OkClient(httpClient))
                .build();
        redditApi = restAdapter.create(RedditApi.class);
    }

    public Observable<TopListing> getTopRedditListings(final String pageSize, final String token) {
        return Observable.create(new Observable.OnSubscribe<TopListing>() {
            @Override
            public void call(Subscriber<? super TopListing> subscriber) {
                final TopListing topListing;
                try {
                    topListing = redditApi.getTop(pageSize, token);
                    subscriber.onNext(topListing);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
