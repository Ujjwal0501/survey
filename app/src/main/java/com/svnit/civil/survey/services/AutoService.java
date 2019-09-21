package com.svnit.civil.survey.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.svnit.civil.survey.NotificationHelper;

public class AutoService extends Service {
    int AS_NOTIF_ID = 1111111;
    NotificationCompat.Builder builder;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        builder = NotificationHelper.build(this);
        context = this;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        startForeground(AS_NOTIF_ID, builder.build());
        return START_STICKY;
    }
}
