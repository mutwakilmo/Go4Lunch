package com.mutwakilandroiddev.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.mutwakilandroiddev.go4lunch.models.Message;
import com.mutwakilandroiddev.go4lunch.models.User;

public class MessageHelper {

    public static final String COLLECTION_NAME = "messages";

    //---GET--------
    public static Query getAllMessageForChat(String chat) {
        return ChatHelper.getChatCollection().document(chat).collection(COLLECTION_NAME).orderBy("dateCreated").limit(50);
    }

    // --- CREATE ---

    public static Task<DocumentReference> createMessageForChat(String textMessage, String chat, User userSender){
        Message message = new Message(textMessage, userSender);
        return ChatHelper.getChatCollection().document(chat).collection(COLLECTION_NAME).add(message);
    }


    public static Task<DocumentReference> createMessageWithImageForChat(String urlImage, String textMessage, String chat, User userSender){
        Message message = new Message(textMessage, urlImage, userSender);
        return ChatHelper.getChatCollection().document(chat).collection(COLLECTION_NAME).add(message);
    }
}
