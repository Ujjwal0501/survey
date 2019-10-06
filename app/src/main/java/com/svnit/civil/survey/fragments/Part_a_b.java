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
public class Part_a_b extends Fragment {
    EditText  m_emi, mfuel_cost, m_maint, mpark_cost, reg;
    Spinner m_income, res_type, car_count, twow_count, bicycle_count;
    View.OnClickListener nextStep, saveStep, backStep;
    CardView step0, step1, one, two;
    ImageView next, prev;
    Context context;
    View v;


    public Part_a_b() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_part_a_b, container, false);
        context = v.getContext();

        m_emi = v.findViewById(R.id.m_emi);
        mfuel_cost = v.findViewById(R.id.mfuel_cost);
        m_maint = v.findViewById(R.id.m_maint);
        mpark_cost = v.findViewById(R.id.mpark_cost);
        reg = v.findViewById(R.id.reg_charge);
        m_income = v.findViewById(R.id.m_income);
        res_type = v.findViewById(R.id.res_type);
        car_count = v.findViewById(R.id.car_count);
        twow_count = v.findViewById(R.id.twow_count);
        bicycle_count = v.findViewById(R.id.bicylce_count);

        step0 = v.findViewById(R.id.step0);
        step1 = v.findViewById(R.id.step1);
        next = v.findViewById(R.id.next);
        prev = v.findViewById(R.id.prev);
        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);

        nextStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyForm() && Home.STEP<Home.MAX) {
                    // proceed to save
                    step0.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(saveStep);
                    prev.setOnClickListener(backStep);
                    next.setImageDrawable(getResources().getDrawable(R.drawable.save));
                    one.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Home.STEP += 1;
                }
            }
        };

        backStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Home.STEP<1) return;
                step1.setVisibility(View.GONE);
                step0.setVisibility(View.VISIBLE);
                ((CardView) prev.getParent()).setVisibility(View.GONE);
                next.setOnClickListener(nextStep);
                prev.setOnClickListener(null);
                next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                one.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
        next.setOnClickListener(nextStep);

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
