package kevin.lo.redditclient.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kevin.lo.redditclient.Model.RedditListModel;
import kevin.lo.redditclient.R;

public class RedditListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RedditListAdapter.class.getSimpleName();

    private RedditListViewHolder.OnThumbnailClickListener onThumbnailClickListener;

    private ArrayList<RedditListModel> models;

    public RedditListAdapter(ArrayList<RedditListModel> modelList) {
        this.models = modelList;
    }

    public void addItems(List<RedditListModel> modelList) {
        Log.e(TAG, "addItems: added " + modelList.size());
        Log.e(TAG, "total size " + models.size());
        this.models.addAll(modelList);
    }

    public ArrayList<RedditListModel> getItems() {
        return this.models;
    }

    public void setOnThumbnailClickListener(RedditListViewHolder.OnThumbnailClickListener onThumbnailClickListener) {
        this.onThumbnailClickListener = onThumbnailClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new RedditListViewHolder(parent.getContext(), view, onThumbnailClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RedditListViewHolder redditListViewHolder =  (RedditListViewHolder) holder;
        redditListViewHolder.initialize(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
