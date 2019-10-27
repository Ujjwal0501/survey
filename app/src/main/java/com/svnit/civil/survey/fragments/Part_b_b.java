package com.svnit.civil.survey.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;
import com.svnit.civil.survey.models.RatedPreference;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Part_b_b extends Fragment {

    private RatedPreference ratedPreference = new RatedPreference();
    private EditText congestion, safety, airPollution, noisePollution,
            fareHome, fareEducation, fareSocial,
            speedHome, speedEducatin, speedSocial,
            comfortHome, comfortEducation, comfortSocial,
            safetyHome, safetyEducation, safetySocial,
            parkingHome, parkingEducation, parkingSocial,
            otherHome, otherEducation, otherSocial;
    private Spinner rank1, rank2, rank3, rank4, rank5, rank6, rank7;
    private int[] option = {0, 0, 0, 0, 0, 0, 0, 0};
    private View.OnClickListener oneToTwo, saveStep, threeToTwo, twoToThree, twoToOne;
    private CardView step0, step1, step2, one, two, three;
    private ImageView next, prev;
    private Context context;
    private View v;


    public Part_b_b() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_part_b_b, container, false);
        context = v.getContext();
        getActivity().setTitle("Rated Preference");

        congestion = v.findViewById(R.id.congestion);
        safety = v.findViewById(R.id.safety);
        airPollution = v.findViewById(R.id.air_pollution);
        noisePollution = v.findViewById(R.id.noise_pollution);

        fareHome = v.findViewById(R.id.fare_home);
        fareEducation = v.findViewById(R.id.fare_education);
        fareSocial = v.findViewById(R.id.fare_social);
        speedHome = v.findViewById(R.id.speed_home);
        speedEducatin = v.findViewById(R.id.speed_education);
        speedSocial = v.findViewById(R.id.speed_social);
        comfortHome = v.findViewById(R.id.comfort_home);
        comfortEducation = v.findViewById(R.id.comfort_education);
        comfortSocial = v.findViewById(R.id.comfort_social);
        safetyHome = v.findViewById(R.id.safety_home);
        safetyEducation = v.findViewById(R.id.safety_education);
        safetySocial = v.findViewById(R.id.safety_social);
        parkingHome = v.findViewById(R.id.parking_home);
        parkingEducation = v.findViewById(R.id.parking_education);
        parkingSocial = v.findViewById(R.id.parking_social);
        otherHome = v.findViewById(R.id.other_home);
        otherEducation = v.findViewById(R.id.other_education);
        otherSocial = v.findViewById(R.id.other_social);

        rank1 = v.findViewById(R.id.rank_1);
        rank2 = v.findViewById(R.id.rank_2);
        rank3 = v.findViewById(R.id.rank_3);
        rank4 = v.findViewById(R.id.rank_4);
        rank5 = v.findViewById(R.id.rank_5);
        rank6 = v.findViewById(R.id.rank_6);
        rank7 = v.findViewById(R.id.rank_7);

        step0 = v.findViewById(R.id.step0);
        step1 = v.findViewById(R.id.step1);
        step2 = v.findViewById(R.id.step2);
        next = v.findViewById(R.id.next);
        prev = v.findViewById(R.id.prev);
        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);
        three = v.findViewById(R.id.three);

        oneToTwo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyStep0() && Home.STEP<Home.MAX) {
                    // proceed to save
                    step0.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(twoToThree);
                    prev.setOnClickListener(twoToOne);
                    one.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Home.STEP += 1;
                }
            }
        };

        twoToThree = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (verifyStep1() && Home.STEP<Home.MAX) {
                // proceed to save
                step1.setVisibility(View.GONE);
                step2.setVisibility(View.VISIBLE);
                ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                next.setOnClickListener(saveStep);
                prev.setOnClickListener(threeToTwo);
                next.setImageDrawable(getResources().getDrawable(R.drawable.save));
                two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                three.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Home.STEP += 1;
            }
            }
        };

        twoToOne = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Home.STEP<1) return;
                step1.setVisibility(View.GONE);
                step0.setVisibility(View.VISIBLE);
                ((CardView) prev.getParent()).setVisibility(View.GONE);
                next.setOnClickListener(oneToTwo);
                prev.setOnClickListener(null);
                one.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Home.STEP -= 1;
            }
        };

        threeToTwo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Home.STEP<1) return;
                step2.setVisibility(View.GONE);
                step1.setVisibility(View.VISIBLE);
                next.setOnClickListener(twoToThree);
                prev.setOnClickListener(twoToOne);
                next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                three.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Home.STEP -= 1;
            }
        };

        saveStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save to firebase
                if (!verifyStep2()) {
                    Snackbar.make(three, "One option should have only one rank.", Snackbar.LENGTH_LONG).show();
                    return ;
                }
                updateFirebase();
            }
        };

        Home.STEP = 0; Home.MAX = 2;
        Home.backBtn = prev;
        next.setOnClickListener(oneToTwo);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Home.backBtn = null;
    }

    private void updateFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(user.getUid());
        dbRef.child("rated_preference_survey").setValue(ratedPreference).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isCanceled()) {
                    // was cancelled
                    // do something
                    Toast.makeText(context, "Cancelled\n" + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } else if (task.isSuccessful()) {
                    // success, do something
                    updatePref();
                    Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();
                    Home.fragmentManager.popBackStack();
                } else {
                    // failed, do something
                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean verifyStep0() {
        if (congestion.getText().toString().equals("")) { congestion.setError("Required"); return false;}
        if (safety.getText().toString().equals("")) { safety.setError("Required"); return false;}
        if (airPollution.getText().toString().equals("")) { airPollution.setError("Required"); return false;}
        if (noisePollution.getText().toString().equals("")) { noisePollution.setError("Required"); return false;}

        Map<String, String> temp = new HashMap<String, String>();

        temp.put("traffic congestion", congestion.getText().toString());
        temp.put("safety and security", safety.getText().toString());
        temp.put("air pollution", airPollution.getText().toString());
        temp.put("noise pollution", noisePollution.getText().toString());

        ratedPreference.setMajor_issue_of_transport_system(temp);

        return true;
    }

    private boolean verifyStep1() {
        if (fareHome.getText().toString().equals("")) { fareHome.setError("Required"); return false;}
        if (fareEducation.getText().toString().equals("")) { fareEducation.setError("Required"); return false;}
        if (fareSocial.getText().toString().equals("")) { fareSocial.setError("Required"); return false;}
        if (speedHome.getText().toString().equals("")) { speedHome.setError("Required"); return false;}
        if (speedEducatin.getText().toString().equals("")) { speedEducatin.setError("Required"); return false;}
        if (speedSocial.getText().toString().equals("")) { speedSocial.setError("Required"); return false;}
        if (comfortHome.getText().toString().equals("")) { comfortHome.setError("Required"); return false;}
        if (comfortEducation.getText().toString().equals("")) { comfortEducation.setError("Required"); return false;}
        if (comfortSocial.getText().toString().equals("")) { comfortSocial.setError("Required"); return false;}
        if (safetyHome.getText().toString().equals("")) { safetyHome.setError("Required"); return false;}
        if (safetyEducation.getText().toString().equals("")) { safetyEducation.setError("Required"); return false;}
        if (safetySocial.getText().toString().equals("")) { safetySocial.setError("Required"); return false;}
        if (parkingHome.getText().toString().equals("")) { parkingHome.setError("Required"); return false;}
        if (parkingEducation.getText().toString().equals("")) { parkingEducation.setError("Required"); return false;}
        if (parkingSocial.getText().toString().equals("")) { parkingSocial.setError("Required"); return false;}
        if (otherHome.getText().toString().equals("")) { otherHome.setError("Required"); return false;}
        if (otherEducation.getText().toString().equals("")) { otherEducation.setError("Required"); return false;}
        if (otherSocial.getText().toString().equals("")) { otherSocial.setError("Required"); return false;}

        Map<String, String> temp = new HashMap<String, String>();

        temp.put("fare_for_home", fareHome.getText().toString());
        temp.put("fare_education", fareEducation.getText().toString());
        temp.put("fare_event", fareSocial.getText().toString());

        temp.put("speed_for_home", speedHome.getText().toString());
        temp.put("speed_education", speedEducatin.getText().toString());
        temp.put("speed_event", speedSocial.getText().toString());

        temp.put("comfort_for_home", comfortHome.getText().toString());
        temp.put("comfort_education", comfortEducation.getText().toString());
        temp.put("comfort_event", comfortSocial.getText().toString());

        temp.put("safety_for_home", safetyHome.getText().toString());
        temp.put("safety_education", safetyEducation.getText().toString());
        temp.put("safety_event", safetySocial.getText().toString());

        temp.put("parking_for_home", parkingHome.getText().toString());
        temp.put("parking_education", parkingEducation.getText().toString());
        temp.put("parking_event", parkingSocial.getText().toString());

        temp.put("other_for_home", otherHome.getText().toString());
        temp.put("other_education", otherEducation.getText().toString());
        temp.put("other_event", otherSocial.getText().toString());

        ratedPreference.setImportant_attribute_for_choosing_transport(temp);

        return true;
    }

    private boolean verifyStep2() {
        for (int i = 0; i < 7; i++) option[i] = -1;
        option[rank1.getSelectedItemPosition()] = 1;
        if (option[rank2.getSelectedItemPosition()] == -1) option[rank2.getSelectedItemPosition()] = 2; else { ( (TextView) rank2.getSelectedView()).setError("Invalid ranking."); return false;}
        if (option[rank3.getSelectedItemPosition()] == -1) option[rank3.getSelectedItemPosition()] = 3; else { ( (TextView) rank3.getSelectedView()).setError("Invalid ranking."); return false;}
        if (option[rank4.getSelectedItemPosition()] == -1) option[rank4.getSelectedItemPosition()] = 4; else { ( (TextView) rank4.getSelectedView()).setError("Invalid ranking."); return false;}
        if (option[rank5.getSelectedItemPosition()] == -1) option[rank5.getSelectedItemPosition()] = 5; else { ( (TextView) rank5.getSelectedView()).setError("Invalid ranking."); return false;}
        if (option[rank6.getSelectedItemPosition()] == -1) option[rank6.getSelectedItemPosition()] = 6; else { ( (TextView) rank6.getSelectedView()).setError("Invalid ranking."); return false;}
        if (option[rank7.getSelectedItemPosition()] == -1) option[rank7.getSelectedItemPosition()] = 7; else { ( (TextView) rank7.getSelectedView()).setError("Invalid ranking."); return false;}

        Map<String, String> temp = new HashMap<String, String>();

        temp.put("rank_1", rank1.getSelectedItem().toString());
        temp.put("rank_2", rank2.getSelectedItem().toString());
        temp.put("rank_3", rank3.getSelectedItem().toString());
        temp.put("rank_4", rank4.getSelectedItem().toString());
        temp.put("rank_5", rank5.getSelectedItem().toString());
        temp.put("rank_6", rank6.getSelectedItem().toString());
        temp.put("rank_7", rank7.getSelectedItem().toString());

        ratedPreference.setAppropriate_policy_rank_for_better_transport(temp);

        return true;
    }

    private void updatePref() {
        Home.PARTBB = 1;
        Home.sharedPref.edit().putInt("partbb", 1).apply();
    }

}
