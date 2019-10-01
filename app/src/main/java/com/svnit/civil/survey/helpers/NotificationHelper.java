package com.svnit.civil.survey.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;

public class NotificationHelper {
    private static String CHANNEL_ID = "SurveyNotificationChannel", CHANNEL_NAME = "Automatic Journey Survey", CHANNEL_DESCRIPTION = "This channel sends notification for the location info used by the app's service.";

    public static NotificationCompat.Builder build(Context context) {
        return init(context);
    }

    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_NONE;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static NotificationCompat.Builder init(Context context) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Active")
                .setContentText("Survey service is running.")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentIntent(PendingIntent.getService(context, 0, new Intent(context, Home.class), 0))
                .setOngoing(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
        builder.setShowWhen(false);
        return builder;
    }
}
