package com.mutwakilmo.go4lunch.models;

import androidx.annotation.Nullable;

public class User {

    private String uid;
    private String username;
    @Nullable private String urlPicture;

    public User(){}

    public User(String uid, String username, String urlPicture){
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
    }

    //------------GETTERS & SETTERS------------


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }
}
