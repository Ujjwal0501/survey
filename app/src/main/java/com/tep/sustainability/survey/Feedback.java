package com.tep.sustainability.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private static final String TAG = "Feedback";
    private TextView charCount;
    private EditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        if (getActionBar() != null) getActionBar().setTitle("Feedback &amp; Support");

        ((TextView) findViewById(R.id.mail)).setText(Home.user.getEmail());
        charCount = (TextView) findViewById(R.id.char_count);
        feedback = (EditText) findViewById(R.id.feedback);

        feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int l = s.length();
                charCount.setText("" + l);
                if (l > 400) ((LinearLayout) feedback.getParent()).setBackgroundResource(R.drawable.border_error);
                else ((LinearLayout) feedback.getParent()).setBackgroundResource(R.drawable.border_normal);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void sendFeedback(View v) {
        
        String email = ((TextView) findViewById(R.id.mail)).getText().toString();
        String feed = feedback.getText().toString();

        if (feed.equals("")) {
            ((EditText) findViewById(R.id.feedback)).setError("You are forgetting the feedback!");
            return;
        }

        if (feed.length() > 400) {
            feedback.setError("You feedback is large. Remove some text");
            return;
        }

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("feedback/" + Home.user.getUid());

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put((new Date()).toLocaleString(), feed);

        dbRef.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Feedback.this, "Feedback submitted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(Feedback.this, "Please try again later", Toast.LENGTH_LONG).show();
                    try { Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage()); } catch (Exception e) { e.printStackTrace(); }
                }
            }
        });

    }
}
