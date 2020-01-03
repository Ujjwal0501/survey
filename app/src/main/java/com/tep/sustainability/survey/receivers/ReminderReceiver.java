package com.tep.sustainability.survey.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tep.sustainability.survey.Home;
import com.tep.sustainability.survey.R;
import com.tep.sustainability.survey.helpers.NotificationHelper;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ReminderReceiver extends BroadcastReceiver {
    private String TAG = "Reminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        int PARTAA = 0, PARTAB = 0, PARTAC = 0, PARTBA = 0, PARTBB = 0, SUM = 0;
        SharedPreferences preferences = getDefaultSharedPreferences(context);

        SUM += preferences.getInt("partaa", 0);
        SUM += preferences.getInt("partab", 0);
        SUM += preferences.getInt("partac", 0);
        SUM += preferences.getInt("partba", 0);
        SUM += preferences.getInt("partbb", 0);

        if (SUM < 5) {
            // Notify the user
            Log.d(TAG, "Survey incomplete!");
            NotificationHelper.createNotificationChannel(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,  "TEP Sustainability")
                    .setContentTitle("Survey Reminder")
                    .setSmallIcon(R.mipmap.logo)
                    .setContentText("You have left the survey incomplete.\nTap here to continue.")
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, Home.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0))
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(66666, builder.build());

        }
    }
}
