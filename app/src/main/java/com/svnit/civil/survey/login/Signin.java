package com.svnit.civil.survey.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.Phoneverify;
import com.svnit.civil.survey.R;
import com.svnit.civil.survey.Splash;

/**
 * A simple {@link Fragment} subclass.
 */
public class Signin extends Fragment {
    private String TAG = "SC";
    private Context context;
    private View fragment;
    private Activity activity;

    public Signin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signin, container, false);
        context = v.getContext();
        fragment = v;
        activity = getActivity();

        Button customSignUp = v.findViewById(R.id.custom_signup_button);
        Button customSignIn = v.findViewById(R.id.custom_signin_button);

        final EditText email = v.findViewById(R.id.email_edittext);
        final EditText password = v.findViewById(R.id.password_edittext);

        customSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.fragmentManager.beginTransaction().replace(R.id.container, new Signup()).commit();
            }
        });
        customSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e, p;
                e = email.getText().toString().trim();
                p = password.getText().toString().trim();

                if (e.equals("")) {
                    email.setError("Email cannot be empty!");
                    return;
                } else if (p.equals("")) {
                    password.setError("Password cannot be blank!");
                    return;
                }

                // if entry is complete initiate sign-in
                initSignin(e, p);
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();
        if (activity != null)
            activity.setTitle("Social India - SignIn");
    }

    private void initSignin(String email, String password) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user == null) {
                                startActivity(new Intent(getContext(), Splash.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra("reason", "unexpected logout"));

                            } else if (user.getPhoneNumber() == null) {
                                startActivity(new Intent(getContext(), Phoneverify.class).putExtra("phone", "+91"));
                                if (activity != null) activity.finish();

                            } else {
                                startActivity(new Intent(context, Home.class));
                                if (activity != null) activity.finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            // updateUI(null);
                            // update failed
                            if (task.getException() == null || task.getException().getLocalizedMessage() == null)
                                Snackbar.make(fragment, getString(R.string.unknown_error), 3000).show();
                            else
                                Snackbar.make(fragment, task.getException().getLocalizedMessage(), 3000).show();
                        }

                        // ...
                    }
                });
    }

}
