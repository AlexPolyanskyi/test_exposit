package comalexpolyanskyi.github.test_exposit.fragments.childFragments;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import comalexpolyanskyi.github.test_exposit.R;
import comalexpolyanskyi.github.test_exposit.models.AnswerDataManager;
import comalexpolyanskyi.github.test_exposit.models.SixteenDaysWeather;
import comalexpolyanskyi.github.test_exposit.utils.DataManagers.SixTDsWeatherDataManager;
import comalexpolyanskyi.github.test_exposit.utils.DataManagers.WeatherDataManager;
import comalexpolyanskyi.github.test_exposit.utils.DialogMessage;
import comalexpolyanskyi.github.test_exposit.utils.adapters.FiveDaysFragmentAdapter;
import comalexpolyanskyi.github.test_exposit.utils.adapters.SixteenDaysFragmentAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Алексей on 07.06.2016.
 */
public class SixteenDaysFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback {
    private List<SixteenDaysWeather> responseList;
    private AnswerDataManager answerDataManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SixteenDaysFragmentAdapter adapter;
    private WeatherDataManager<SixteenDaysWeather> weatherDataManager;
    public SixteenDaysFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listdays, container, false);
        responseList =  new ArrayList<>();
        adapter = new SixteenDaysFragmentAdapter(getActivity(), responseList, rootView);
        weatherDataManager = new SixTDsWeatherDataManager();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_weather);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        getWeather();
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }
    private void getWeather(){
        adapter.startLoading();
        boolean isLocation = getArguments().getBoolean(getString(R.string.type_data_key));
        int MAX_AGE_5 = 5;
        if (isLocation) {
            Location location = getArguments().getParcelable(getString(R.string.location_key));
            answerDataManager = weatherDataManager.getData(location, MAX_AGE_5, getString(R.string.locale), getString(R.string.api_key), this);
        } else {
            String cityName = getArguments().getString(getString(R.string.city_name_key));
            answerDataManager = weatherDataManager.getData(cityName, MAX_AGE_5, getString(R.string.locale), getString(R.string.api_key), this);
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        int MAX_AGE_20h = 60 * 20;
        if(answerDataManager.getMaxAge() < MAX_AGE_20h){
            answerDataManager = weatherDataManager.getCacheData(answerDataManager.getRefreshingURL(), MAX_AGE_20h, this);
        }else{
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogMessage.getDialog(getActivity(), R.string.title_error_io, R.string.message_error_io).show();
                    stopLoading();
                }
            });
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            responseList = weatherDataManager.JSONParse(response.body().string(), responseList);
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    stopLoading();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogMessage.getDialog(getActivity(), R.string.title_error_server, R.string.message_error_server).show();
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        int MAX_AGE_0 = 0;
        weatherDataManager.refreshingData(answerDataManager.getRefreshingURL(), MAX_AGE_0, this);
    }

    private void stopLoading(){
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(adapter.isLoading()){
            adapter.stopLoading();
        }
    }
}
