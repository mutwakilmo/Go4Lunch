package com.mutwakilandroiddev.go4lunch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.mutwakilandroiddev.go4lunch.models.nearby.GooglePlacesResult;

import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment implements DisplayNearByPlaces {

    public ListFragment() {
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ((MainScreen) Objects.requireNonNull(getActivity())).setActionBarTitle(getResources().getString(R.string.toolbar_title));
        return view;
    }

    @Override
    public void updateNearbyPlaces(List<GooglePlacesResult> googlePlacesResults) {

    }

    @Override
    public void setUserLocation(LatLng userLatLng) {

    }
}
