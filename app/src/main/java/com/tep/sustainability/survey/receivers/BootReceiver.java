package com.tep.sustainability.survey.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.tep.sustainability.survey.services.AutoService;
import com.tep.sustainability.survey.R;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AutoService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(serviceIntent);
            Toast.makeText(context, context.getString(R.string.app_name)+" service started successfully!", Toast.LENGTH_SHORT).show();
            return ;
        }
        context.startService(serviceIntent);
        Toast.makeText(context, context.getString(R.string.app_name)+" service started successfully!", Toast.LENGTH_SHORT).show();
    }
}
