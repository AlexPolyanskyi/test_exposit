package comalexpolyanskyi.github.test_exposit.utils.DataManagers;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import comalexpolyanskyi.github.test_exposit.models.AnswerDataManager;
import comalexpolyanskyi.github.test_exposit.models.TodayWeather;
import okhttp3.Callback;

/**
 * Created by Алексей on 08.06.2016.
 */
public class TodayWeatherDataManager extends WeatherDataManager<TodayWeather> {
    private final String URL = "http://api.openweathermap.org/data/2.5/weather?units=metric";

    @Override
    public AnswerDataManager getData(Location location, final int age, String lang, String apiKey, Callback call){
        String url = URL+"&lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&lang="+lang+"&appid="+apiKey+"";
        super.getServerData(url, age, call);
        return new AnswerDataManager(url, age);
    }
    @Override
    public AnswerDataManager getData(String cityName, final int age, String lang, String apiKey, Callback call){
        String url = URL+"&q="+cityName+"&lang="+lang+"&appid="+apiKey+"";
        super.getServerData(url, age, call);
        return new AnswerDataManager(url, age);
    }

    @Override
    public void refreshingData(String url, int age, Callback callback) {
        super.getServerData(url, age, callback);
    }

    @Override
    public AnswerDataManager getCacheData(String url, int age, Callback callback) {
        super.getServerData(url, age, callback);
        return new AnswerDataManager(url, age);
    }

    @Override
    public List<TodayWeather> JSONParse(String response, List<TodayWeather> list) throws JSONException {
        Log.i("123", response);
        JSONObject jsonResult = new JSONObject(response);
        JSONArray jsonWeather =jsonResult.getJSONArray("weather");
        JSONObject jsonWeatherObj =  jsonWeather.getJSONObject(0);
        JSONObject jsonMain = jsonResult.getJSONObject("main");
        JSONObject jsonWind = jsonResult.getJSONObject("wind");
        JSONObject jsonClouds = jsonResult.getJSONObject("clouds");
        list.clear();
        list.add(new TodayWeather(jsonWeatherObj.getString("description"), jsonWeatherObj.getString("icon"), jsonMain.getDouble("temp"),
                jsonMain.getDouble("pressure"), jsonMain.getInt("humidity"), jsonWind.getInt("speed"), jsonClouds.getInt("all")));
        return list;
    }
}
