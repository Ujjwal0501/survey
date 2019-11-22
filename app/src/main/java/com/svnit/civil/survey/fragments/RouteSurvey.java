package com.svnit.civil.survey.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;
import com.svnit.civil.survey.helpers.TimeButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteSurvey extends Fragment {
    private View.OnClickListener oneToTwo, saveStep, threeToTwo, twoToThree, twoToOne, threeToFour, fourToThree, fourToFive, fiveToSix, sixToFive, fiveToFour;
    View v;
    Context context;
    TimeButton   selfStartTime, selfEndTime, spouseStartTime, spouseEndTime, childrenStartTime, childrenEndTime
               , recreationStartTime, recreationEndTime, socialStartTime, socialEndTime, shoppingStartTime, shoppingEndTime;
    Button selfStartLocation, selfEndLocation, spouseStartLocation, spouseEndLocation,
            childrenStartLocation, childrenEndLocation,recreationStartLocation, recreationEndLocation,socialStartLocation,
            socialEndLocation, shoppingStartLocation, shoppingEndLocation;
    private CardView step0, step1, step2, step3, step4, step5, one, two, three, four, five, six;
    private ImageView next, prev;

    public RouteSurvey() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_route_survey, container, false);
        context = v.getContext();
        getActivity().setTitle("Preference");


        step0 = v.findViewById(R.id.step0);
        step1 = v.findViewById(R.id.step1);
        step2 = v.findViewById(R.id.step2);
        step3 = v.findViewById(R.id.step3);
        step4 = v.findViewById(R.id.step4);
        step5 = v.findViewById(R.id.step5);
        next = v.findViewById(R.id.next);
        prev = v.findViewById(R.id.prev);
        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);
        three = v.findViewById(R.id.three);
        four = v.findViewById(R.id.four);
        five = v.findViewById(R.id.five);
        six = v.findViewById(R.id.six);


        selfStartLocation = v.findViewById(R.id.start_self);
        selfEndLocation = v.findViewById(R.id.end_self);
        selfStartTime = v.findViewById(R.id.start_time_self);
        selfEndTime = v.findViewById(R.id.end_time_self);

        spouseStartLocation = v.findViewById(R.id.start_spouse);
        spouseEndLocation = v.findViewById(R.id.end_spouse);
        spouseStartTime = v.findViewById(R.id.start_time_spouse);
        spouseEndTime = v.findViewById(R.id.end_time_spouse);

        childrenStartLocation = v.findViewById(R.id.start_sd);
        childrenEndLocation = v.findViewById(R.id.end_sd);
        childrenStartTime = v.findViewById(R.id.start_time_sd);
        childrenEndTime = v.findViewById(R.id.end_time_sd);

        recreationStartLocation = v.findViewById(R.id.start_recreation);
        recreationEndLocation = v.findViewById(R.id.end_recreation);
        recreationStartTime = v.findViewById(R.id.start_time_recreation);
        recreationEndTime = v.findViewById(R.id.end_time_recreation);

        socialStartLocation = v.findViewById(R.id.start_social);
        socialEndLocation = v.findViewById(R.id.end_social);
        socialStartTime = v.findViewById(R.id.start_time_social);
        socialEndTime = v.findViewById(R.id.end_time_social);

        shoppingStartLocation = v.findViewById(R.id.start_shopping);
        shoppingEndLocation = v.findViewById(R.id.end_shopping);
        shoppingStartTime = v.findViewById(R.id.start_time_shopping);
        shoppingEndTime = v.findViewById(R.id.end_time_shopping);

        oneToTwo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (/*verifyStep0() && */Home.STEP<Home.MAX) {
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
                if (/*verifyStep1() && */Home.STEP<Home.MAX) {
                    // proceed to save
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(threeToFour);
                    prev.setOnClickListener(threeToTwo);
                    two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    three.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Home.STEP += 1;
                }
            }
        };

        threeToFour = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (/*verifyStep2() && */Home.STEP<Home.MAX) {
                    // proceed to save
                    step2.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(fourToFive);
                    prev.setOnClickListener(fourToThree);
                    three.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    four.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Home.STEP += 1;
                }
            }
        };

        fourToFive = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (/*verifyStep3() && */Home.STEP<Home.MAX) {
                    // proceed to save
                    step3.setVisibility(View.GONE);
                    step4.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(fiveToSix);
                    prev.setOnClickListener(fiveToFour);
                    four.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    five.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Home.STEP += 1;
                }
            }
        };

        fiveToSix = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (/*verifyStep4() && */Home.STEP<Home.MAX) {
                    // proceed to save
                    step4.setVisibility(View.GONE);
                    step5.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(saveStep);
                    prev.setOnClickListener(sixToFive);
                    next.setImageDrawable(getResources().getDrawable(R.drawable.save));
                    five.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    six.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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

        fourToThree = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Home.STEP<1) return;
                step3.setVisibility(View.GONE);
                step2.setVisibility(View.VISIBLE);
                next.setOnClickListener(threeToFour);
                prev.setOnClickListener(threeToTwo);
                next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                three.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                four.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Home.STEP -= 1;
            }
        };

        fiveToFour = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Home.STEP<1) return;
                step4.setVisibility(View.GONE);
                step3.setVisibility(View.VISIBLE);
                next.setOnClickListener(fourToFive);
                prev.setOnClickListener(fourToThree);
                next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                four.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                five.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Home.STEP -= 1;
            }
        };

        sixToFive = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Home.STEP<1) return;
                step5.setVisibility(View.GONE);
                step4.setVisibility(View.VISIBLE);
                next.setOnClickListener(saveStep);
                prev.setOnClickListener(fiveToFour);
                next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                five.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                six.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Home.STEP -= 1;
            }
        };

        saveStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save to firebase
//                if (!/*verifyStep5()*/) return;
//                updateFirebase();
            }
        };

        Home.STEP = 0; Home.MAX = 5;
        Home.backBtn = prev;
        next.setOnClickListener(oneToTwo);


        return v;
    }

}
