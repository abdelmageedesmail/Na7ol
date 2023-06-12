package com.art4muslim.na7ol.notifications;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by abdelmageed on 30/03/17.
 */

public class MyFCMService extends FirebaseMessagingService {

    Intent intent;
    public static String id;
    PrefrencesStorage storage;
    private NotificationChannel channel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        storage = new PrefrencesStorage(this);
        final String message = remoteMessage.getNotification().getBody();
//                get("body");
        final String title = remoteMessage.getNotification().getTitle();
//                .get("title");

        Intent intent = new Intent("CUSTOM_ACTION");
//        intent.putExtra("body", "" + message);
//        intent.putExtra("title", "" + title);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);
        sendNotification(message, title);
//        startNotification(title, message);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String messageBody, String title) {
        intent = new Intent(MyFCMService.this, HomeActivity.class);
        intent.putExtra("from", "notification");
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(intent);

        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "Default")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Default", "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
