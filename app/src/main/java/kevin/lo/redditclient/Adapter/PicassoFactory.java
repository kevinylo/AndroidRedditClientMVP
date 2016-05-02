package kevin.lo.redditclient.Adapter;


import android.content.Context;

import com.squareup.picasso.Picasso;

public enum PicassoFactory {
    INSTANCE;

    private Picasso picasso;

    public Picasso getPicasso(final Context context) {
        if (picasso == null) {
            picasso = new Picasso.Builder(context.getApplicationContext())
                    .build();
        }
        return picasso;
    }
}
