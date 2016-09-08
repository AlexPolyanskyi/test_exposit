package comalexpolyanskyi.github.test_exposit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comalexpolyanskyi.github.test_exposit.R;
import comalexpolyanskyi.github.test_exposit.utils.adapters.ListCityAdapter;
import comalexpolyanskyi.github.test_exposit.utils.DialogMessage;
import comalexpolyanskyi.github.test_exposit.utils.DataManagers.ListCitiesDataManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Алексей on 07.06.2016.
 */
public class ListCityFragment extends Fragment implements SearchView.OnQueryTextListener, Callback {
    private ListCitiesDataManager dataManager;
    private ListCityAdapter adapter;
    private SearchView searchView;
    private List<String> dataList;
    public ListCityFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.example));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        dataList = new ArrayList<>();
        adapter = new ListCityAdapter(dataList);
        dataManager =  new ListCitiesDataManager();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        dataManager.getServerData(query, getString(R.string.locale), getString(R.string.api_key), this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(getActivity() == null)
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogMessage.getDialog(getActivity(), R.string.title_error_io, R.string.message_error_io).show();
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String resp = response.body().string();
            dataList = dataManager.JsonParse(resp, dataList);
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(dataList.size() == 0){
                        DialogMessage.getDialog(getActivity(), R.string.title_error_server, R.string.not_found_data).show();
                    }else{
                        adapter.notifyDataSetChanged();
                    }
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
}
