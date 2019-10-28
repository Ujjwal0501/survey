package com.svnit.civil.survey.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;

import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;

import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ReminderReceiver extends BroadcastReceiver {
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
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,  "SurveyNotificationChannel")
                    .setContentTitle("Survey Reminder")
                    .setContentText("You have left the survey incomplete. Tap here to complete.")
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(PendingIntent.getService(context, 0, new Intent(context, Home.class), 0))
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(66666, builder.build());

        }
    }
}
