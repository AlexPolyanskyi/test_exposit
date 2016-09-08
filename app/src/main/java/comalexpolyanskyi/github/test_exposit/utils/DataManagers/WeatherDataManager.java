package comalexpolyanskyi.github.test_exposit.utils.DataManagers;

import android.location.Location;
import org.json.JSONException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import comalexpolyanskyi.github.test_exposit.models.AnswerDataManager;
import comalexpolyanskyi.github.test_exposit.models.Weather;
import comalexpolyanskyi.github.test_exposit.utils.DownloadClient;
import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Алексей on 08.06.2016.
 */
public abstract class WeatherDataManager<T extends Weather> {

    public abstract AnswerDataManager getData(Location location, int age, String lang, String apiKey, Callback call);

    public abstract AnswerDataManager getData(String cityName, int age, String lang, String apiKey, Callback call);

    public abstract void refreshingData(String url, int age, Callback callback);

    public abstract AnswerDataManager getCacheData(String url, int age, Callback callback);

    public abstract List<T> JSONParse(String response,  List<T> weatherList) throws JSONException;

    protected void getServerData(final String u, final int age, Callback callback) {
        OkHttpClient client = new DownloadClient().getClient();
        Request request = new Request.Builder()
                .url(u)
                .cacheControl(new CacheControl.Builder().maxStale(age, TimeUnit.MINUTES).build())
                .build();
        client.newCall(request).enqueue(callback);
    }
}
