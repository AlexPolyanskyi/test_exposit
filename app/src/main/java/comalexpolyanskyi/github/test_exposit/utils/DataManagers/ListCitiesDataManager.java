package comalexpolyanskyi.github.test_exposit.utils.DataManagers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.concurrent.TimeUnit;
import comalexpolyanskyi.github.test_exposit.utils.DownloadClient;
import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Алексей on 17.06.2016.
 */
public class ListCitiesDataManager {

    private String URL = "http://api.openweathermap.org/data/2.5/find?cnt=10";
    public void getServerData(String city, String lang, String appId, Callback callback) {
        URL = URL + "&q="+city+"&lang="+lang+"&appid="+appId;
        OkHttpClient client = new DownloadClient().getClient();
        Request request = new Request.Builder()
                .url(URL)
                .cacheControl(new CacheControl.Builder().maxStale(12, TimeUnit.HOURS).build())
                .build();
        client.newCall(request).enqueue(callback);
    }

    public List<String> JsonParse(String response, List<String> dataList) throws JSONException {
        JSONObject data = new JSONObject(response);
        int countRecords = data.getInt("count");
        JSONArray jsonArray = data.getJSONArray("list");
        dataList.clear();
        for(int i = 0; i < countRecords; i++){
            JSONObject elementObj = jsonArray.getJSONObject(i);
            String cityName = elementObj.getString("name");
            String countryCode = elementObj.getJSONObject("sys").getString("country");
            cityName = cityName +", "+ countryCode;
            dataList.add(cityName);
        }
        return dataList;
    }
}
