package com.mutwakilmo.go4lunch.api;

import com.google.firebase.firestore.Query;

public class MessageHelper {

    public static final String COLLECTION_NAME = "messages";

    //---GET--------
    public static final Query getAllMessageForChat(String chat){
        return ChatHelper.getChatCollection()
                .document(chat)
                .collection(COLLECTION_NAME)
                .orderBy("dateCreated")
                .limit(50);
    }
}
