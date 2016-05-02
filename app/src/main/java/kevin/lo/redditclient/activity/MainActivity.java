package kevin.lo.redditclient.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import kevin.lo.redditclient.Adapter.EndlessRecyclerViewScrollListener;
import kevin.lo.redditclient.Adapter.RedditListAdapter;
import kevin.lo.redditclient.Adapter.RedditListViewHolder;
import kevin.lo.redditclient.Model.RedditListModel;
import kevin.lo.redditclient.Presenter.MainActivityPresentable;
import kevin.lo.redditclient.Presenter.MainActivityPresenter;
import kevin.lo.redditclient.R;
import kevin.lo.redditclient.communication.Api;


public class MainActivity extends AppCompatActivity implements MainActivityPage,
        RedditListViewHolder.OnThumbnailClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NUMBER_OF_REDDIT_TOP_LISTINGS = 50;

    private static final String BUNDLE_KEY_MODEL_LIST = "bundle.model_list";

    private static final String BUNDLE_KEY_NEXT_PAGE_TOKEN = "bundle.next_page_token";

    private RecyclerView recyclerView;

    private RedditListAdapter redditListAdapter;

    private Api api;

    private MainActivityPresentable presenter;

    private ArrayList<RedditListModel> modelList;

    private String nextPageToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            this.modelList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_MODEL_LIST);
            this.nextPageToken = savedInstanceState.getString(BUNDLE_KEY_NEXT_PAGE_TOKEN);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if (redditListAdapter != null) {
            modelList = redditListAdapter.getItems();
        }
        savedInstanceState.putParcelableArrayList(BUNDLE_KEY_MODEL_LIST, modelList);
        savedInstanceState.putString(BUNDLE_KEY_NEXT_PAGE_TOKEN, nextPageToken);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void init() {
        // Initialize the api
        api = new Api(this);

        // Initialize presenter
        presenter = new MainActivityPresenter(this);

        // Initialize the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                presenter.onReceiveLoadMoreRequest(redditListAdapter.getItemCount(), NUMBER_OF_REDDIT_TOP_LISTINGS);
            }
        });

        presenter.onInit(api, modelList, nextPageToken);
    }

    @Override
    public void initializeListAdapter(ArrayList<RedditListModel> modelList, String nextPageToken) {
        this.nextPageToken = nextPageToken;
        redditListAdapter = new RedditListAdapter(modelList);
        redditListAdapter.setOnThumbnailClickListener(MainActivity.this);
        recyclerView.setAdapter(redditListAdapter);
    }

    @Override
    public void updateListAdapter(ArrayList<RedditListModel> modelList, String nextPageToken) {
        this.nextPageToken = nextPageToken;
        redditListAdapter.addItems(modelList);
        redditListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onThumbNailClicked(String url) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra(ImageActivity.BUNDLE_URL, url);
        startActivity(intent);
    }

}
