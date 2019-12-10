package com.tep.sustainability.survey.helpers;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimeButton extends Button {

    public TimeButton(final Context context) {
        super(context);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog testDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Log.d("Button", i+""+i1);
                        TimeButton.this.setText(i+" : "+i1);
                        TimeButton.this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    }
                }, 9, 10, true);
                testDialog.setCancelable(false);

                testDialog.show();
                ((Button) view).setError(null);
            }
        });
    }

    public TimeButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog testDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Log.d("Button", i+""+i1);
                        TimeButton.this.setText(i+" : "+i1);
                        TimeButton.this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    }
                }, 9, 10, true);
                testDialog.setCancelable(false);

                testDialog.show();
                ((Button) view).setError(null);
            }
        });
    }

    public TimeButton(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Log.d("Button", i+""+i1);
                        TimeButton.this.setText(i+" : "+i1);
                        TimeButton.this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    }
                }, 9, 10, true);
                dialog.setCancelable(false);

                dialog.show();
            }
        });
    }

    @TargetApi(21)
    public TimeButton(final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog testDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Log.d("Button", i+""+i1);
                        TimeButton.this.setText(i+" : "+i1);
                        TimeButton.this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    }
                }, 9, 10, true);
                testDialog.setCancelable(false);

                testDialog.show();
                ((Button) view).setError(null);
            }
        });
    }
}
