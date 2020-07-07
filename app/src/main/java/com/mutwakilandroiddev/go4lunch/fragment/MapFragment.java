package com.mutwakilandroiddev.go4lunch.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mutwakilandroiddev.go4lunch.R;
import com.mutwakilandroiddev.go4lunch.view.RestaurantDetailActivity;
import com.mutwakilandroiddev.go4lunch.api.Restaurant;
import com.mutwakilandroiddev.go4lunch.api.RestaurantHelper;
import com.mutwakilandroiddev.go4lunch.models.nearby.GooglePlacesResult;
import com.mutwakilandroiddev.go4lunch.utils.DisplayNearByPlaces;
import com.mutwakilandroiddev.go4lunch.utils.LunchDateFormat;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment implements OnMapReadyCallback, DisplayNearByPlaces {

    private View mView;

    private final static String TAG = "MapFragment";


    @BindView(R.id.ic_gps)
    ImageView mGps;

    private Marker myMarker;
    private GoogleMap mMap;
    private double myLatitude;
    private double myLongitude;
    private String today;
    private static final float DEFAULT_ZOOM = 16f;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        LunchDateFormat forToday = new LunchDateFormat();
        today = forToday.getTodayDate();
        ButterKnife.bind(this, mView);
        // Default position
        myLatitude = 48.858511;
        myLongitude = 2.294524;

        return mView;
    }

    // --------------------
    // Update nearByPlace
    // --------------------
    @Override
    public void updateNearbyPlaces(List<GooglePlacesResult> googlePlacesResults) {
        List<GooglePlacesResult> placesToShowId;
        placesToShowId = googlePlacesResults;
        displayNearbyPlaces(placesToShowId);
    }

    // --------------------
    // SetUserLocation
    // --------------------
    public void setUserLocation(LatLng userLatLng) {
        // update location
        myLatitude = userLatLng.latitude;
        myLongitude = userLatLng.longitude;

        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), DEFAULT_ZOOM));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMap();
        clickGps();
    }

    // --------------------
    // Map of the world
    // --------------------
    //initializing map
    private void initMap() {
        MapView mMapView;
        mMapView = mView.findViewById(R.id.map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext()  , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), DEFAULT_ZOOM));


    }

    // --------------------
    // OnClick for the GBS icon
    // --------------------
    private void clickGps() {
        // click on gps
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), DEFAULT_ZOOM));
            }
        });
    }

    private void displayNearbyPlaces(List<GooglePlacesResult> idRestaurant) {
        for (int i = 0; i < idRestaurant.size(); i++) {
            GooglePlacesResult restaurantLunch = idRestaurant.get(i);
            String restaurantName = restaurantLunch.getName();
            double restaurantLat = restaurantLunch.getGeometry().getLocation().getLat();
            double restaurantLng = restaurantLunch.getGeometry().getLocation().getLng();
            String restaurantPlaceId = restaurantLunch.getPlaceId();

            LatLng restaurantLatLng = new LatLng(restaurantLat, restaurantLng);
            updateLikeColorPin(restaurantPlaceId, restaurantName, restaurantLatLng);

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    restaurantDetail(marker);
                }
            });
        }
    }

    //---------------------------------------------------------------------------------------------------
    // Update Pin color from Firebase for selected lunch
    //---------------------------------------------------------------------------------------------------
    private void updateLikeColorPin(final String placeId, final String name, final LatLng latLng) {

        // The color of the pin is adjusted according to the user's choice
        final MarkerOptions markerOptions = new MarkerOptions();

        // By default we put red pins
        markerOptions.position(latLng)
                .title(name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_locator_for_map_orange));
        myMarker = mMap.addMarker(markerOptions);
        myMarker.setTag(placeId);


        RestaurantHelper.getRestaurant(placeId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                    Date dateRestaurantSheet;
                    if (restaurant != null) {
                        dateRestaurantSheet = restaurant.getDateCreated();
                        LunchDateFormat myDate = new LunchDateFormat();
                        String dateRegistered = myDate.getRegisteredDate(dateRestaurantSheet);

                        if (dateRegistered.equals(today)) {
                            int lunchUsers = restaurant.getClientsTodayList().size();
                            if (lunchUsers > 0) {
                                markerOptions.position(latLng)
                                        .title(name)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_locator_for_map_green));
                                myMarker = mMap.addMarker(markerOptions);
                                myMarker.setTag(placeId);
                            }
                        }
                    }
                }
            }
        });
    }

    //--------------------------------------------------------------------------------------------------------------------
    //manages the launch of restaurant detail activity
    //--------------------------------------------------------------------------------------------------------------------
    //launch restaurant detail activity
    private void restaurantDetail(Marker marker) {
        String PLACE_ID_RESTAURANT = "resto_place_id";
        String ref = (String) marker.getTag();
        Intent intent = new Intent(getContext(), RestaurantDetailActivity.class);
        //Id
        intent.putExtra(PLACE_ID_RESTAURANT, ref);
        startActivity(intent);
    }
}




