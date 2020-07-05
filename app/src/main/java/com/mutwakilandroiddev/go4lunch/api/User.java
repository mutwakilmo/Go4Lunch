package com.mutwakilandroiddev.go4lunch.api;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
public class User {

    private String uid;
    private String username;
    private String userEmail;
    private String restoToday;
    private String restoTodayName;
    private String restoDate;
    private Boolean isWorkMates;
    @Nullable
    private String urlPicture;
    private List<String> restoLike;

    public User() { }

    public User(String uid, String username, String userEmail, String urlPicture) {
        this.uid = uid;
        this.username = username;
        this.userEmail = userEmail;
        this.restoToday = "";
        this.restoTodayName = "";
        this.restoDate = "";
        this.urlPicture = urlPicture;
        this.restoLike = new ArrayList<>();
        this.isWorkMates = false;


    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }

    public Boolean getIsWorkMates() { return isWorkMates; }
    public String getUserEmail() { return  userEmail;}
    public String getRestoToday() { return restoToday;}
    public String getRestoTodayName() {return restoTodayName;}
    public String getRestoDate() {return restoDate;}
    public String getUrlPicture() { return urlPicture; }
    public List<String> getRestoLike() { return restoLike; }
    // --- SETTERS ---

    public void setIsWorkMates(Boolean workMates) { isWorkMates = workMates; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail;}
    public void setRestoToday(String restoToday) {this.restoToday = restoToday;}
    public void setRestoTodayName(String restoTodayName) {this.restoTodayName = restoTodayName;}
    public void setRestoDate(String restoDate) {this.restoDate = restoDate;}
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setRestoLike(List<String> restoLike) {this.restoLike = restoLike;}
}