package com.svnit.civil.survey.services;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;
import com.svnit.civil.survey.helpers.NotificationHelper;
import com.svnit.civil.survey.models.LocationInfo;
import com.svnit.civil.survey.models.Route;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@TargetApi(21)
public class ProcessRouteService extends JobService {
    private final String ROOT_TRAVEL =  "/travel_details", LOCATION = "/raw", PROCESSED = "/routes", CONFIRMED = "/routes_confirmed";
    private FirebaseUser user;
    private DatabaseReference raw, processed, confirmed;
    private String TAG = "ProcessRoute";


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Start of onCreate");
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            raw = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+ ROOT_TRAVEL + LOCATION);
            processed = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+ ROOT_TRAVEL + PROCESSED);
            confirmed = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+ ROOT_TRAVEL + CONFIRMED);
        } else {
            // TODO: notify to login again at later time
        }
        Log.d(TAG, "End of onCreate");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Start of onStartJob");
        // do the processing of locations
        raw.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getChildrenCount() > 20)
                    doProcessing(dataSnapshot.getChildren());
//                Map<String, LocationInfo> map = new HashMap<>();
//                if (dataSnapshot.getChildrenCount() < 60) return;
//
//                String temp = "";
//                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
//                    LocationInfo entry = snapshot.getValue(LocationInfo.class);
//                    if (entry.getSpeed() > 0)
//                    temp += entry.toString();
//                }
//                Log.d(TAG, temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Route processing cancelled.");
                Log.d(TAG, databaseError.getMessage());
            }
        });
        Log.d(TAG, "End of onStartJob");
        return false;
    }

    private void doProcessing(Iterable<DataSnapshot> snapshotIterable) {
        Log.d(TAG, "Start of doProcessing");
        ArrayList<LocationInfo> arrayList = new ArrayList<>();
        Map<String, Route> routeList = new HashMap<>();
        int[] list = {-1, -1, -1,};
        Double[] last = {0D, 0D};
        int state = 0, size = 0;
        for (DataSnapshot snapshot: snapshotIterable) {
            boolean lc = false, sc = false;
            LocationInfo temp = (snapshot).getValue(LocationInfo.class);
            if (temp == null) return;
            arrayList.add(temp);

            Double[] cur = {temp.getLatitude(), temp.getLongitude()};
            float[] dist = {0f, 0f, 0f};
            try { Location.distanceBetween(last[0], last[1], cur[0], cur[1], dist); }
            catch (IllegalArgumentException e) { Log.d(TAG, e.getMessage() + ""); }
            last[0] = cur[0];
            last[1] = cur[1];

            if (temp.getSpeed() > 2.5) sc = true;
            if (dist[0] > 0f) lc = true;

            switch (state) {
                case 0:
                    if (sc && !lc) state++;
                    list[state] = arrayList.size();
                    break;
                case 1:
                    if (!sc || !lc) state++;
                    list[state] = arrayList.size();
                    break;
                case 2:
                    if (lc && sc) {
                        list[state] = -1;
                        state--;
                        list[state] = arrayList.size();
                        break;
                    }
                    if (arrayList.get(list[1]).getTime()-temp.getTime() > 180*1000) {
                        routeList.put(""+(new Date().getTime()), generateRoute(list, arrayList));
                        state = 0;
                        list[0] = -1; list[1] = -1; list[2] = -1;
                    }
                    break;
                default:
                    Log.d(TAG, "Unknown state.");
                    break;
            }
        }

        processed.setValue(routeList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // done uploading
                    notifyUser();
                } else {
                    Log.d(TAG, task.getException().getLocalizedMessage()+"");
                }
            }
        });
        Log.d(TAG, "End of doProcessing");
    }

    private void notifyUser() {
        NotificationHelper.createNotificationChannel(this); // create channel on Oreo and later
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, "SurveyNotificationChannel")
                .setContentTitle("Submit your routes")
                .setContentText("Confirm your routes and submit them.")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(PendingIntent.getActivity(this,
                        0, new Intent(this, Home.class), 0))
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(44444, builder.build());
    }

    private Route generateRoute(int[] list, ArrayList<LocationInfo> locationList) {
        Log.d(TAG, "Start of generateRoute");
        Route newRoute = new Route();
        LocationInfo start = locationList.get(list[1]), end = locationList.get(list[2]);
        newRoute.setStartLocation(start.getLongitude()+","+start.getLatitude());
        newRoute.setEndLocation(end.getLongitude()+","+end.getLatitude());
        newRoute.setStartTime(start.getTime());
        newRoute.setEndTime(end.getTime());
        newRoute.setTravelTime((start.getTime() - end.getTime())/60000);
        newRoute.setVehicleTime((start.getTime() - end.getTime())/60000);

        ArrayList<Double> distanceList = new ArrayList<>(); // debug
        Double[] last = {0D, 0D}, cur = {0D, 0D};
        double tripLength = 0d;
        last[0] = start.getLatitude();
        last[1] = start.getLongitude();
        for (int i = list[1]; i <= list[2]; i++) {
            cur[0] = locationList.get(i).getLatitude();
            cur[1] = locationList.get(i).getLongitude();
            float[] dist = {0f, 0f, 0f};
            try { Location.distanceBetween(last[0], last[1], cur[0], cur[1], dist); }
            catch (IllegalArgumentException e) { Log.d(TAG, e.getMessage()+""); }
            tripLength += dist[0];

            last[0] = cur[0]; last[1] = cur[1];
            distanceList.add(1D * dist[0]); // debug
        }

        float[] dist = {0f, 0f, 0f};
        try { Location.distanceBetween(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude(), dist); }
        catch (IllegalArgumentException e) { Log.d(TAG, e.getMessage()+""); }

        newRoute.setTripLength(tripLength);
        newRoute.setDistance(1D * dist[0]);
        newRoute.setDistanceList(distanceList); // debug

        // wait time
        dist[0] = 0f;
        int pos = -1;
        for (int i = list[1]-1; i >= 0; i--) {
            dist[0] = 0f;
            LocationInfo temp = locationList.get(i);
            try { Location.distanceBetween(temp.getLatitude(), temp.getLongitude(), start.getLatitude(), start.getLongitude(), dist); }
            catch (IllegalArgumentException e) { Log.d(TAG, e.getMessage()+""); }
            if (dist[0] > 15.0) pos = i;
        }

        if (pos != -1)
        newRoute.setWaitTime((locationList.get(pos).getTime()-start.getTime())/60000);

        for (int i = 0; i < list[2]; i++) locationList.remove(i);

        Log.d(TAG, "End of generateRoute");
        return newRoute;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
