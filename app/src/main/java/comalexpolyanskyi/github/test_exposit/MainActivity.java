package comalexpolyanskyi.github.test_exposit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import comalexpolyanskyi.github.test_exposit.fragments.FavoritesFragment;
import comalexpolyanskyi.github.test_exposit.fragments.DrawerFragment;
import comalexpolyanskyi.github.test_exposit.fragments.ForecastFragment;
import comalexpolyanskyi.github.test_exposit.fragments.ListCityFragment;
import comalexpolyanskyi.github.test_exposit.utils.DownloadClient;

public class MainActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        new DownloadClient().initCache(this);
        initDrawer();
    }

    private void initDrawer(){
        DrawerFragment drawerFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(null, "0");
    }

    @Override
    public void onDrawerItemSelected(View view, String position) {
        displayView(null, position);
    }

    public void CitySelected(View view) {
        displayView((String) view.getTag(), "3");
    }

    public void onSearchCityPick(View view) {
        displayView((String) view.getTag(), "3");
    }

    private void displayView(String data, String position) {
        Fragment fragment = null;
        Bundle bundle;
        String title = getString(R.string.app_name);
        switch (position) {
            case "0":
                fragment = new ForecastFragment();
                bundle = new Bundle();
                bundle.putBoolean(getString(R.string.type_data_key), true);
                fragment.setArguments(bundle);
                title = getString(R.string.nav_item_loc);
                break;
            case "1":
                fragment = new ListCityFragment();
                title = getString(R.string.nav_item_search);
                break;
            case "2":
                fragment = new FavoritesFragment();
                title = getString(R.string.nav_item_favorites);
                break;
            case "3":
                fragment = new ForecastFragment();
                bundle = new Bundle();
                bundle.putBoolean(getString(R.string.type_data_key), false);
                bundle.putString(getString(R.string.city_name_key), data);
                fragment.setArguments(bundle);
                title = data;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }
}