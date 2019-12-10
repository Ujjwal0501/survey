package com.tep.sustainability.survey.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tep.sustainability.survey.Home;
import com.tep.sustainability.survey.R;
import com.tep.sustainability.survey.helpers.PlaceButton;
import com.tep.sustainability.survey.helpers.TimeButton;
import com.tep.sustainability.survey.models.TravelChar;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteSurvey extends Fragment {
    private View.OnClickListener oneToTwo, saveStep, threeToTwo, twoToThree, twoToOne, threeToFour, fourToThree, fourToFive, fiveToSix, sixToFive, fiveToFour;
    private View v;
    private Context context;
    private TimeButton   selfStartTime, selfEndTime, spouseStartTime, spouseEndTime, childrenStartTime, childrenEndTime
               , recreationStartTime, recreationEndTime, socialStartTime, socialEndTime, shoppingStartTime, shoppingEndTime;
    private PlaceButton selfStartLocation, selfEndLocation, spouseStartLocation, spouseEndLocation,
            childrenStartLocation, childrenEndLocation,recreationStartLocation, recreationEndLocation,socialStartLocation,
            socialEndLocation, shoppingStartLocation, shoppingEndLocation;
    private EditText selfWait, selfVehicle, selfDistance, selfParking, spouseWait, spouseVehicle, spouseDistance, spouseParking,
            childrenWait, childrenVehicle, childrenDistance, childrenParking, recreationWait, recreationVehicle,
            recreationDistance, recreationParking, socialWait, socialVehicle, socialDistance, socialParking,
            shoppingWait,shoppingVehicle, shoppingDistance, shoppingParking;
    private Spinner selfPurpose, selfMode, selfFrequency, selfTravel, spousePurpose, spouseMode, spouseFrequency, spouseTravel,
            childrenPurpose, childrenMode, childrenFrequency, childrenTravel, recreationPurpose, recreationMode,
            recreationFrequency, recreationTravel, socialPurpose, socialMode, socialFrequency, socialTravel,
            shoppingPurpose,shoppingMode, shoppingFrequency, shoppingTravel;
    private CardView step0, step1, step2, step3, step4, step5, one, two, three, four, five, six;
    private ImageView next, prev;
    public static double[] arr = {0f, 0f, 0f, 0f};
    public static int START_END = 1;
    public static LatLng latLng = new LatLng(26.6 , 77.2);
    private TravelChar self = new TravelChar(), spouse = new TravelChar(), children = new TravelChar(),
            recreation = new TravelChar(), social = new TravelChar(), shopping = new TravelChar();

    public RouteSurvey() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_route_survey, container, false);
        context = v.getContext();
        getActivity().setTitle("Travel");
        if (getActivity().getActionBar() != null) getActivity().getActionBar().setSubtitle("Characteristics");


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
        selfPurpose = v.findViewById(R.id.purpose_self);
        selfMode = v.findViewById(R.id.mode_self);
        selfFrequency = v.findViewById(R.id.frequency_self);
        selfVehicle = v.findViewById(R.id.travel_time_self);
        selfWait = v.findViewById(R.id.wait_time_self);
        selfDistance = v.findViewById(R.id.distance_self);
        selfParking = v.findViewById(R.id.cost_self);
        selfTravel = v.findViewById(R.id.vehicle_time_self);

        spouseStartLocation = v.findViewById(R.id.start_spouse);
        spouseEndLocation = v.findViewById(R.id.end_spouse);
        spouseStartTime = v.findViewById(R.id.start_time_spouse);
        spouseEndTime = v.findViewById(R.id.end_time_spouse);
        spousePurpose = v.findViewById(R.id.purpose_spouse);
        spouseMode = v.findViewById(R.id.mode_spouse);
        spouseFrequency = v.findViewById(R.id.frequency_spouse);
        spouseVehicle = v.findViewById(R.id.travel_time_spouse);
        spouseWait = v.findViewById(R.id.wait_time_spouse);
        spouseDistance = v.findViewById(R.id.distance_spouse);
        spouseParking = v.findViewById(R.id.cost_spouse);
        spouseTravel = v.findViewById(R.id.vehicle_time_spouse);

        childrenStartLocation = v.findViewById(R.id.start_sd);
        childrenEndLocation = v.findViewById(R.id.end_sd);
        childrenStartTime = v.findViewById(R.id.start_time_sd);
        childrenEndTime = v.findViewById(R.id.end_time_sd);
        childrenPurpose = v.findViewById(R.id.purpose_sd);
        childrenMode = v.findViewById(R.id.mode_sd);
        childrenFrequency = v.findViewById(R.id.frequency_sd);
        childrenVehicle = v.findViewById(R.id.travel_time_sd);
        childrenWait = v.findViewById(R.id.wait_time_sd);
        childrenDistance = v.findViewById(R.id.distance_sd);
        childrenParking = v.findViewById(R.id.cost_sd);
        childrenTravel = v.findViewById(R.id.vehicle_time_sd);

        recreationStartLocation = v.findViewById(R.id.start_recreation);
        recreationEndLocation = v.findViewById(R.id.end_recreation);
        recreationStartTime = v.findViewById(R.id.start_time_recreation);
        recreationEndTime = v.findViewById(R.id.end_time_recreation);
        recreationPurpose = v.findViewById(R.id.member_recreation);
        recreationMode = v.findViewById(R.id.mode_recreation);
        recreationFrequency = v.findViewById(R.id.frequency_recreation);
        recreationVehicle = v.findViewById(R.id.travel_time_recreation);
        recreationWait = v.findViewById(R.id.wait_time_recreation);
        recreationDistance = v.findViewById(R.id.distance_recreation);
        recreationParking = v.findViewById(R.id.cost_recreation);
        recreationTravel = v.findViewById(R.id.vehicle_time_recreation);

        socialStartLocation = v.findViewById(R.id.start_social);
        socialEndLocation = v.findViewById(R.id.end_social);
        socialStartTime = v.findViewById(R.id.start_time_social);
        socialEndTime = v.findViewById(R.id.end_time_social);
        socialPurpose = v.findViewById(R.id.member_social);
        socialMode = v.findViewById(R.id.mode_social);
        socialFrequency = v.findViewById(R.id.frequency_social);
        socialVehicle = v.findViewById(R.id.travel_time_social);
        socialWait = v.findViewById(R.id.wait_time_social);
        socialDistance = v.findViewById(R.id.distance_social);
        socialParking = v.findViewById(R.id.cost_social);
        socialTravel = v.findViewById(R.id.vehicle_time_social);

        shoppingStartLocation = v.findViewById(R.id.start_shopping);
        shoppingEndLocation = v.findViewById(R.id.end_shopping);
        shoppingStartTime = v.findViewById(R.id.start_time_shopping);
        shoppingEndTime = v.findViewById(R.id.end_time_shopping);
        shoppingPurpose = v.findViewById(R.id.member_shopping);
        shoppingMode = v.findViewById(R.id.mode_shopping);
        shoppingFrequency = v.findViewById(R.id.frequency_shopping);
        shoppingVehicle = v.findViewById(R.id.travel_time_shopping);
        shoppingWait = v.findViewById(R.id.wait_time_shopping);
        shoppingDistance = v.findViewById(R.id.distance_shopping);
        shoppingParking = v.findViewById(R.id.cost_shopping);
        shoppingTravel = v.findViewById(R.id.vehicle_time_shopping);

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
                    one.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                    two.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
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
                    next.setOnClickListener(threeToFour);
                    prev.setOnClickListener(threeToTwo);
                    two.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                    three.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                    Home.STEP += 1;
                }
            }
        };

        threeToFour = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyStep2() && Home.STEP<Home.MAX) {
                    // proceed to save
                    step2.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(fourToFive);
                    prev.setOnClickListener(fourToThree);
                    three.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                    four.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                    Home.STEP += 1;
                }
            }
        };

        fourToFive = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyStep3() && Home.STEP<Home.MAX) {
                    // proceed to save
                    step3.setVisibility(View.GONE);
                    step4.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(fiveToSix);
                    prev.setOnClickListener(fiveToFour);
                    four.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                    five.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                    Home.STEP += 1;
                }
            }
        };

        fiveToSix = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyStep4() && Home.STEP<Home.MAX) {
                    // proceed to save
                    step4.setVisibility(View.GONE);
                    step5.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(saveStep);
                    prev.setOnClickListener(sixToFive);
                    next.setImageDrawable(getResources().getDrawable(R.drawable.save));
                    five.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                    six.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
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
                one.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                two.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
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
                two.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                three.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
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
                three.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                four.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
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
                four.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                five.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
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
                five.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                six.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                Home.STEP -= 1;
            }
        };

        saveStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save to firebase
                if (!verifyStep5()) return;
                updateFirebase();
            }
        };

        Home.STEP = 0; Home.MAX = 5;
        Home.backBtn = prev;
        next.setOnClickListener(oneToTwo);


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Home.backBtn = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PlaceButton.who == null) return;
        ((PlaceButton) PlaceButton.who).setText(latLng.toString());

        if (START_END == 1) {
            setStart(null);
        } else {
            setEnd(null);
        }
    }

    private boolean verifyStep0() {
        if (selfStartLocation.getText().toString().equalsIgnoreCase("START\nLOCATION")) { selfStartLocation.setError(""); return false;}
        if (selfEndLocation.getText().toString().equalsIgnoreCase("END\nLOCATION")) { selfEndLocation.setError(""); return false;}
        if (selfStartTime.getText().toString().equalsIgnoreCase("START\nTIME")) { selfStartTime.setError(""); return false;}
        if (selfEndTime.getText().toString().equalsIgnoreCase("END\nTIME")) { selfEndTime.setError(""); return false;}

        if (selfPurpose.getSelectedItemPosition() == 0) { ((TextView) selfPurpose.getSelectedView()).setError(""); return false; }
        if (selfMode.getSelectedItemPosition() == 0) { ((TextView) selfMode.getSelectedView()).setError(""); return false; }
        if (selfVehicle.getText().toString().equals("")) { selfVehicle.setError(""); return false; }
        if (selfDistance.getText().toString().equals("")) { selfDistance.setError(""); return false; }
        if (selfParking.getText().toString().equals("")) { selfParking.setError(""); return false; }
        if (selfFrequency.getSelectedItemPosition() == 0) { ((TextView) selfFrequency.getSelectedView()).setError(""); return false; }

        self.setStartLocation(new LatLng(arr[0], arr[1]));
        self.setEndLocation(new LatLng(arr[0], arr[1]));
        self.setPurpose(selfPurpose.getSelectedItem().toString());
        self.setStartTime(selfStartTime.getText().toString());
        self.setEndTime(selfEndTime.getText().toString());
        self.setMode(selfMode.getSelectedItem().toString());
        self.setStartLoc(selfStartLocation.getText().toString());
        self.setEndLoc(selfEndLocation.getText().toString());
        self.setFrequency(selfFrequency.getSelectedItem().toString());
        self.setParking(Float.parseFloat(selfParking.getText().toString()));
        self.setVehicleTime(Float.parseFloat(selfVehicle.getText().toString()));
        if (selfWait.getText().toString().equals(""))
            self.setWaitTime(0f);
        else
            self.setWaitTime(Float.parseFloat(selfWait.getText().toString()));
        self.setDistance(Float.parseFloat(selfDistance.getText().toString()));

        return true;
    }

    private boolean verifyStep1() {
        if (spouseStartLocation.getText().toString().equalsIgnoreCase("START\nLOCATION")) { spouseStartLocation.setError(""); return false;}
        if (spouseEndLocation.getText().toString().equalsIgnoreCase("END\nLOCATION")) { spouseEndLocation.setError(""); return false;}
        if (spouseStartTime.getText().toString().equalsIgnoreCase("START\nTIME")) { spouseStartTime.setError(""); return false;}
        if (spouseEndTime.getText().toString().equalsIgnoreCase("END\nTIME")) { spouseEndTime.setError(""); return false;}

        if (spousePurpose.getSelectedItemPosition() == 0) { ((TextView) spousePurpose.getSelectedView()).setError(""); return false; }
        if (spouseMode.getSelectedItemPosition() == 0) { ((TextView) spouseMode.getSelectedView()).setError(""); return false; }
        if (spouseVehicle.getText().toString().equals("")) { spouseVehicle.setError(""); return false; }
        if (spouseDistance.getText().toString().equals("")) { spouseDistance.setError(""); return false; }
        if (spouseParking.getText().toString().equals("")) { spouseParking.setError(""); return false; }
        if (spouseFrequency.getSelectedItemPosition() == 0) { ((TextView) spouseFrequency.getSelectedView()).setError(""); return false; }

        spouse.setStartLocation(new LatLng(arr[0], arr[1]));
        spouse.setEndLocation(new LatLng(arr[0], arr[1]));
        spouse.setPurpose(spousePurpose.getSelectedItem().toString());
        spouse.setStartTime(spouseStartTime.getText().toString());
        spouse.setEndTime(spouseEndTime.getText().toString());
        spouse.setMode(spouseMode.getSelectedItem().toString());
        spouse.setStartLoc(spouseStartLocation.getText().toString());
        spouse.setEndLoc(spouseEndLocation.getText().toString());
        spouse.setFrequency(spouseFrequency.getSelectedItem().toString());
        spouse.setParking(Float.parseFloat(spouseParking.getText().toString()));
        spouse.setVehicleTime(Float.parseFloat(spouseVehicle.getText().toString()));
        if (spouseWait.getText().toString().equals(""))
            spouse.setWaitTime(0f);
        else
            spouse.setWaitTime(Float.parseFloat(spouseWait.getText().toString()));
        spouse.setDistance(Float.parseFloat(spouseDistance.getText().toString()));

        return true;
    }

    private boolean verifyStep2() {
        if (childrenStartLocation.getText().toString().equalsIgnoreCase("START\nLOCATION")) { childrenStartLocation.setError(""); return false;}
        if (childrenEndLocation.getText().toString().equalsIgnoreCase("END\nLOCATION")) { childrenEndLocation.setError(""); return false;}
        if (childrenStartTime.getText().toString().equalsIgnoreCase("START\nTIME")) { childrenStartTime.setError(""); return false;}
        if (childrenEndTime.getText().toString().equalsIgnoreCase("END\nTIME")) { childrenEndTime.setError(""); return false;}

        if (childrenPurpose.getSelectedItemPosition() == 0) { ((TextView) childrenPurpose.getSelectedView()).setError(""); return false; }
        if (childrenMode.getSelectedItemPosition() == 0) { ((TextView) childrenMode.getSelectedView()).setError(""); return false; }
        if (childrenVehicle.getText().toString().equals("")) { childrenVehicle.setError(""); return false; }
        if (childrenDistance.getText().toString().equals("")) { childrenDistance.setError(""); return false; }
        if (childrenParking.getText().toString().equals("")) { childrenParking.setError(""); return false; }
        if (childrenFrequency.getSelectedItemPosition() == 0) { ((TextView) childrenFrequency.getSelectedView()).setError(""); return false; }

        children.setStartLocation(new LatLng(arr[0], arr[1]));
        children.setEndLocation(new LatLng(arr[0], arr[1]));
        children.setPurpose(childrenPurpose.getSelectedItem().toString());
        children.setStartTime(childrenStartTime.getText().toString());
        children.setEndTime(childrenEndTime.getText().toString());
        children.setMode(childrenMode.getSelectedItem().toString());
        children.setStartLoc(childrenStartLocation.getText().toString());
        children.setEndLoc(childrenEndLocation.getText().toString());
        children.setFrequency(childrenFrequency.getSelectedItem().toString());
        children.setParking(Float.parseFloat(childrenParking.getText().toString()));
        children.setVehicleTime(Float.parseFloat(childrenVehicle.getText().toString()));
        if (childrenWait.getText().toString().equals(""))
            children.setWaitTime(0f);
        else
            children.setWaitTime(Float.parseFloat(childrenWait.getText().toString()));
        children.setDistance(Float.parseFloat(childrenDistance.getText().toString()));

        return true;
    }

    private boolean verifyStep3() {
        if (recreationStartLocation.getText().toString().equalsIgnoreCase("START\nLOCATION")) { recreationStartLocation.setError(""); return false;}
        if (recreationEndLocation.getText().toString().equalsIgnoreCase("END\nLOCATION")) { recreationEndLocation.setError(""); return false;}
        if (recreationStartTime.getText().toString().equalsIgnoreCase("START\nTIME")) { recreationStartTime.setError(""); return false;}
        if (recreationEndTime.getText().toString().equalsIgnoreCase("END\nTIME")) { recreationEndTime.setError(""); return false;}

        if (recreationPurpose.getSelectedItemPosition() == 0) { ((TextView) recreationPurpose.getSelectedView()).setError(""); return false; }
        if (recreationMode.getSelectedItemPosition() == 0) { ((TextView) recreationMode.getSelectedView()).setError(""); return false; }
        if (recreationVehicle.getText().toString().equals("")) { recreationVehicle.setError(""); return false; }
        if (recreationDistance.getText().toString().equals("")) { recreationDistance.setError(""); return false; }
        if (recreationParking.getText().toString().equals("")) { recreationParking.setError(""); return false; }
        if (recreationFrequency.getSelectedItemPosition() == 0) { ((TextView) recreationFrequency.getSelectedView()).setError(""); return false; }

        recreation.setStartLocation(new LatLng(arr[0], arr[1]));
        recreation.setEndLocation(new LatLng(arr[0], arr[1]));
        recreation.setPurpose(recreationPurpose.getSelectedItem().toString());
        recreation.setStartTime(recreationStartTime.getText().toString());
        recreation.setEndTime(recreationEndTime.getText().toString());
        recreation.setMode(recreationMode.getSelectedItem().toString());
        recreation.setStartLoc(recreationStartLocation.getText().toString());
        recreation.setEndLoc(recreationEndLocation.getText().toString());
        recreation.setFrequency(recreationFrequency.getSelectedItem().toString());
        recreation.setParking(Float.parseFloat(recreationParking.getText().toString()));
        recreation.setVehicleTime(Float.parseFloat(recreationVehicle.getText().toString()));
        if (recreationWait.getText().toString().equals(""))
            recreation.setWaitTime(0f);
        else
            recreation.setWaitTime(Float.parseFloat(recreationWait.getText().toString()));
        recreation.setDistance(Float.parseFloat(recreationDistance.getText().toString()));

        return true;
    }

    private boolean verifyStep4() {
        if (socialStartLocation.getText().toString().equalsIgnoreCase("START\nLOCATION")) { socialStartLocation.setError(""); return false;}
        if (socialEndLocation.getText().toString().equalsIgnoreCase("END\nLOCATION")) { socialEndLocation.setError(""); return false;}
        if (socialStartTime.getText().toString().equalsIgnoreCase("START\nTIME")) { socialStartTime.setError(""); return false;}
        if (socialEndTime.getText().toString().equalsIgnoreCase("END\nTIME")) { socialEndTime.setError(""); return false;}

        if (socialPurpose.getSelectedItemPosition() == 0) { ((TextView) socialPurpose.getSelectedView()).setError(""); return false; }
        if (socialMode.getSelectedItemPosition() == 0) { ((TextView) socialMode.getSelectedView()).setError(""); return false; }
        if (socialVehicle.getText().toString().equals("")) { socialVehicle.setError(""); return false; }
        if (socialDistance.getText().toString().equals("")) { socialDistance.setError(""); return false; }
        if (socialParking.getText().toString().equals("")) { socialParking.setError(""); return false; }
        if (socialFrequency.getSelectedItemPosition() == 0) { ((TextView) socialFrequency.getSelectedView()).setError(""); return false; }

        social.setStartLocation(new LatLng(arr[0], arr[1]));
        social.setEndLocation(new LatLng(arr[0], arr[1]));
        social.setPurpose(socialPurpose.getSelectedItem().toString());
        social.setStartTime(socialStartTime.getText().toString());
        social.setEndTime(socialEndTime.getText().toString());
        social.setMode(socialMode.getSelectedItem().toString());
        social.setStartLoc(socialStartLocation.getText().toString());
        social.setEndLoc(socialEndLocation.getText().toString());
        social.setFrequency(socialFrequency.getSelectedItem().toString());
        social.setParking(Float.parseFloat(socialParking.getText().toString()));
        social.setVehicleTime(Float.parseFloat(socialVehicle.getText().toString()));
        if (socialWait.getText().toString().equals(""))
            social.setWaitTime(0f);
        else
            social.setWaitTime(Float.parseFloat(socialWait.getText().toString()));
        social.setDistance(Float.parseFloat(socialDistance.getText().toString()));

        return true;
    }

    private boolean verifyStep5() {
        if (shoppingStartLocation.getText().toString().equalsIgnoreCase("START\nLOCATION")) { shoppingStartLocation.setError(""); return false;}
        if (shoppingEndLocation.getText().toString().equalsIgnoreCase("END\nLOCATION")) { shoppingEndLocation.setError(""); return false;}
        if (shoppingStartTime.getText().toString().equalsIgnoreCase("START\nTIME")) { shoppingStartTime.setError(""); return false;}
        if (shoppingEndTime.getText().toString().equalsIgnoreCase("END\nTIME")) { shoppingEndTime.setError(""); return false;}

        if (shoppingPurpose.getSelectedItemPosition() == 0) { ((TextView) shoppingPurpose.getSelectedView()).setError(""); return false; }
        if (shoppingMode.getSelectedItemPosition() == 0) { ((TextView) shoppingMode.getSelectedView()).setError(""); return false; }
        if (shoppingVehicle.getText().toString().equals("")) { shoppingVehicle.setError(""); return false; }
        if (shoppingDistance.getText().toString().equals("")) { shoppingDistance.setError(""); return false; }
        if (shoppingParking.getText().toString().equals("")) { shoppingParking.setError(""); return false; }
        if (shoppingFrequency.getSelectedItemPosition() == 0) { ((TextView) shoppingFrequency.getSelectedView()).setError(""); return false; }

        shopping.setStartLocation(new LatLng(arr[0], arr[1]));
        shopping.setEndLocation(new LatLng(arr[0], arr[1]));
        shopping.setPurpose(shoppingPurpose.getSelectedItem().toString());
        shopping.setStartTime(shoppingStartTime.getText().toString());
        shopping.setEndTime(shoppingEndTime.getText().toString());
        shopping.setMode(shoppingMode.getSelectedItem().toString());
        shopping.setStartLoc(shoppingStartLocation.getText().toString());
        shopping.setEndLoc(shoppingEndLocation.getText().toString());
        shopping.setFrequency(shoppingFrequency.getSelectedItem().toString());
        shopping.setParking(Float.parseFloat(shoppingParking.getText().toString()));
        shopping.setVehicleTime(Float.parseFloat(shoppingVehicle.getText().toString()));
        if (shoppingWait.getText().toString().equals(""))
            shopping.setWaitTime(0f);
        else
            shopping.setWaitTime(Float.parseFloat(shoppingWait.getText().toString()));
        shopping.setDistance(Float.parseFloat(shoppingDistance.getText().toString()));

        return true;
    }

    private void updateFirebase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user/" + Home.user.getUid() + "/travel_characteristic");

        Map<String, TravelChar> data = new HashMap<>();
        data.put("self", self);
        data.put("spouse", spouse);
        data.put("children", children);
        data.put("recreation", recreation);
        data.put("social", social);
        data.put("shopping", shopping);

        dbRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        });;

    }

    public void setStart(View v) {
        arr[0] = latLng.latitude;
        arr[1] = latLng.longitude;
        START_END = 1;
    }

    public void setEnd(View v) {
        arr[2] = latLng.latitude;
        arr[3] = latLng.longitude;
        START_END = 2;
    }

    private void updatePref() {
        Home.sharedPref.edit().putInt("route", 1).apply();
    }
}
