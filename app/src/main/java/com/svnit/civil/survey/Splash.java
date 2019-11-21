package com.svnit.civil.survey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

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
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "Splash";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.getidtoken))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            signIn();
        } else if (user.getEmail() == null || user.getEmail().equals("")) {
            // user cannot register without email, weird behaviour
            // ask user to sign in again
            FirebaseAuth.getInstance().signOut();
            signIn();
        } else {
            updateUI(user);
        }

    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Sign-In is required to access the app.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getIdToken());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null) return;
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user/" + user.getUid() + "/account");

                            try { if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                dbRef.child("email").setValue(user.getEmail());
                                dbRef.child("name").setValue(user.getDisplayName());
                            } } catch (Exception e) { Log.d(TAG, "" + e.getLocalizedMessage()); }

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Splash.this, "Authentication Failed. Try again", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        final SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/"+user.getUid()+"/loginInstance");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uuid = dataSnapshot.getValue(String.class);
                if (uuid == null || uuid.equals("")) {
                    uuid = UUID.randomUUID().toString();
                    preferences.edit().putString("uuid", uuid).apply();
                    reference.setValue(uuid);

                    startActivity(new Intent(Splash.this, Home.class));
                    finish();
                } else if (uuid.equals(preferences.getString("uuid", ""))) {

                    startActivity(new Intent(Splash.this, Home.class));
                    finish();
                } else {
                    Toast.makeText(Splash.this, "Multiple device login with same account is not allowed.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Splash.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
