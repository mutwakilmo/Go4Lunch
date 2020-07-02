package com.mutwakilandroiddev.go4lunch;

import com.google.android.gms.maps.model.LatLng;
import com.mutwakilandroiddev.go4lunch.models.nearby.GooglePlacesResult;

import java.util.List;

public interface DisplayNearByPlaces {
    void updateNearbyPlaces(List<GooglePlacesResult> googlePlacesResults);

    void setUserLocation(LatLng userLatLng);
}
