package kevin.lo.redditclient.communication;


import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.util.concurrent.TimeUnit;


public class OkHttpClientFactory {

    private static final long TIMEOUT_MS = 20 * 1000;

    // Set 10 MB of cache.
    private static final long MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private OkHttpClientFactory() {
    }

    public static OkHttpClient provideOkHttpClientWithCache(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        Cache cache = null;
        try {
            cache = provideCache(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cache != null) {
            okHttpClient.setCache(cache);
        }
        return okHttpClient;
    }

    private static Cache provideCache(Context context) throws IOException {
        File cacheDir = context.getCacheDir();
        return new Cache(cacheDir, MAX_CACHE_SIZE);
    }
}
