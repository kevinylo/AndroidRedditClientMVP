package kevin.lo.redditclient.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import kevin.lo.redditclient.Adapter.PicassoFactory;
import kevin.lo.redditclient.R;

public class ImageActivity extends AppCompatActivity{

    public static final String BUNDLE_URL = "url";

    private String url;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_layout);

        image = (ImageView) findViewById(R.id.image);

        url = getIntent().getStringExtra(BUNDLE_URL);

        populateImage(url);
    }

    private void populateImage(String url) {
        PicassoFactory.INSTANCE
                .getPicasso(this)
                .load(url)
                .into(image);
    }
}
