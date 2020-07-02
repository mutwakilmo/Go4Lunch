package com.mutwakilandroiddev.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.api.Context;
import com.mutwakilandroiddev.go4lunch.api.ApiClient;
import com.mutwakilandroiddev.go4lunch.api.ApiInterface;
import com.mutwakilandroiddev.go4lunch.base.BaseActivity;
import com.mutwakilandroiddev.go4lunch.models.nearby.GooglePlacesResult;
import com.mutwakilandroiddev.go4lunch.models.nearby.NearbyPlacesList;
import com.mutwakilandroiddev.go4lunch.workmates_chat.ChatActivity;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //Fragment
    final Fragment fragment1 = new MapFragment();
    final Fragment fragment2 = new ListFragment();
    final Fragment fragment3 = new WorkmatesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    // Class name for Log tag
    public static final String TAG_LOG_MAIN = MainScreen.class.getSimpleName();

    //permission
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 111;

    private boolean mLocationPermissionGranted = false;
    private Location currentLocation;
    private double societyLat = 49.14;
    private double societyLng = 2.53;


    private List<GooglePlacesResult> results;
    private int radius;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        this.configureNavigationView();

        //set-up tool bar
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        fm.beginTransaction().add(R.id.fragment_content, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_content, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_content, fragment1, "1").commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Places.initialize(getApplicationContext(), BuildConfig.API_KEY);
        getLocationPermission();
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_main_screen;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void searchNearbyRestaurants(double mylat, double mylng){
        Log.d(TAG_LOG_MAIN, "searchNearbyRestaurants: ");
        String keyword = "";
        String key = BuildConfig.API_KEY;
        //String lat = String.valueOf(currentLocation.getLatitude());
        //String lng = String.valueOf(currentLocation.getLongitude());
        String lat = String.valueOf(mylat);
        String lng = String.valueOf(mylng);

        String location = lat+","+lng;

        Call<NearbyPlacesList> call;
        ApiInterface googleMapService = ApiClient.getClient().create(ApiInterface.class);
        call = googleMapService.getNearBy(location, radius, type, keyword, key);
        call.enqueue(new Callback<NearbyPlacesList>() {
            @Override
            public void onResponse(@NonNull Call<NearbyPlacesList> call, @NonNull Response<NearbyPlacesList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        results = response.body().getResults();
                        fragment1.updateNearbyPlaces(results);
                        fragment2.updateNearbyPlaces(results);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NearbyPlacesList> call, @NonNull Throwable t) {
                Log.d(TAG_LOG_MAIN, "onFailure: ");
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    // ----------------------------
    // CONFIGURATION permissions
    // ----------------------------

    private void getLocationPermission(){
        FusedLocationProviderClient mFusedLocationProviderClient;
        //getLocationPermission: getting location permissions
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;

                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // onComplete: found location
                            currentLocation = (Location) task.getResult();
                            // We pass the user's position to the fragment map
                            assert currentLocation != null;

                            fragment1.setUserLocation(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                            fragment2.setUserLocation(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                            searchNearbyRestaurants(currentLocation.getLatitude(), currentLocation.getLongitude());
                        }
                    }
                });

            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    //permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // onRequestPermissionsResult: called
        mLocationPermissionGranted = false;
        Log.d(TAG_LOG_MAIN, "onRequestPermissionsResult: ");
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            // onRequestPermissionsResult: permissions failed
                            return;
                        } else {
                            // onRequestPermissionsResult: Permissions granted
                            mLocationPermissionGranted = true;
                            getLocationPermission();
                        }
                    }
                }
            }
        }
    }


    // --------------------
    // BottomNavigationView
    // --------------------
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_map:
                            fm.beginTransaction().hide(active).show(fragment1).commit();
                            active = fragment1;
                            getSupportActionBar().setTitle(R.string.toolbar_title);
                            return true;

                        case R.id.nav_list:
                            fm.beginTransaction().hide(active).show(fragment2).commit();
                            active = fragment2;
                            getSupportActionBar().setTitle(R.string.toolbar_title);
                            return true;

                        case R.id.nav_workmates:
                            fm.beginTransaction().hide(active).show(fragment3).commit();
                            active = fragment3;
                            getSupportActionBar().setTitle(R.string.workmates);
                            return true;

                    }
                    return false;
                }
            };



    // ----------------------------
    // CONFIGURATION ProfileActivity
    // ----------------------------
    private void openProfileActivity() {
        Intent profileActivity = new Intent(MainScreen.this, ProfileActivity.class);
        startActivity(profileActivity);
    }

    // ----------------------------
    // CONFIGURATION ChatActivity
    // ----------------------------
    private void openChatActivity() {
        Intent chatActivity = new Intent(MainScreen.this, ChatActivity.class);


        if (this.isCurrentUserLogged()) {
            startActivity(chatActivity);
        } else {
            ////////////////////
        }


    }


    private void configureNavigationView() {

        navigationView.setNavigationItemSelectedListener(this);

        if (this.getCurrentUser() != null) {
            // Customize image profile
            View headView = navigationView.getHeaderView(0);
            ImageView imgProfile = headView.findViewById(R.id.image_user);
            //Get picture URL from Firebase
            if (this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(this.getCurrentUser().getPhotoUrl())
                        .override(200, 200)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgProfile);
                //Customize user data username and name
                String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ?
                        getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();
                String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ?
                        getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();

                TextView textUsername = headView.findViewById(R.id.name_user);
                textUsername.setText(username);
                TextView textEmail = headView.findViewById(R.id.email_user);
                textEmail.setText(email);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity_drawer_chat:
                drawer.closeDrawer(GravityCompat.START);
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected:  your CHAT");
                openChatActivity();
                return true;
            case R.id.main_activity_drawer_lunch:
                drawer.closeDrawer(GravityCompat.START);
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected:  your lunch");
                return true;
            case R.id.main_activity_drawer_settings:
                drawer.closeDrawer(GravityCompat.START);
                openProfileActivity();
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected: Settings");
                return true;
            case R.id.main_activity_drawer_logout:
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), SplashLunchActivity.class);
                startActivity(intent);
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected: logout");
                return true;
            default:
                return false;
        }


    }
}



