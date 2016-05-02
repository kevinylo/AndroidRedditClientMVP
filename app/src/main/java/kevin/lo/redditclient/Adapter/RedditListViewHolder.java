package kevin.lo.redditclient.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import kevin.lo.redditclient.Model.RedditListModel;
import kevin.lo.redditclient.R;


public class RedditListViewHolder extends RecyclerView.ViewHolder {

    private static final String COMMENTS = " comments";

    public interface OnThumbnailClickListener {
        void onThumbNailClicked(String url);
    }

    private Context context;

    private View listView;

    private TextView title;

    private TextView author;

    private TextView date;

    private TextView numberOfComments;

    private ImageView thumbnail;

    private OnThumbnailClickListener onThumbnailClickListener;

    public RedditListViewHolder(Context context, View itemView, OnThumbnailClickListener onThumbnailClickListener) {
        super(itemView);
        this.context = context;
        this.listView = itemView;
        this.onThumbnailClickListener = onThumbnailClickListener;
    }

    public void initialize(final RedditListModel model) {
        if (model != null) {
            title = (TextView) listView.findViewById(R.id.title);
            title.setText(model.getTitle());

            author = (TextView) listView.findViewById(R.id.author);
            author.setText(model.getAuthor());

            date = (TextView) listView.findViewById(R.id.date);
            date.setText(model.getDate());

            numberOfComments = (TextView) listView.findViewById(R.id.numberOfComments);
            numberOfComments.setText(model.getNumberOfComments() + COMMENTS);

            thumbnail = (ImageView) listView.findViewById(R.id.thumbnail);
            if (model.getThumbnailUrl() != null) {
                if (thumbnail.getVisibility() == View.GONE) {
                    thumbnail.setVisibility(View.VISIBLE);
                }
                PicassoFactory.INSTANCE
                        .getPicasso(context)
                        .load(model.getThumbnailUrl())
                        .into(thumbnail);

                thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onThumbnailClickListener.onThumbNailClicked(model.getPreviewSourceUrl());
                    }
                });
            }
            else {
                // hide the thumb nail image if there are no larger image available to show
                thumbnail.setVisibility(View.GONE);
            }
        }
    }

}
