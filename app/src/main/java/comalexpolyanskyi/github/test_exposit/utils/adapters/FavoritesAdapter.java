package comalexpolyanskyi.github.test_exposit.utils.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Map;

import comalexpolyanskyi.github.test_exposit.R;

/**
 * Created by Алексей on 17.06.2016.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {
    private Activity activity;
    private Map<String, ?> cityData;
    private Object[] cityKey;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textCity;
        public ImageView imageFavorites;
        public MyViewHolder(View view) {
            super(view);
            textCity = (TextView) view.findViewById(R.id.city_name);
            imageFavorites = (ImageView) view.findViewById(R.id.image_favorites);
        }
    }
    public FavoritesAdapter(Activity activity, Map<String, ?> data) {
        this.activity = activity;
        this.cityData = data;
        this.cityKey = data.keySet().toArray();
    }
    @Override
    public FavoritesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favatits_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FavoritesAdapter.MyViewHolder holder, int position) {
        String city = (String) cityData.get(cityKey[position].toString());
        final String statusE = "enable";
        holder.textCity.setText(city);
        holder.textCity.setTag(city);
        holder.imageFavorites.setTag(statusE);
        holder.imageFavorites.setTag(R.string.city_name_key, city);
        holder.imageFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                if(imageView.getTag().equals(statusE)){
                    imageView.setImageResource(R.drawable.ic_favorites_off);
                    imageView.setTag("disable");
                    String city = (String) imageView.getTag(R.string.city_name_key);
                    deleteInPref(city.toLowerCase());
                }else{
                    imageView.setImageResource(R.drawable.ic_favorites_on);
                    imageView.setTag(statusE);
                    String data = (String) imageView.getTag(R.string.city_name_key);
                    addInPref(data.toLowerCase(), data);
                }
            }
        });
    }
    private  void addInPref(String key, String data){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();

    }
    public void updateDataSet(Object []cityData){
        this.cityKey = cityData;
        this.notifyDataSetChanged();
    }
    private void deleteInPref(String key){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
    @Override
    public int getItemCount() {
        return cityKey.length;
    }
}
