package com.svnit.civil.survey.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.svnit.civil.survey.MapLocation;

public class PlaceButton extends Button {
    public PlaceButton(final Context context) {
        super(context);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                SupportMapFragment mapFragment = getSu
//                GoogleMap map =
                context.startActivity(new Intent(context, MapLocation.class));
            }
        });
    }

    public PlaceButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                SupportMapFragment mapFragment = getSu
//                GoogleMap map =
                context.startActivity(new Intent(context, MapLocation.class));
            }
        });
    }

    public PlaceButton(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                SupportMapFragment mapFragment = getSu
//                GoogleMap map =
                context.startActivity(new Intent(context, MapLocation.class));
            }
        });
    }

    @TargetApi(21)
    public PlaceButton(final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                SupportMapFragment mapFragment = getSu
//                GoogleMap map =
                context.startActivity(new Intent(context, MapLocation.class));
            }
        });
    }
}
