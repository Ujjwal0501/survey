package com.tep.sustainability.survey.services;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tep.sustainability.survey.Home;
import com.tep.sustainability.survey.R;
import com.tep.sustainability.survey.helpers.NotificationHelper;

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
            Log.d(TAG, "Survey incomplete!");
            NotificationHelper.createNotificationChannel(this);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,  "TEP Sustainability")
                    .setContentTitle("Survey Reminder")
                    .setSmallIcon(R.mipmap.logo)
                    .setContentText("You have left the survey incomplete.\nTap here to continue.")
                    .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, Home.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0))
                    .setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(66666, builder.build());

        } else {
            Log.d(TAG, "Completed Survey!");
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(11111);
        }
    }
}
