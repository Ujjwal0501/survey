package com.svnit.civil.survey.login;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.svnit.civil.survey.R;

public class Login extends AppCompatActivity {
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new Signin()).commit();
    }

    @Override
    public void onBackPressed() {
        int fragmentCount = fragmentManager.getBackStackEntryCount();
        if (fragmentCount == 0)
            super.onBackPressed();
        else
            fragmentManager.popBackStack();
    }

    public void passwordReset(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Password Reset");
        dialog.setContentView(R.layout.dialog_reset_password);
        dialog.findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ((EditText) dialog.findViewById(R.id.email_edittext)).getText().toString().trim();
                if (email.equals("") || email == null) {
                    ((EditText) dialog.findViewById(R.id.email_edittext)).setError("Field cannot be blank.");
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Link sent to " + email, Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } else {
                            if (task.getException() == null || task.getException().getLocalizedMessage() == null)
                                Toast.makeText(Login.this, getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Login.this, task.getException().getLocalizedMessage() + "", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
