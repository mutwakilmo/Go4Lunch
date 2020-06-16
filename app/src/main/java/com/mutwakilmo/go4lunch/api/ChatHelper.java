package com.mutwakilmo.go4lunch.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PublicKey;

public class ChatHelper {

    public static final String COLLECTION_NAME = "chats";


    /*Explanations: In this class, we;ve created a method (  getChatCollection()  )
    that will enable us to create a reference of the root Collection "chats".*/

    //-----COLLECTION REFERENCE----
    public static CollectionReference getChatCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
}
