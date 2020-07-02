package com.mutwakilandroiddev.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

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
//-------------------------------------------------------------------------------------------------------
//    This is nothing too complicated, we're just implementing a method to enable us to create a Message object containing
//    the URL of a previously sent image, then we're saving that Message in Firestore.
//    So I can't send that message until I have a
//    -------------------------------------------------------------------------------------------------------

    public static Task<DocumentReference> createMessageWithImageForChat(String urlImage, String textMessage, String chat, User userSender){
        Message message = new Message(textMessage, urlImage, userSender);
        return ChatHelper.getChatCollection().document(chat).collection(COLLECTION_NAME).add(message);
    }
}
