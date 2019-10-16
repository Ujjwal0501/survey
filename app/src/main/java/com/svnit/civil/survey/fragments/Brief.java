package com.svnit.civil.survey.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Brief extends Fragment {


    public Brief() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_brief, container, false);
        getActivity().setTitle("Transport survey");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Home.backBtn = null;
    }
}
