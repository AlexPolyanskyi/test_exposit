package comalexpolyanskyi.github.test_exposit.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Map;

import comalexpolyanskyi.github.test_exposit.R;
import comalexpolyanskyi.github.test_exposit.utils.adapters.FavoritesAdapter;

/**
 * Created by Алексей on 07.06.2016.
 */
public class FavoritesFragment extends Fragment implements SearchView.OnQueryTextListener {
    private Map<String, ?> dataMap;
    private FavoritesAdapter adapter;
    public FavoritesFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        dataMap = sharedPreferences.getAll();
        adapter = new FavoritesAdapter(getActivity(), dataMap);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ArrayList<Object> data = new ArrayList<>(0);
        query = query.toLowerCase();
        Object [] stringsArr = dataMap.keySet().toArray();
        for(int i = 0; i < stringsArr.length; i++){
            if(stringsArr[i].toString().contains(query)){
                data.add(stringsArr[i].toString());
            }
        }
        if(data.size() > 0){
            adapter.updateDataSet(data.toArray());
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}