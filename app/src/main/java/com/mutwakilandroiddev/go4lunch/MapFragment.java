package com.mutwakilandroiddev.go4lunch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.mutwakilandroiddev.go4lunch.api.DisplayNearByPlaces;
import com.mutwakilandroiddev.go4lunch.api.Restaurant;
import com.mutwakilandroiddev.go4lunch.api.RestaurantHelper;
import com.mutwakilandroiddev.go4lunch.models.nearby.GooglePlacesResult;
import com.mutwakilandroiddev.go4lunch.utils.LunchDateFormat;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment implements OnMapReadyCallback, DisplayNearByPlaces {
    public static final float DEFAULT_ZOOM = 16f;
    @BindView(R.id.ic_gps)
    ImageView mGbs;
    private Marker myMarker;
    private double myLatitude;
    private double myLongitude;
    private String today;
    private GoogleMap googleMap;


    private View view;
    public MapFragment() {
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        LunchDateFormat lunchDateFormat = new LunchDateFormat();
        today = lunchDateFormat.getTodayDate();

        // Default position
        myLatitude = 48.858511;
        myLongitude = 2.294524;

        return view;
    }


    @Override
    public void updateNearbyPlaces(List<GooglePlacesResult> googlePlacesResults) {
        List<GooglePlacesResult> placesToShowId;
        placesToShowId = googlePlacesResults;
        displayNearbyPlaces(placesToShowId);

    }


    @Override
    public void setUserLocation(LatLng userLatLng) {

        myLatitude = userLatLng.latitude;
        myLongitude = userLatLng.longitude;

        if (googleMap != null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude),DEFAULT_ZOOM));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMap();
    }

    //--------------------------------------------------
    // Map of the world
    //--------------------------------------------------
    private void initMap() {
        MapView mapView;
        mapView = view.findViewById(R.id.map);
        if (mapView !=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), DEFAULT_ZOOM));
    }

    //click on the gps
    private void gpsClick(){
        mGbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), DEFAULT_ZOOM));
            }
        });
    }

    //display nearby  places/restaurant
    private void displayNearbyPlaces(List<GooglePlacesResult> idRestaurant) {
        for (int i = 0; i< idRestaurant.size(); i++){
            GooglePlacesResult restaurantMe = idRestaurant.get(i);
            String nameRestaurant = restaurantMe.getName();
            double LatRestaurant = restaurantMe.getGeometry().getLocation().getLat();
            double LngRestaurant = restaurantMe.getGeometry().getLocation().getLng();
            String placeIdRestaurant = restaurantMe.getPlaceId();

            LatLng restaurantLatLng = new LatLng(LatRestaurant,LngRestaurant );
            updateLikeColorPin(placeIdRestaurant, nameRestaurant, restaurantLatLng);
            //Todo 1 launch restaurant detail

        }
    }

    //update pin color
    private void updateLikeColorPin(final String placeId, final String name, final LatLng latLng) {
        final MarkerOptions markerOptions = new MarkerOptions();
        //By default we put red pins
        markerOptions.position(latLng)
                .title(name)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_locator_for_map_orange));
        myMarker = googleMap.addMarker(markerOptions);
        myMarker.setTag(placeId);


        RestaurantHelper.getRestaurant(placeId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                    Date dateRestaurant;
                    if (restaurant !=null){
                       dateRestaurant = restaurant.getDateCreated();
                       LunchDateFormat myDate = new LunchDateFormat();
                        String dateRegistered = myDate.getRegisteredDate(dateRestaurant);
                        if (dateRegistered == today){
                            int numberOfUser = restaurant.getClientTodayList().size();
                            if (numberOfUser > 0){
                                markerOptions.position(latLng)
                                        .title(name)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_locator_for_map_green));
                                myMarker = googleMap.addMarker(markerOptions);
                                myMarker.setTag(placeId);
                            }
                        }
                    }
                }
            }
        });
    }

}
