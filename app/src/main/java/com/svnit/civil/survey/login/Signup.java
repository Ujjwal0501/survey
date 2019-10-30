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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.Phoneverify;
import com.svnit.civil.survey.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends Fragment {
    private Context context;
    private View fragment;
    private Activity activity;

    public Signup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signup, container, false);
//        getActivity().setTitle("Survey - SignUp");
        context = v.getContext();
        fragment = v;
        activity = getActivity();

        final Button customSignIn = v.findViewById(R.id.custom_signin_button);
        final Button customSignUp = v.findViewById(R.id.custom_signup_button);
        final EditText name = v.findViewById(R.id.name_edittext);
        final EditText email = v.findViewById(R.id.email_edittext);
        final EditText password = v.findViewById(R.id.password_edittext);
        final EditText cnfPassword = v.findViewById(R.id.confirm_password_edittext);
        final EditText phone = v.findViewById(R.id.phone_edittext);

        customSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.fragmentManager.beginTransaction().replace(R.id.container, new Signin()).commit();
            }
        });
        customSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e, p, cp, n, ph;
                e = email.getText().toString().trim();
                p = password.getText().toString().trim();
                cp = cnfPassword.getText().toString().trim();
                n = name.getText().toString().trim();
                ph = "+91" + phone.getText().toString().trim();     // append the country(India) code

                if (n.equals("")) {
                    name.setError("Name cannot be empty!");
                    return;
                } else if (e.equals("")) {
                    email.setError("Email cannot be empty!");
                    return;
                } else if (ph.equals("")) {
                    phone.setError("Mobile Number is required.");
                    return;
                } else if (p.equals("")) {
                    password.setError("Password cannot be blank!");
                    return;
                } else if (cp.equals("")) {
                    cnfPassword.setError("Password not confirmed.");
                    return;
                }

                if (p.equals(cp)) initSignUp(e, p, n, ph);
                else cnfPassword.setError("Passwords do not match.");
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();
        if (activity != null)
            activity.setTitle("SignUp");
    }

    private void initSignUp(final String email, final String password, final String name, final String phone) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user;
                            if (task.getResult() != null) {
                                user = task.getResult().getUser();
                            } else {
                                Toast.makeText(context, getString(R.string.unexpected_logout), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(context, Login.class));
                                if (activity != null) activity.finish();
                                return;
                            }
                            // send the verification mail
                            user.sendEmailVerification();

                            // set username for the member
                            user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build());

                            // connect to firebase database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = database.getReference(user.getUid() + "/account");

                            // store user info to firebase database
                            userRef.child("name").setValue(name);
                            userRef.child("phone").child("value").setValue(phone);
                            userRef.child("phone").child("status").setValue("Unverified");
                            userRef.child("email").child("value").setValue(email);
                            userRef.child("email").child("status").setValue("Unverified");

                            // proceed for optional verification step
                            // pass the required information
                            Bundle bundle = new Bundle();
                            bundle.putString("phone_number", phone);
                            bundle.putString("email", email);

                            // check if user has provided phone number
                            if (user.getPhoneNumber() == null) {
                                startActivity(new Intent(getContext(), Phoneverify.class).putExtra("phone", phone));
                                if (activity != null) activity.finish();

                            } else {
                                startActivity(new Intent(context, Home.class));
                                if (activity != null) activity.finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            // Update(UI)
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
