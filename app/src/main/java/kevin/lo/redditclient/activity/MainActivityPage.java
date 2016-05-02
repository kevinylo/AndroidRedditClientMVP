package kevin.lo.redditclient.activity;


import java.util.ArrayList;

import kevin.lo.redditclient.Model.RedditListModel;

public interface MainActivityPage {

    void initializeListAdapter(ArrayList<RedditListModel> modelList, String nextPageToken);

    void updateListAdapter(ArrayList<RedditListModel> modelList, String nextPageToken);

}
