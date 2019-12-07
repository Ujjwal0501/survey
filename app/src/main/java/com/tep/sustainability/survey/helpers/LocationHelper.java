package com.tep.sustainability.survey.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.tep.sustainability.survey.PermissionActivity;

public class LocationHelper {
    public final static int LOCATION_RC = 111;

    public boolean isEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is new method provided in API 28
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            // This is Deprecated in API 28
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return  (mode != Settings.Secure.LOCATION_MODE_OFF);

        }
    }


    public boolean checkPermission(Context context) {
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    public void reqPermission(@Nullable Activity activity, Context context) {
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    LOCATION_RC);
        } else {
            // TODO: permission requested from background service
            context.startActivity(new Intent(context, PermissionActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("task", "permission"));
        }
    }

    public boolean reqEnable(@Nullable final Context context) {

        try {
//            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(final DialogInterface dialog, final int id) {
//                            context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(final DialogInterface dialog, final int id) {
//                            dialog.cancel();
//                        }
//                    });
//            final AlertDialog alert = builder.create();
//            alert.show();
        } catch (Exception e) {
            e.printStackTrace();

            // TODO: permission requested from background service
            context.startActivity(new Intent(context, PermissionActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("task", "activate"));
        }
        return true;
    }
}
