package com.svnit.civil.survey.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

public class PlaceButton extends Button {
    public PlaceButton(Context context) {
        super(context);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }

    public PlaceButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }

    public PlaceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }

    @TargetApi(21)
    public PlaceButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }
}
