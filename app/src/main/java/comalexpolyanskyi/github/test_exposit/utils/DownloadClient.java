package comalexpolyanskyi.github.test_exposit.utils;

import android.app.Activity;
import java.io.File;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Алексей on 17.06.2016.
 */
public class DownloadClient {
    private static OkHttpClient client = null;
    public  void initCache(Activity activity){
        File httpCacheDir = new File(activity.getApplicationContext().getCacheDir(), "http");
        long httpCacheSize = 2 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDir, httpCacheSize);
        client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    public OkHttpClient getClient() {
        return client;
    }
}
