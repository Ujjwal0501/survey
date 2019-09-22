package com.svnit.civil.survey.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.svnit.civil.survey.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Part_a_a extends Fragment {
    EditText self_age, spouse_age, m_emi, mfuel_cost, m_maint, mpark_cost, reg;
    Spinner self_ocu, spouse_ocu, daughter, son, m_income, res_type, car_count, twow_count, bicycle_count;
    ImageView imageView;
    Context context;
    View v;


    public Part_a_a() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_part_a_a, container, false);
        context = v.getContext();
        self_age = v.findViewById(R.id.self_age);
        spouse_age = v.findViewById(R.id.spouse_age);
        m_emi = v.findViewById(R.id.m_emi);
        mfuel_cost = v.findViewById(R.id.mfuel_cost);
        m_maint = v.findViewById(R.id.m_maint);
        mpark_cost = v.findViewById(R.id.mpark_cost);
        reg = v.findViewById(R.id.reg_charge);
        self_ocu = v.findViewById(R.id.self_ocu);
        spouse_ocu = v.findViewById(R.id.spouse_ocu);
        daughter = v.findViewById(R.id.daughters);
        son = v.findViewById(R.id.sons);
        m_income = v.findViewById(R.id.m_income);
        res_type = v.findViewById(R.id.res_type);
        car_count = v.findViewById(R.id.car_count);
        twow_count = v.findViewById(R.id.twow_count);
        bicycle_count = v.findViewById(R.id.bicylce_count);
        imageView = v.findViewById(R.id.save);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyForm()) {
                    // proceed to save

                }
            }
        });

        return v;

    }

    private boolean verifyForm() {

        return true;
    }

}
