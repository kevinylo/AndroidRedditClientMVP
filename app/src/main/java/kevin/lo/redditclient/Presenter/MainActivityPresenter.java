package kevin.lo.redditclient.Presenter;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    private MainActivityPage page;

    private ArrayList<RedditListModel> models;

    private String nextPageToken;

    @Inject
    protected Api api;

    @Inject
    protected CompositeSubscription subscription;

    @Inject
    public MainActivityPresenter(MainActivityPage page) {
        this.page = page;
    }

    @Override
    public void onInit(ArrayList<RedditListModel> modelArrayList, String token) {
        this.models = modelArrayList;
        this.nextPageToken = token;

        if (models == null) {
            /**
             * No saved listings from before. Fetch listings from server.
             */
            getTopRedditListings();
        }
        else {
            /**
             * Populate the list using the listings and token saved.
             */
            page.initializeListAdapter(models, token);
        }
    }

    /**
     * Get PAGE_SIZE number of listings from server, using token whenever applicable.
     */
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
                        Log.i(TAG, "getTopRedditListings onNext");
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

    /**
     * If the list is not fully fetched, then get more listings from server.
     * Total size is specified @ MainActivity, dictated by server.
     * @param currentSize
     * @param totalSize
     */
    @Override
    public void onReceiveLoadMoreRequest(int currentSize, int totalSize) {
        if (currentSize < totalSize) {
            Log.i(TAG, "onLoadMore");
            getTopRedditListings();
        }
        else {
            Log.i(TAG, "Already have all.");
        }
    }

    /**
     * Clean up Rx to avoid memory leak
     */
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

    /**
     * Populate the model for the UI from server data.
     * @param listData
     * @return
     */
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

    /**
     * Get the full size image URL
     * @param preview
     * @return
     */
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
