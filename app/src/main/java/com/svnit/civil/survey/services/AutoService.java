package com.svnit.civil.survey.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.text.format.Time;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.svnit.civil.survey.helpers.LocationHelper;
import com.svnit.civil.survey.helpers.NotificationHelper;
import com.svnit.civil.survey.models.LocationInfo;

public class AutoService extends Service {

    final int AS_NOTIF_ID = 1111111;
    public IBinder binder = new AutoServiceBinder();
    private boolean handlerFlag = false;

    Handler handler = new Handler();
    Runnable runnable;
    LocationHelper locationHelper;
    NotificationCompat.Builder builder;

    FirebaseUser user;
    DatabaseReference rawRef, processRef, confirmRef;

    long INTERVAL = 60 * 1000, FACTOR = 1;
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {Toast.makeText(context, "unexpected logout", Toast.LENGTH_LONG).show(); stopSelf();}
        rawRef = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/raw");
        processRef = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/routes");
        confirmRef = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/routes_confirmed");

        // initialise notification channel
        NotificationHelper.createNotificationChannel(this);
        builder = NotificationHelper.build(this);
        context = this;

        locationHelper = new LocationHelper();
        runnable = new Runnable() {
            @Override
            public void run() {
                FACTOR = 1;
                updateFrequency();
                handlerFlag = false;
            }
        };

        // initialise location service
        fusedLocationProviderClient = new FusedLocationProviderClient(AutoService.this);
        locationRequest = new LocationRequest()
                .setFastestInterval(5*1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(INTERVAL / FACTOR);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Toast.makeText(context, locationResult.getLastLocation().getSpeed()+"", Toast.LENGTH_LONG).show();

                Location location = locationResult.getLastLocation();
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.LocationInfo(location);
                rawRef.child(""+ location.getTime()).setValue(locationInfo);
                if (locationResult.getLastLocation().getSpeed() > 3.0) {
                    if (FACTOR == 1)  { FACTOR = 10; updateFrequency(); }
                    if (handlerFlag) { handler.removeCallbacks(runnable); handlerFlag = false; }
                } else {
                    if (!handlerFlag && FACTOR == 10) {
                        handler.postDelayed(runnable, 140*1000);
                        handlerFlag = true;
                    }
                }
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

            // TODO: schedule the request for later`
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
        locationRequest = new LocationRequest()
                .setFastestInterval(5*1000)
                .setInterval(INTERVAL / FACTOR);
        if (FACTOR == 1) locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        else locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        stopLocationUpdate();
        startLocationUpdate();
    }

    class AutoServiceBinder extends Binder {
        public AutoService getService() {
            return AutoService.this;
        }
    }
}
