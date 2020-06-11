package com.mutwakilmo.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mutwakilmo.go4lunch.models.User;

public class UserHelper {

    public static final String COLLECTION_NAME = "users";
    //---COLLECTION REFERENCE
    public static CollectionReference getUserCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }


    //--Create---
    public static Task<Void> createUser(String uid, String username, String urlPicture){
        //Create User object
        User userToCreate = new User(uid, username, urlPicture);
        return UserHelper.getUserCollection().document(uid).set(userToCreate);
    }


    //--Get------
    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUserCollection().document(uid).get();
    }
    //--Update-----
    public static Task<Void> updateUsername(String username, String uid){
    return UserHelper.getUserCollection().document(uid).update("username", username);
    }

    //---Delete--
    public static Task<Void> deleteUser(String uid){
        return UserHelper.getUserCollection().document(uid).delete();
    }

}