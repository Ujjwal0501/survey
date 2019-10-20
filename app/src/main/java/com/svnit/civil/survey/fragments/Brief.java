package com.svnit.civil.survey.fragments;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Brief extends Fragment {
    CardView one, two, three, four, five;
    ProgressBar progress;


    public Brief() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_brief, container, false);
        getActivity().setTitle("Transport survey");

        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);
        three = v.findViewById(R.id.three);
        four= v.findViewById(R.id.four);
        five= v.findViewById(R.id.five);
        progress = v.findViewById(R.id.progress);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Home.backBtn = null;
    }
}
