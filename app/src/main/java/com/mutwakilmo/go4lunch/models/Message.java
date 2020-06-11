package com.mutwakilmo.go4lunch.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {
    private String message;
    private Date dateCreated;
    private User userSender;
    private String urlImage;

    public Message(){}

    public Message(String message, User userSender){
        this.message = message;
        this.userSender = userSender;
    }


    public Message(String message, String urlImage, User userSender){
        this.message = message;
        this.urlImage = urlImage;
        this.userSender = userSender;
    }



    //------------GETTERS & SETTERS------------
    // --- GETTERS ---
    public String getMessage() { return message; }

//    @ServerTimestamp (line 35) is present and will indicate the date when the object is created in
//    Fire store. Firebase therefore manages it for us; no need to indicate it in one of the constructors... ;)
    @ServerTimestamp
    public Date getDateCreated() { return dateCreated; }
    public User getUserSender() { return userSender; }
    public String getUrlImage() { return urlImage; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(User userSender) { this.userSender = userSender; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
}
