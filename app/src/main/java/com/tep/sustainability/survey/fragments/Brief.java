package com.tep.sustainability.survey.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tep.sustainability.survey.Home;
import com.tep.sustainability.survey.R;

import static com.tep.sustainability.survey.Home.PARTAA;
import static com.tep.sustainability.survey.Home.PARTAB;
import static com.tep.sustainability.survey.Home.PARTAC;
import static com.tep.sustainability.survey.Home.PARTBA;
import static com.tep.sustainability.survey.Home.PARTBB;

/**
 * A simple {@link Fragment} subclass.
 */
public class Brief extends Fragment {
    CardView one, two, three, four, five, locSurvey;
    ProgressBar progress;
    Context context;
    View v;


    public Brief() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_brief, container, false);
        getActivity().setTitle("Transport survey");
        context = v.getContext();

        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);
        three = v.findViewById(R.id.three);
        four= v.findViewById(R.id.four);
        five= v.findViewById(R.id.five);
        locSurvey = v.findViewById(R.id.loc_survey);
        progress = v.findViewById(R.id.progress);

        locSurvey.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Home.showPrompt(getString(R.string.location_description), context);
                return false;
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Home.backBtn = null;
        updateProgress(PARTAA+PARTAB+PARTAC+PARTBA+PARTBB);
        updateSectionStatus();
    }

    private void updateProgress(int n) {
        if (n > 0) progress.setProgress(5);
        switch(n) {
            case 5:
                progress.incrementProgressBy(25);
                five.setCardBackgroundColor(getResources().getColor(R.color.secondaryMain));
            case 4:
                progress.incrementProgressBy(25);
                four.setCardBackgroundColor(getResources().getColor(R.color.secondaryMain));
            case 3:
                progress.incrementProgressBy(25);
                three.setCardBackgroundColor(getResources().getColor(R.color.secondaryMain));
            case 2:
                progress.incrementProgressBy(25);
                two.setCardBackgroundColor(getResources().getColor(R.color.secondaryMain));
            case 1:
                one.setCardBackgroundColor(getResources().getColor(R.color.secondaryMain));
            default:
        }

        return;
    }


    private void updateSectionStatus() {
        if (PARTAA != 0) ((ImageView) v.findViewById(R.id.status_social)).setImageResource(R.drawable.check);
        else ((ImageView) v.findViewById(R.id.status_social)).setImageResource(R.drawable.cross);

        if (PARTAB != 0) ((ImageView) v.findViewById(R.id.status_vehicle)).setImageResource(R.drawable.check);
        else ((ImageView) v.findViewById(R.id.status_vehicle)).setImageResource(R.drawable.cross);

        if (PARTAC != 0) ((ImageView) v.findViewById(R.id.status_public)).setImageResource(R.drawable.check);
        else ((ImageView) v.findViewById(R.id.status_public)).setImageResource(R.drawable.cross);

        if (PARTBA != 0) ((ImageView) v.findViewById(R.id.status_preference)).setImageResource(R.drawable.check);
        else ((ImageView) v.findViewById(R.id.status_preference)).setImageResource(R.drawable.cross);

        if (PARTBB != 0) ((ImageView) v.findViewById(R.id.status_rated)).setImageResource(R.drawable.check);
        else ((ImageView) v.findViewById(R.id.status_rated)).setImageResource(R.drawable.cross);

    }
}
