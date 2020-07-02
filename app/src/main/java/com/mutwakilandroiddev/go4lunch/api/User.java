package com.mutwakilandroiddev.go4lunch.api;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String uid;
    private String username;
    private Boolean isMentor;
    @Nullable private String urlPicture;
    private String restaurantToday;
    private String restaurantTodayName;
    private String restaurantDate;
    private List<String> restaurantLike;

    public User() { }

    public User(String uid, String username, String urlPicture) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.isMentor = false;
        this.restaurantToday = "";
        this.restaurantTodayName = "";
        this.restaurantDate = "";
        this.urlPicture = urlPicture;
        this.restaurantLike = new ArrayList<>();

    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public String getUrlPicture() { return urlPicture; }
    public Boolean getIsMentor() { return isMentor; }
    public String getRestaurantToday() { return restaurantToday;}
    public String getRestaurantTodayName() {return restaurantTodayName;}
    public String getRestaurantDate() {return restaurantDate;}
    public List<String> getRestaurantLike() { return restaurantLike; }
    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setIsMentor(Boolean mentor) { isMentor = mentor; }
    public void setRestaurantToday(String restaurantToday) {this.restaurantToday = restaurantToday;}
    public void setRestaurantTodayName(String restaurantTodayName) {this.restaurantTodayName = restaurantTodayName;}
    public void setRestaurantDate(String restaurantDate) {this.restaurantDate = restaurantDate;}
    public void setRestaurantLike(List<String> restaurantLike) {this.restaurantLike = restaurantLike;}
}