package com.tep.sustainability.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        if (getActionBar() != null) getActionBar().setTitle("Feedback &amp; Support");

        ((TextView) findViewById(R.id.mail)).setText(Home.user.getEmail());
    }

    public void sendFeedback(View v) {


        String email = ((TextView) findViewById(R.id.mail)).getText().toString();
        String feedback = ((EditText) findViewById(R.id.feedback)).getText().toString();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("feedback/" + Home.user.getUid());

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put((new Date()).toLocaleString(), feedback);

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
