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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;
import com.svnit.civil.survey.models.PublicTransport;
import com.svnit.civil.survey.models.TransportDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class Part_a_c extends Fragment {

    PublicTransport publicTransport = new PublicTransport();
    private EditText busDistance, busWaitTime, busReachTime, busFrequency,
             brtDistance, brtWaitTime, brtReachTime, brtFrequency,
             autoDistance, autoWaitTime, autoReachTime, autoFrequency;
    private Spinner busReliability, busSafety, busFare, busComfort,
            brtReliability, brtSafety, brtFare, brtComfort,
            autoReliability, autoSafety, autoFare, autoComfort;
    private View.OnClickListener oneToTwo, saveStep, threeToTwo, twoToThree, twoToOne;
    private CardView step0, step1, step2, one, two, three;
    private ImageView next, prev;
    private Context context;
    private View v;



    public Part_a_c() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_part_a_c, container, false);
        context = v.getContext();
        getActivity().setTitle("Public Transport");

        busDistance = v.findViewById(R.id.bus_distance);
        busWaitTime = v.findViewById(R.id.bus_wait_time);
        busReachTime = v.findViewById(R.id.bus_time_to_reach);
        busFrequency = v.findViewById(R.id.bus_frequency);
        brtDistance = v.findViewById(R.id.brt_distance);
        brtWaitTime = v.findViewById(R.id.brt_wait_time);
        brtReachTime = v.findViewById(R.id.brt_time_to_reach);
        brtFrequency = v.findViewById(R.id.brt_frequency);
        autoDistance = v.findViewById(R.id.auto_distance);
        autoWaitTime = v.findViewById(R.id.auto_wait_time);
        autoReachTime = v.findViewById(R.id.auto_time_to_reach);
        autoFrequency = v.findViewById(R.id.auto_frequency);
        busReliability = v.findViewById(R.id.bus_reliability);
        busSafety = v.findViewById(R.id.bus_safety);
        busFare = v.findViewById(R.id.bus_fare);
        busComfort = v.findViewById(R.id.bus_comfort);
        brtReliability = v.findViewById(R.id.brt_reliability);
        brtSafety = v.findViewById(R.id.brt_safety);
        brtFare = v.findViewById(R.id.brt_fare);
        brtComfort = v.findViewById(R.id.brt_comfort);
        autoReliability = v.findViewById(R.id.auto_reliability);
        autoSafety = v.findViewById(R.id.auto_safety);
        autoFare = v.findViewById(R.id.auto_fare);
        autoComfort = v.findViewById(R.id.auto_comfort);


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
                    Home.STEP += 1;;
;
;
;
;
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
                if (!verifyStep2()) return;
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
        dbRef.child("public_transport_survey").setValue(publicTransport).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        if (busDistance.getText().toString().equals("")) { busDistance.setError("Required."); return false; }
        if (busReachTime.getText().toString().equals("")) { busReachTime.setError("Required."); return false; }
        if (busWaitTime.getText().toString().equals("")) { busWaitTime.setError("Required."); return false; }
        if (busFrequency.getText().toString().equals("")) { busFrequency.setError("Required."); return false; }
        if (busReliability.getSelectedItemPosition() == 0) { ((TextView) busReliability.getSelectedView()).setError("Select option"); return false; }
        if (busSafety.getSelectedItemPosition() == 0) { ((TextView) busSafety.getSelectedView()).setError("Select option"); return false; }
        if (busFare.getSelectedItemPosition() == 0) { ((TextView) busFare.getSelectedView()).setError("Select option"); return false; }
        if (busComfort.getSelectedItemPosition() == 0) { ((TextView) busComfort.getSelectedView()).setError("Select option"); return false; }

        TransportDetails cityBus = new TransportDetails();
        cityBus.setStop_distance(busDistance.getText().toString());
        cityBus.setTime_to_stoppage(busReachTime.getText().toString());
        cityBus.setAverage_wait_time(busWaitTime.getText().toString());
        cityBus.setUsage_frequency(busFrequency.getText().toString());
        cityBus.setService_reliability(busReliability.getSelectedItem().toString());
        cityBus.setSafety(busSafety.getSelectedItem().toString());
        cityBus.setFare(busFare.getSelectedItem().toString());
        cityBus.setCleanliness_and_comfort(busComfort.getSelectedItem().toString());

        publicTransport.setCityBus(cityBus);

        return true;
    }

    private boolean verifyStep1() {
        if (brtDistance.getText().toString().equals("")) { brtDistance.setError("Required."); return false; }
        if (brtReachTime.getText().toString().equals("")) { brtReachTime.setError("Required."); return false; }
        if (brtWaitTime.getText().toString().equals("")) { brtWaitTime.setError("Required."); return false; }
        if (brtFrequency.getText().toString().equals("")) { brtFrequency.setError("Required."); return false; }
        if (brtReliability.getSelectedItemPosition() == 0) { ((TextView) brtReliability.getSelectedView()).setError("Select option"); return false; }
        if (brtSafety.getSelectedItemPosition() == 0) { ((TextView) brtSafety.getSelectedView()).setError("Select option"); return false; }
        if (brtFare.getSelectedItemPosition() == 0) { ((TextView) brtFare.getSelectedView()).setError("Select option"); return false; }
        if (brtComfort.getSelectedItemPosition() == 0) { ((TextView) brtComfort.getSelectedView()).setError("Select option"); return false; }

        TransportDetails brt = new TransportDetails();
        brt.setStop_distance(busDistance.getText().toString());
        brt.setTime_to_stoppage(busReachTime.getText().toString());
        brt.setAverage_wait_time(busWaitTime.getText().toString());
        brt.setUsage_frequency(busFrequency.getText().toString());
        brt.setService_reliability(busReliability.getSelectedItem().toString());
        brt.setSafety(busSafety.getSelectedItem().toString());
        brt.setFare(busFare.getSelectedItem().toString());
        brt.setCleanliness_and_comfort(busComfort.getSelectedItem().toString());

        publicTransport.setBrt(brt);

        return true;
    }

    private boolean verifyStep2() {
        if (autoDistance.getText().toString().equals("")) { autoDistance.setError("Required."); return false; }
        if (autoReachTime.getText().toString().equals("")) { autoReachTime.setError("Required."); return false; }
        if (autoWaitTime.getText().toString().equals("")) { autoWaitTime.setError("Required."); return false; }
        if (autoFrequency.getText().toString().equals("")) { autoFrequency.setError("Required."); return false; }
        if (autoReliability.getSelectedItemPosition() == 0) { ((TextView) autoReliability.getSelectedView()).setError("Select option"); return false; }
        if (autoSafety.getSelectedItemPosition() == 0) { ((TextView) autoSafety.getSelectedView()).setError("Select option"); return false; }
        if (autoFare.getSelectedItemPosition() == 0) { ((TextView) autoFare.getSelectedView()).setError("Select option"); return false; }
        if (autoComfort.getSelectedItemPosition() == 0) { ((TextView) autoComfort.getSelectedView()).setError("Select option"); return false; }

        TransportDetails auto = new TransportDetails();
        auto.setStop_distance(busDistance.getText().toString());
        auto.setTime_to_stoppage(busReachTime.getText().toString());
        auto.setAverage_wait_time(busWaitTime.getText().toString());
        auto.setUsage_frequency(busFrequency.getText().toString());
        auto.setService_reliability(busReliability.getSelectedItem().toString());
        auto.setSafety(busSafety.getSelectedItem().toString());
        auto.setFare(busFare.getSelectedItem().toString());
        auto.setCleanliness_and_comfort(busComfort.getSelectedItem().toString());

        publicTransport.setAuto(auto);

        return true;
    }

    private void updatePref() {
        Home.PARTAC = 1;
        Home.sharedPref.edit().putInt("partac", 1).apply();
    }

}
