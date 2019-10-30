package com.svnit.civil.survey;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Phoneverify extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String phone, phoneVerificationId, newPhone;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private EditText tv_phone, tv_code;
    private Button bt_edit_phone, bt_send, bt_verify;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private Handler handler = new Handler();
    private Runnable runnable;
    // create a clickListener for resending code
    final View.OnClickListener resendCode = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // listener for resend code
            // get the authId and resend the old verification code
            PhoneAuthProvider.getInstance().verifyPhoneNumber(newPhone, 60, TimeUnit.SECONDS, Phoneverify.this, callbacks, mResendToken);
            handler.postDelayed(runnable, 60 * 1000);
        }
    };
    // create a clickListener for sending code
    final View.OnClickListener sendCode = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // listener for send code
            // remove this listener, add resend listener, handler to activate this button
            newPhone = "+91" + tv_phone.getText().toString();
            verifyPhone();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user == null) {
            // no user logged-in, normally the new user should be logged in
            // restart the sign-in/sign-up activity
            Toast.makeText(this, getString(R.string.login_to_phoneverify_error), Toast.LENGTH_LONG).show();

            // flags to kill all old activities and start afresh
            startAfresh();
        }

        /* TODO
           Check if empty call to this activity should be accepted
           check if we should accept null/empty value of phone
         */
        try {
            phone = getIntent().getExtras().getString("phone");
        } catch (Exception e) {
            e.printStackTrace();
            phone = "+91";
        }
        if (phone == null || phone.equals("")) {
            // phone number is empty, not expected
//            Toast.makeText(this, getString(R.string.login_to_phoneverify_error), 6*1000).show();
//            startAfresh();
            phone = "+91";
        }

        tv_code = findViewById(R.id.code);
        tv_phone = findViewById(R.id.phone);
        bt_edit_phone = findViewById(R.id.edit_phone);
        bt_send = findViewById(R.id.send_code);
        bt_verify = findViewById(R.id.verify);

        // when user edits the phone number, we should remove resend listener
        // attach the send listener
        bt_edit_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_send.setEnabled(true);
                bt_send.setText("Send");
                bt_send.setOnClickListener(sendCode);
                tv_phone.setEnabled(true);
                ((LinearLayout) bt_verify.getParent()).setVisibility(View.INVISIBLE);
                if (runnable != null) handler.removeCallbacks(runnable);
            }
        });

        bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.updatePhoneNumber(PhoneAuthProvider.getCredential(phoneVerificationId,
                        tv_code.getText().toString().trim()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Snackbar.make(bt_verify, "Verification successful.", 3000).show();

                                    ((LinearLayout) bt_send.getParent()).setEnabled(false);
                                    ((LinearLayout) bt_send.getParent()).setEnabled(false);

                                    // save info to firebae database
                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference userRef = db.getReference(user.getUid() + "/account");

                                    // update info in firebase database
                                    userRef.child("phone").child("value").setValue(newPhone);
                                    userRef.child("phone").child("status").setValue("Verified");

                                    // proceed
                                    startActivity(new Intent(Phoneverify.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    Phoneverify.this.finish();

                                } else {
                                    // update failed
                                    if (task.getException() == null || task.getException().getLocalizedMessage() == null)
                                        Snackbar.make(bt_verify, getString(R.string.unknown_error), 3000).show();
                                    else
                                        Snackbar.make(bt_verify, task.getException().getLocalizedMessage(), 3000).show();
                                }
                            }
                        });
            }
        });

        // set preferred initial state for textview
        bt_send.setOnClickListener(sendCode);
        tv_phone.setText(phone.substring(3));
        tv_phone.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * function closes all the on-going activities and
     * starts the new task of this app
     */
    private void startAfresh() {
        startActivity(new Intent(this, Splash.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("reason", "unexpected logout"));
    }

    private void verifyPhone() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                // verification complete, proceed
                user.updatePhoneNumber(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(bt_verify, "Verification successful.", 3000).show();

                            ((LinearLayout) bt_send.getParent()).setEnabled(false);
                            ((LinearLayout) bt_send.getParent()).setEnabled(false);

                            // update complete
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = db.getReference("users").child(user.getUid());

                            // update info in firebase database
                            userRef.child("phone").child("value").setValue(newPhone);
                            userRef.child("phone").child("status").setValue("Verified");

                            startActivity(new Intent(Phoneverify.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            Phoneverify.this.finish();
                        } else {
                            // update failed
                            if (task.getException() == null || task.getException().getLocalizedMessage() == null)
                                Snackbar.make(bt_verify, getString(R.string.unknown_error), 3000).show();
                            else
                                Snackbar.make(bt_verify, task.getException().getLocalizedMessage(), 3000).show();
                        }
                    }
                });

                Snackbar.make(bt_verify, "Phone Verification Successful!", 3000).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                // save the resend id and token for later use
                phoneVerificationId = s;            // used to verify manually
                mResendToken = forceResendingToken; // used to resend verification code

                // make the verification text view appear
                ((LinearLayout) bt_verify.getParent()).setVisibility(View.VISIBLE);
                tv_code.setText("");

                // modify the UI: make 'resend' button, attach listener, disable button
                bt_send.setText("Resend");
                bt_send.setOnClickListener(resendCode);
                bt_send.setEnabled(false);


                /* TODO
                 *
                 * Check for multiple send/resend clicks
                 *
                 */

                // enable the resend button after 60 seconds
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        bt_send.setEnabled(true);
                    }
                };
                handler.postDelayed(runnable, 1000 * 60);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // error in verification process, inform the user
                if (e == null || e.getLocalizedMessage() == null)
                    Snackbar.make(bt_verify, getString(R.string.unknown_error), 3000).show();
                else Snackbar.make(bt_verify, e.getLocalizedMessage(), 3000).show();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                // could not retrieve verification code
                Snackbar.make(bt_verify, "Auto verification timed out. You must manually enter the code.", 4000).show();
            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(newPhone, 60, TimeUnit.SECONDS, this, callbacks);
    }
}
