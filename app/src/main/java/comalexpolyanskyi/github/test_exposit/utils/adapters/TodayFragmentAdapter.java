package comalexpolyanskyi.github.test_exposit.utils.adapters;

import android.databinding.DataBindingUtil;
import android.view.View;

import comalexpolyanskyi.github.test_exposit.databinding.FragmentTodayBinding;
import comalexpolyanskyi.github.test_exposit.models.TodayWeather;

/**
 * Created by Алексей on 15.06.2016.
 */
public class TodayFragmentAdapter {
    private View view = null;
    private FragmentTodayBinding binding;
    private boolean loading = false;
    public TodayFragmentAdapter(View view){
        this.view = view;
        binding = DataBindingUtil.bind(view);
    }
    public void initView(TodayWeather todayWeather) {
        binding.setTWeather(todayWeather);
    }

    public void stopLoading(){
        loading = false;
        binding.todayProgress.setVisibility(View.GONE);
        binding.todayLayout.setVisibility(View.VISIBLE);
    }
    public void startLoading(){
        loading = true;
        binding.todayProgress.setVisibility(View.VISIBLE);
        binding.todayLayout.setVisibility(View.GONE);
    }

    public boolean isLoading(){
        return loading;
    }
}
