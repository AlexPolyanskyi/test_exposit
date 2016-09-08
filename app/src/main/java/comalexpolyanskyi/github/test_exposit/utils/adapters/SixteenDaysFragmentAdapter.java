package comalexpolyanskyi.github.test_exposit.utils.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import comalexpolyanskyi.github.test_exposit.R;
import comalexpolyanskyi.github.test_exposit.models.SixteenDaysWeather;
import comalexpolyanskyi.github.test_exposit.models.Weather;

/**
 * Created by Алексей on 19.06.2016.
 */
    public class SixteenDaysFragmentAdapter extends RecyclerView.Adapter<SixteenDaysFragmentAdapter.MyViewHolder> {
        private List<? extends Weather> data = null;
        private boolean loading = false;
        private Activity activity;
        private View view;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView dateText;
            private TextView tempText;
            private TextView weatherText;
            private TextView cloudText;
            private TextView humidityText;
            private TextView pressureText;
            private TextView windSpeedText;
            private ImageView imageView;
            public MyViewHolder(View view) {
                super(view);
                dateText = (TextView) view.findViewById(R.id.date_weather);
                imageView= (ImageView) view.findViewById(R.id.weather_item_image);
                tempText = (TextView) view.findViewById(R.id.temperature_item);
                weatherText = (TextView) view.findViewById(R.id.weather_item);
                cloudText = (TextView) view.findViewById(R.id.cloudiness_item);
                humidityText = (TextView) view.findViewById(R.id.humidity_item);
                pressureText = (TextView) view.findViewById(R.id.pressure_item);
                windSpeedText = (TextView) view.findViewById(R.id.wind_speed_item);
            }
        }
        public SixteenDaysFragmentAdapter(Activity activity, List<? extends Weather> data, View view) {
            this.activity = activity;
            this.data = data;
            this.view = view;
        }

        @Override
        public SixteenDaysFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.weather_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SixteenDaysFragmentAdapter.MyViewHolder holder, int position) {
            SixteenDaysWeather weather = (SixteenDaysWeather) data.get(position);
            holder.dateText.setText(weather.getInfo());
            int type = changeImage(weather.getIcon());
            holder.imageView.setImageResource(type);
            holder.tempText.setText(weather.getTemp() + "°c");
            holder.weatherText.setText(weather.getPrecipitation());
            holder.cloudText.setText(activity.getString(R.string.cloudness)+" "+weather.getCloudsProc() + "%");
            holder.humidityText.setText(activity.getString(R.string.humidity)+" "+weather.getHumidity() + "%");
            holder.pressureText.setText(activity.getString(R.string.pressure)+" "+weather.getPressure() + "hPa");
            holder.windSpeedText.setText(activity.getString(R.string.wind_speed)+" "+weather.getWindSpeed() + "m/с");
        }
        private int changeImage(String image){
            int imageType;
            switch (image){
                case "01d": imageType = R.drawable.w01d; break;
                case "02d": case "02n": imageType = R.drawable.w02d; break;
                case "01n": imageType =R.drawable.w01n; break;
                case "03d": case "03n": imageType = R.drawable.w03; break;
                case "04d": case "04n": imageType = R.drawable.w04; break;
                case "09d": case "09n": imageType = R.drawable.w05; break;
                case "10d": case "10n": imageType = R.drawable.w06; break;
                case "11d": case "11n": imageType =R.drawable.w07; break;
                case "13d": case "13n": imageType =R.drawable.w08; break;
                case "50d": case "50n": imageType =R.drawable.w50; break;
                default:
                    imageType = R.drawable.w01d;
            }
            return  imageType;
        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public void startLoading(){
            loading = true;
            view.findViewById(R.id.list_progress).setVisibility(View.VISIBLE);
            view.findViewById(R.id.recycler_view_weather).setVisibility(View.GONE);
        }
        public void stopLoading(){
            loading = false;
            view.findViewById(R.id.list_progress).setVisibility(View.GONE);
            view.findViewById(R.id.recycler_view_weather).setVisibility(View.VISIBLE);
        }
        public boolean isLoading(){
            return loading;
        }
    }