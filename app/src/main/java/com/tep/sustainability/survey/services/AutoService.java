package com.tep.sustainability.survey.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tep.sustainability.survey.helpers.LocationHelper;
import com.tep.sustainability.survey.helpers.NotificationHelper;
import com.tep.sustainability.survey.models.LocationInfo;

import java.util.HashMap;
import java.util.Map;

public class AutoService extends Service {

    final int AS_NOTIF_ID = 1111111;
    private final String TAG = "AutoService";
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
    private Map<String, LocationInfo> locationList;
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
        if (user == null) {Log.d(TAG, "Unexpected logout / never signed in"); stopSelf();}
        rawRef = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/raw");
        processRef = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/routes");
        confirmRef = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/routes_confirmed");

        // initialise notification channel
        NotificationHelper.createNotificationChannel(this);
        builder = NotificationHelper.build(this);
        context = this;

        locationList = new HashMap<>();
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
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(INTERVAL / FACTOR);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d(TAG, "" + locationResult.getLastLocation().getSpeed() + " - " + locationList.size());

                Location location = locationResult.getLastLocation();
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.LocationInfo(location);

                // rawRef.child(""+ location.getTime()).setValue(locationInfo);
                locationList.put(location.getTime()+"", locationInfo);
                if (locationList.size() > 10) {
                    sendLocations();
                }
                if (locationResult.getLastLocation().getSpeed() > 3.0) {
                    if (FACTOR == 1)  { FACTOR = 10; updateFrequency(); }
                    if (handlerFlag) { handler.removeCallbacks(runnable); handlerFlag = false; }
                } else {
                    if (!handlerFlag && FACTOR == 10) {
                        handler.postDelayed(runnable, 60*1000);
                        handlerFlag = true;
                    }
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (!locationAvailability.isLocationAvailable()) {
                    locationHelper.reqEnable(context);
                }
            }
        };

    }

    private void sendLocations() {
        rawRef.setValue(locationList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                locationList.clear();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, ""+e.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdate();
        sendLocations();
        Toast.makeText(context, "Survey closed unexpectedly.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (!locationHelper.checkPermission(context)) {
            // Toast.makeText(context, "Location permission unavailable.", Toast.LENGTH_SHORT).show();
            locationHelper.reqPermission(null, context);
            stopSelf();
        }

        if (!locationHelper.isEnabled(context)) {
            // request enabling location service
            locationHelper.reqEnable(context);
            // Toast.makeText(context, "Location disabled.", Toast.LENGTH_SHORT).show();

            // TODO: schedule the request for later`
        }

        verifySession();
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

    private void verifySession() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/"+user.getUid()+"/loginInstance");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uuid = dataSnapshot.getValue(String.class);
                if (uuid == null || uuid.equals("")) {

                    stopSelf();
                } else if (!uuid.equals(preferences.getString("uuid", ""))) {

                    stopSelf();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void updateFrequency() {
        locationRequest = new LocationRequest()
                .setFastestInterval(5*1000)
                .setInterval(INTERVAL / FACTOR);
        if (FACTOR == 1) locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        else locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        stopLocationUpdate();
        startLocationUpdate();
    }

    class AutoServiceBinder extends Binder {
        public AutoService getService() {
            return AutoService.this;
        }
    }
}
