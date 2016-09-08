package comalexpolyanskyi.github.test_exposit.utils.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import comalexpolyanskyi.github.test_exposit.R;

/**
 * Created by Алексей on 17.06.2016.
 */
public class ListCityAdapter extends RecyclerView.Adapter<ListCityAdapter.MyViewHolder> {
    private List<String> data = null;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cityName;
        public MyViewHolder(View view) {
            super(view);
            cityName = (TextView) view.findViewById(R.id.city_item_name);
        }
    }
    public ListCityAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public ListCityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListCityAdapter.MyViewHolder holder, int position) {
        String city = data.get(position);
        holder.cityName.setText(city);
        holder.cityName.setTag(city);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
