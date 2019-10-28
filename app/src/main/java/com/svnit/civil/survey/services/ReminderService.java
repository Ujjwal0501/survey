package com.svnit.civil.survey.services;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;

import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

@TargetApi(21)
public class ReminderService extends JobService {
    private String TAG = "Reminder";

    @Override
    public boolean onStartJob(JobParameters params) {
        checkAndNotify();
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void checkAndNotify() {
        int PARTAA = 0, PARTAB = 0, PARTAC = 0, PARTBA = 0, PARTBB = 0, SUM = 0;
        SharedPreferences preferences = getDefaultSharedPreferences(this);

        SUM += preferences.getInt("partaa", 0);
        SUM += preferences.getInt("partab", 0);
        SUM += preferences.getInt("partac", 0);
        SUM += preferences.getInt("partba", 0);
        SUM += preferences.getInt("partbb", 0);

        if (SUM < 5) {
            // Notify the user
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,  "SurveyNotificationChannel")
                    .setContentTitle("Survey Reminder")
                    .setContentText("You have left the survey incomplete. Tap here to complete.")
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(PendingIntent.getService(this, 0, new Intent(this, Home.class), 0))
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(66666, builder.build());

        } else {
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(11111);
        }
    }
}
