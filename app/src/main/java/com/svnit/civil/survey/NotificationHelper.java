package com.svnit.civil.survey;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {
    private static String CHANNEL_ID = "SurveyNotificationChannel"
            , CHANNEL_NAME = "AUTO SERVICE NOTIFICATION CHANNEL FROM SURVEY"
            , CHANNEL_DESCRIPTION = "This channel sends notification for the location info used by the app's service.";

    public static NotificationCompat.Builder build(Context context) {
        return init(context);
    }

    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static NotificationCompat.Builder init(Context context) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.InboxStyle()
                        .setSummaryText("Running")
                        .addLine("Big Text Survey will fetch your travel related info and ask for your confirmation."))
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setOngoing(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setShowWhen(false);
        return builder;
    }
}
