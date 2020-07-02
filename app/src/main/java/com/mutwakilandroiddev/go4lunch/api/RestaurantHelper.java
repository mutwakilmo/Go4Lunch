package com.mutwakilandroiddev.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RestaurantHelper {
    public static final String COLLECTION_NAME ="restaurants";

    //collection reference
    public static CollectionReference getRestaurantsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    //create
    public static Task<Void> createRestaurant(String restaurantId, String restaurantName, String restaurantAddress){
        Restaurant restaurantToCreate = new Restaurant(restaurantName,restaurantAddress);
        return RestaurantHelper.getRestaurantsCollection().document(restaurantId).set(restaurantToCreate);
    }

    //Get
    public static Task<DocumentSnapshot> getRestaurant(String restaurantId){
        return RestaurantHelper.getRestaurantsCollection().document(restaurantId).get();
    }

    //update-name
    public static Task<Void> updateRestaurantName(String restaurantName,String restaurantId){
        return RestaurantHelper.getRestaurantsCollection().document(restaurantId).update("restaurantName", restaurantName);
    }

    //update restaurant users
    public static Task<Void> updateClientsTodayList(List<String> clientTodayList, String restaurantId){
        return RestaurantHelper.getRestaurantsCollection().document(restaurantId).update("clientsTodayList", clientTodayList);
    }
}
