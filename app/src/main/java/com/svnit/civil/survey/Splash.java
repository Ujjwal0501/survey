package com.svnit.civil.survey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.svnit.civil.survey.login.Login;

/**
 * This activity shows Launch Screen
 * Then the logged-in status is determined
 * and accordingly, the flow continues
 * <p>
 * If user is null, sign-in flow is started and
 * upon successful sign-up which follows login,
 * next activity appears
 */
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        // persist a local copy of database, only what is synced
        // this has to be done only once and before use of any instance
        // this is the best place to setPersistenceFirebaseDatabase
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // initiate sign-in UI and start the flow
            logUi();
        } else if (user.getEmail() == null || user.getEmail().equals("")) {
            // user cannot register without email, weird behaviour
            // ask user to sign in again
            FirebaseAuth.getInstance().signOut();
            logUi();
        } else if (user.getPhoneNumber() == null || user.getPhoneNumber().equals("")) {
            Toast.makeText(this, "Phone number was not verified during sign-up. It's necessary to provide a phone number.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Phoneverify.class).putExtra("phone", "+91"));
            this.finish();
        } else {
            startActivity(new Intent(this, Home.class));
            this.finish();
        }
    }

    public void logUi() {
        startActivity(new Intent(this, Login.class));
        this.finish();
    }

}
