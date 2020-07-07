package com.mutwakilandroiddev.go4lunch.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mutwakilandroiddev.go4lunch.view.MainActivity;
import com.mutwakilandroiddev.go4lunch.R;
import com.mutwakilandroiddev.go4lunch.api.Restaurant;
import com.mutwakilandroiddev.go4lunch.api.RestaurantHelper;
import com.mutwakilandroiddev.go4lunch.api.User;
import com.mutwakilandroiddev.go4lunch.api.UserHelper;
import com.mutwakilandroiddev.go4lunch.utils.LunchDateFormat;

import java.util.ArrayList;
import java.util.List;


public class NotificationsService extends FirebaseMessagingService {
    private static final String TAG = "NotificationsService";
    private final int NOTIFICATION_ID = 007;
    private final String NOTIFICATION_TAG = "FIRE_BASE_OC";
    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String NOTIFICATION_PREFS = "notifications";


    private String userId;
    private String restoTodayId;
    private String restoTodayName;
    private String restoTodayAddress;
    private List<String> listUserId = new ArrayList<>();
    private String listNames="";
    private boolean notifOk;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "onMessageReceived");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        notifOk = sharedPreferences.getBoolean(NOTIFICATION_PREFS, true);
        userId = UserHelper.getCurrentUserId();

        // We look if the user wants to receive notifications
        checkIfNotifToday();
    }


    private void checkIfNotifToday() {
        Log.d(TAG, "checkIfNotifToday");
        LunchDateFormat forToday = new LunchDateFormat();
        final String today = forToday.getTodayDate();

        // We check that the user has selected a restaurant for today
        UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                String myRestoToday;
                if (user != null) {
                    myRestoToday = user.getRestoToday();
                    String registeredDate = user.getRestoDate();
                    if (!myRestoToday.equals("")) {
                        Log.d(TAG, "onSuccess: ");
                        // We check that the restaurant has been registered for today
                        if (registeredDate.equals(today)) {
                            Log.d(TAG, "onSuccess: ");
                            // We check that he has subscribed to the sending of notifications
                            if (notifOk) {
                                createPersonalizedMessage();
                            }
                        }
                    }
                }
            }
        });
    }

    private void createPersonalizedMessage() {
        Log.d(TAG, "createPersonalizedMessage");

        // I get the id of the user's restoToday
        UserHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    restoTodayId=user.getRestoToday();
                }

                // I get the name and address of the restaurant
                RestaurantHelper.getRestaurant(restoTodayId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Restaurant resto = documentSnapshot.toObject((Restaurant.class));
                        if (resto != null) {
                            restoTodayName = resto.getRestoName();
                            restoTodayAddress = resto.getAddress();
                            // I retrieve the list of colleagues who have chosen this restaurant
                            listUserId = resto.getClientsTodayList();
                        }

                        // I retrieve the list of colleagues' names
                        for (int i=0; i<listUserId.size(); i++) {
                            UserHelper.getUser(listUserId.get(i)).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
                                    String name = null;
                                    if (user != null) {
                                        name = user.getUsername();
                                    }
                                    listNames+= name +", ";

                                    String line1_name = getResources().getString(R.string.notif_message1) + " " + restoTodayName;
                                    String line2_address = restoTodayAddress;
                                    String line3_with = getResources().getString(R.string.notif_message2);
                                    String line4_colleagues = listNames;
                                    if(line4_colleagues.endsWith(", ")) line4_colleagues = line4_colleagues.substring(0, line4_colleagues.length() - 2);

                                    sendVisualNotification(line1_name,line2_address, line3_with, line4_colleagues);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void sendVisualNotification(String m1, String m2, String m3, String m4) {
        Log.d(TAG, "sendVisualNotification");
        //  Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //  Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(getString(R.string.notification_title));
        inboxStyle.addLine(m1);
        inboxStyle.addLine(m2);
        inboxStyle.addLine(m3);
        inboxStyle.addLine(m4);

        //  Create a Channel (Android 8)
        String channelId = getString(R.string.default_notification_channel_id);

        //  Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.baseline_local_dining_24)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(m1)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setStyle(inboxStyle);

        //  Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //  Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Firebase Message";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        //  Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }

}
