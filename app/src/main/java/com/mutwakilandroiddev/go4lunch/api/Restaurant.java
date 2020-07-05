package com.mutwakilandroiddev.go4lunch.api;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Restaurant {
    private String restaurantName;
    private Date dateCreated;
    private String address;
    private List<String> clientTodayList;

    public Restaurant(){}

    public Restaurant(String restaurantName, String address) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.clientTodayList = new ArrayList<>();
    }

    //getters
    public String getRestaurantName() {
        return restaurantName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getClientTodayList() {
        return clientTodayList;
    }

    //setters

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setClientTodayList(List<String> clientTodayList) {
        this.clientTodayList = clientTodayList;
    }


}