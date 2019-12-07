package com.tep.sustainability.survey.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

public class WatchedEditText extends EditText {

    public WatchedEditText(final Context context) {
        super(context);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.equals("")) return;
                if (Integer.parseInt(str) > 5) {
                    Toast.makeText(context, "Allowed range is 1-5", Toast.LENGTH_SHORT).show();
                }
                else {
                    WatchedEditText.this.getNextFocusForwardId();
                    nextFocusCallback();
                }
            }
        });
    }

    public WatchedEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.equals("")) return;
                if (Integer.parseInt(str) > 5) {
                    WatchedEditText.this.setText("");
                    Toast.makeText(context, "Allowed range is 1-5", Toast.LENGTH_SHORT).show();
                }
                else {
                    nextFocusCallback();
                }
            }
        });
    }

    public WatchedEditText(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.equals("")) return;
                if (Integer.parseInt(str) > 5) {
                    Toast.makeText(context, "Allowed range is 1-5", Toast.LENGTH_SHORT).show();
                }
                else {
                    nextFocusCallback();
                }
            }
        });
    }

    @TargetApi(21)
    public WatchedEditText(final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.equals("")) return;
                if (Integer.parseInt(str) > 5) {
                    Toast.makeText(context, "Allowed range is 1-5", Toast.LENGTH_SHORT).show();
                }
                else {
                    nextFocusCallback();
                }
            }
        });
    }

    private void nextFocusCallback() {
        ;
    }
}
