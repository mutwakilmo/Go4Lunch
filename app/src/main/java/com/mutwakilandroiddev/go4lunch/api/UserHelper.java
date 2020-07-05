package com.mutwakilandroiddev.go4lunch.api;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;



public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String userEmail,String urlPicture) {
        User userToCreate = new User(uid, username, userEmail, urlPicture);

        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).get();
    }

    // --- GET CURRENT USER ID ---
    public static String getCurrentUserId() {
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }


    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> updateIsWorkMates(String uid, Boolean isWorkMates) {
        return UserHelper.getUsersCollection().document(uid).update("isWorkMates", isWorkMates);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }


    // --- UPDATE TODAY'S Restaurant---
    public static Task<Void> updateTodayRestaurant(String restoToday, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("restoToday", restoToday);
    }

    // --- UPDATE TODAY'S Restaurant---
    public static Task<Void> updateTodayRestaurantName(String restoTodayName, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("restoTodayName", restoTodayName);
    }

    // --- UPDATE DATE'S Restaurant---
    public static Task<Void> updateRestaurantDate(String restoDate, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("restoDate", restoDate);
    }

    // --- UPDATE LIKED RESTO---
    public static Task<Void> updateLikedRestaurant(List<String> restoLike, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("restoLike", restoLike);
    }



    // -- GET ALL USERS --
    public static Query getAllUsers(){
        return UserHelper.getUsersCollection().orderBy("restoDate", Query.Direction.DESCENDING);
    }
}