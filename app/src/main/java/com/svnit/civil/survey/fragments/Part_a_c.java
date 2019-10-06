package com.svnit.civil.survey.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Part_a_c extends Fragment {
    EditText busDistance, busWaitTime, busReachTime, busFrequency,
             brtDistance, brtWaitTime, brtReachTime, brtFrequency,
             autoDistance, autoWaitTime, autoReachTime, autoFrequency;
    Spinner busReliability, busSafety, busFare, busComfort,
            brtReliability, brtSafety, brtFare, brtComfort,
            autoReliability, autoSafety, autoFare, autoComfort;
    View.OnClickListener oneToTwo, saveStep, threeToTwo, twoToThree, twoToOne;
    CardView step0, step1, step2, one, two, three;
    ImageView next, prev;
    Context context;
    View v;



    public Part_a_c() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_part_a_c, container, false);
        context = v.getContext();

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
                if (verifyForm() && Home.STEP<Home.MAX) {
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
                if (verifyForm() && Home.STEP<Home.MAX) {
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

    private boolean verifyForm() {

        return true;
    }

}
