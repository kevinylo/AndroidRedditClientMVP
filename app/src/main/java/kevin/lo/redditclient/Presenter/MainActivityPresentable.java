package kevin.lo.redditclient.Presenter;


import java.util.ArrayList;

import kevin.lo.redditclient.Model.RedditListModel;
import kevin.lo.redditclient.communication.Api;

public interface MainActivityPresentable {

    void onInit(ArrayList<RedditListModel> modelArrayList, String token);

    void getTopRedditListings();

    void onReceiveLoadMoreRequest(int currentSize, int totalSize);

    void onDestroy();

}
