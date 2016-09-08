package comalexpolyanskyi.github.test_exposit.utils.DataManagers;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import comalexpolyanskyi.github.test_exposit.models.AnswerDataManager;
import comalexpolyanskyi.github.test_exposit.models.FiveDaysWeather;
import okhttp3.Callback;

/**
 * Created by Алексей on 18.06.2016.
 */
public class FiveDsWeatherDataManager extends WeatherDataManager<FiveDaysWeather> {

    private final String URL = "http://api.openweathermap.org/data/2.5/forecast?units=metric";

    @Override
    public AnswerDataManager getData(Location location, int age, String lang, String apiKey, Callback call) {
        String url = URL +"&lon="+location.getLongitude()+"&lat="+location.getLatitude()+"&appid="+apiKey+"&lang=ru";
        super.getServerData(url, age, call);
        Log.i("123", url);
        return new AnswerDataManager(url, age);
    }

    @Override
    public AnswerDataManager getData(String cityName, int age, String lang, String apiKey, Callback call) {
        String url = URL +"&q="+cityName+"&appid="+apiKey+"&lang=ru";
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
    public List<FiveDaysWeather> JSONParse(String response, List<FiveDaysWeather> weatherList) throws JSONException {
        weatherList.clear();
        Log.i("123", response);
        JSONObject jsonData = new JSONObject(response);
        int countItem = jsonData.getInt("cnt");
        JSONArray listJson = jsonData.getJSONArray("list");
        for(int i = 0; i < countItem; i++){
            JSONObject jsonItem = listJson.getJSONObject(i);
            JSONObject jsonMain = jsonItem.getJSONObject("main");
            JSONArray jsonWeatherA = jsonItem.getJSONArray("weather");
            JSONObject jsonWeatherO = jsonWeatherA.getJSONObject(0);
            JSONObject jsonCloud = jsonItem.getJSONObject("clouds");
            JSONObject jsonWind = jsonItem.getJSONObject("wind");
            weatherList.add(new FiveDaysWeather(jsonItem.getString("dt_txt"), jsonWeatherO.getString("description"), jsonWeatherO.getString("icon"),
                    jsonMain.getDouble("temp"), jsonMain.getDouble("pressure"), jsonMain.getInt("humidity"), jsonWind.getInt("speed"),
                    jsonCloud.getInt("all")));
        }
        return weatherList;
    }
}
