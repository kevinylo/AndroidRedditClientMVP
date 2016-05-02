package kevin.lo.redditclient.Presenter;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kevin.lo.redditclient.Model.RedditListModel;
import kevin.lo.redditclient.Util.TimeConverter;
import kevin.lo.redditclient.activity.MainActivityPage;
import kevin.lo.redditclient.communication.Api;
import kevin.lo.redditclient.reddis.Child;
import kevin.lo.redditclient.reddis.Data_;
import kevin.lo.redditclient.reddis.Preview;
import kevin.lo.redditclient.reddis.TopListing;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class MainActivityPresenter implements MainActivityPresentable {

    private static final String TAG = MainActivityPresenter.class.getSimpleName();

    private static final String PAGE_SIZE = "10";

    private Api api;

    private MainActivityPage page;

    private ArrayList<RedditListModel> models;

    private String nextPageToken;

    private CompositeSubscription subscription = new CompositeSubscription();

    public MainActivityPresenter(MainActivityPage page) {
        this.page = page;
    }

    @Override
    public void onInit(Api api, ArrayList<RedditListModel> modelArrayList, String token) {
        this.api = api;
        this.models = modelArrayList;
        this.nextPageToken = token;

        if (models == null) {
            getTopRedditListings();
        }
        else {
            page.initializeListAdapter(models, token);
        }
    }

    @Override
    public void getTopRedditListings() {
        subscription.add(
                api.getTopRedditListings(PAGE_SIZE, nextPageToken).subscribe(new Subscriber<TopListing>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "getTopRedditListings onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "getTopRedditListings onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(TopListing topListing) {
                        models = buildModelFromDataReturned(topListing);

                        if (nextPageToken == null) {
                            nextPageToken = topListing.getData().getAfter();
                            page.initializeListAdapter(models, nextPageToken);
                        }
                        else {
                            nextPageToken = topListing.getData().getAfter();
                            page.updateListAdapter(models, nextPageToken);
                        }
                    }
                })
        );
    }

    @Override
    public void onReceiveLoadMoreRequest(int currentSize, int totalSize) {
        if (currentSize < totalSize) {
            Log.e(TAG, "onLoadMore");
            getTopRedditListings();
        }
        else {
            Log.e(TAG, "Already have all.");
        }
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }

    private ArrayList<RedditListModel> buildModelFromDataReturned(TopListing topListing) {

        ArrayList<RedditListModel> modelList = new ArrayList<>();
        List<Child> children = topListing.getData().getChildren();

        for (Child child : children) {
            modelList.add(setModel(child.getData()));
        }
        return modelList;
    }

    private RedditListModel setModel(Data_ listData) {
        RedditListModel model = new RedditListModel();
        model.setTitle(listData.getTitle());
        model.setAuthor(listData.getAuthor());
        model.setDate(TimeConverter.getTimeAgo(listData.getCreatedUtc()));

        Preview preview = listData.getPreview();
        if (preview != null) {
            model.setThumbnailUrl(listData.getThumbnail());
            model.setPreviewSourceUrl(getPreviewSourceImageUrl(preview));
        }
        else {
            model.setThumbnailUrl(null);
        }
        model.setNumberOfComments(listData.getNumComments());
        return model;
    }

    private String getPreviewSourceImageUrl(Preview preview) {
        if (preview.getImages() != null && preview.getImages().get(0) != null &&
                preview.getImages().get(0).getSource() != null) {
            return preview.getImages().get(0).getSource().getUrl();
        }
        else {
            return null;
        }
    }
}
