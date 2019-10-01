package com.svnit.civil.survey.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.svnit.civil.survey.helpers.LocationHelper;
import com.svnit.civil.survey.helpers.NotificationHelper;

public class AutoService extends Service {
    final int AS_NOTIF_ID = 1111111;
    public IBinder binder = new AutoServiceBinder();
    LocationHelper locationHelper;
    NotificationCompat.Builder builder;
    long INTERVAL = 180 * 1000, FACTOR = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // initialise notification channel
        NotificationHelper.createNotificationChannel(this);
        builder = NotificationHelper.build(this);
        context = this;

        locationHelper = new LocationHelper();

        // initialise location service
        fusedLocationProviderClient = new FusedLocationProviderClient(AutoService.this);
        locationRequest = new LocationRequest().setInterval(INTERVAL / FACTOR);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Toast.makeText(context, locationResult.getLastLocation().getSpeed() + "", Toast.LENGTH_SHORT).show();
                if (locationResult.getLastLocation().getSpeed() > 3.0) {
                    FACTOR = 3;
                    updateFrequency();
                } else FACTOR = 1;
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (!locationAvailability.isLocationAvailable()) {
                    locationHelper.reqEnable(context);
                } else {
                    Toast.makeText(context, "Location is available now.", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdate();
        Toast.makeText(context, "Survey closed unexpectedly.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (!locationHelper.checkPermission(context)) {
            Toast.makeText(context, "Location permission unavailable.", Toast.LENGTH_SHORT).show();
            locationHelper.reqPermission(null, context);
            stopSelf();
        }

        if (!locationHelper.isEnabled(context)) {
            // request enabling location service
            locationHelper.reqEnable(context);
            Toast.makeText(context, "Location disabled.", Toast.LENGTH_SHORT).show();

            // TODO: schedule the request for later
        }

        startForeground(AS_NOTIF_ID, builder.build());
        startLocationUpdate();

        return START_STICKY;
    }

    private void startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    void updateFrequency() {
        locationRequest = new LocationRequest().setInterval(INTERVAL / FACTOR);
        stopLocationUpdate();
        startLocationUpdate();
    }

    class AutoServiceBinder extends Binder {
        public AutoService getService() {
            return AutoService.this;
        }
    }
}
