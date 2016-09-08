package comalexpolyanskyi.github.test_exposit.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import comalexpolyanskyi.github.test_exposit.R;
import comalexpolyanskyi.github.test_exposit.utils.adapters.ViewPagerAdapter;
import comalexpolyanskyi.github.test_exposit.fragments.childFragments.FiveDaysFragment;
import comalexpolyanskyi.github.test_exposit.fragments.childFragments.SixteenDaysFragment;
import comalexpolyanskyi.github.test_exposit.fragments.childFragments.TodayFragment;
import comalexpolyanskyi.github.test_exposit.utils.DialogMessage;

/**
 * Created by Алексей on 07.06.2016.
 */
public class ForecastFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private GoogleApiClient mGoogleApiClient = null;
    private Boolean isLocation = false;
    private String cityName = "-1";
    private View rootView;
    public ForecastFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        isLocation = bundle.getBoolean(getString(R.string.type_data_key));
        if(!isLocation){
            cityName = bundle.getString(getString(R.string.city_name_key));
       }
        if (mGoogleApiClient == null && isLocation) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        checkFavorites();
        if(!isLocation) {
            rootView.findViewById(R.id.fab).setVisibility(View.VISIBLE);
            setupViewPager(setupBundleFromCity());
            tabLayout.setupWithViewPager(viewPager);
        }else{
            rootView.findViewById(R.id.fab).setVisibility(View.GONE);
        }
        return rootView;
    }
    private Bundle setupBundleFromCity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.type_data_key), false);
        bundle.putString(getString(R.string.city_name_key), cityName);
        return bundle;
    }
    private Bundle setupBundleFromMyLoc(Location location) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.type_data_key), true);
        bundle.putParcelable(getString(R.string.location_key), location);
        return bundle;
    }
    private void setupViewPager(Bundle bundle){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        TodayFragment todayFragment = new TodayFragment();
        todayFragment.setArguments(bundle);
        FiveDaysFragment fiveDaysFragment = new FiveDaysFragment();
        fiveDaysFragment.setArguments(bundle);
        SixteenDaysFragment sixteenDaysFragment = new SixteenDaysFragment();
        sixteenDaysFragment.setArguments(bundle);
        adapter.addFragment(todayFragment, getString(R.string.fragment_today));
        adapter.addFragment(fiveDaysFragment, getString(R.string.fragment_five_days));
        adapter.addFragment(sixteenDaysFragment, getString(R.string.fragment_sixteen_days));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            setupViewPager(setupBundleFromMyLoc(mLastLocation));
            tabLayout.setupWithViewPager(viewPager);
        }else{
            errorFindLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        errorFindLocation();
    }
    private void errorFindLocation(){
        DialogMessage.getDialog(getActivity(), R.string.title_error_location, R.string.message_error_location).show();
        cityName = "Russia,Moscow";
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null)
        actionBar.setTitle(cityName);
        setupViewPager(setupBundleFromCity());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        if(cityName != "-1"){
            FloatingActionButton floatingActionButton = (FloatingActionButton) v;
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean does = doesTheFavorites(cityName);
            if(!does){
                editor.putString(cityName.toLowerCase(), cityName);
                floatingActionButton.setImageResource(R.drawable.ic_favorites_on2);
                Toast.makeText(getContext(), getString(R.string.favorit_add), Toast.LENGTH_SHORT).show();
            }else{
                editor.remove(cityName.toLowerCase());
                floatingActionButton.setImageResource(R.drawable.ic_favorites_off2);
                Toast.makeText(getContext(), getString(R.string.favorit_delete), Toast.LENGTH_SHORT).show();
            }
            editor.apply();
        }
    }

    private void checkFavorites(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(sharedPreferences.contains(cityName.toLowerCase())) {
            ((FloatingActionButton)rootView.findViewById(R.id.fab)).setImageResource(R.drawable.ic_favorites_on2);
        }
    }
    private boolean doesTheFavorites(String city){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.contains(city.toLowerCase());
    }
}
