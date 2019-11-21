package com.svnit.civil.survey.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.svnit.civil.survey.models.Preference;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Part_b_a extends Fragment {
    private Preference preference = new Preference();
    private Spinner modeChangeSelf, modeChangeSpouse, modeChangeSD, modeChangeOther;
    private CheckBox publicWaitTime, publicDistance, publicTravel, publicReliability, publicFare,
             privateTravel, privateCost, privateParking, vehicleSharing;
    private EditText publicWaitTimeValue, publicDistanceValue, publicTravelValue, publicReliabilityValue, publicFareValue,
             privateTravelValue, privateCostValue, privateParkingValue;
    private View.OnClickListener oneToTwo, saveStep, threeToTwo, twoToThree, twoToOne, threeToFour, fourToThree;
    private static RadioButton currentService, serviceA, serviceB;
    private CardView step0, step1, step2, step3, one, two, three, four;
    private ImageView next, prev;
    private Context context;
    private View v;


    public Part_b_a() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_part_b_a, container, false);
        context = v.getContext();
        getActivity().setTitle("Preference");

        publicWaitTime = v.findViewById(R.id.public_wait_time_checkbox);
        publicWaitTimeValue = v.findViewById(R.id.public_wait_time_edittext);
        publicDistance = v.findViewById(R.id.public_distance_checkbox);
        publicDistanceValue = v.findViewById(R.id.public_distance_edittext);
        publicTravel = v.findViewById(R.id.public_travel_time_checkbox);
        publicTravelValue = v.findViewById(R.id.public_travel_time_edittext);
        publicReliability = v.findViewById(R.id.public_reliability_checkbox);
        publicReliabilityValue = v.findViewById(R.id.public_reliability_edittext);
        publicFare = v.findViewById(R.id.public_fare_checkbox);
        publicFareValue = v.findViewById(R.id.public_fare_edittext);
        privateCost = v.findViewById(R.id.private_cost_checkbox);
        privateCostValue = v.findViewById(R.id.private_cost_edittext);
        privateTravel = v.findViewById(R.id.private_travel_time_checkbox);
        privateTravelValue = v.findViewById(R.id.private_travel_time_edittext);
        privateParking = v.findViewById(R.id.private_parking_checkbox);
        privateParkingValue = v.findViewById(R.id.private_parking_edittext);
        vehicleSharing = v.findViewById(R.id.private_sharing_checkbox);
        modeChangeSelf = v.findViewById(R.id.mode_change_self);
        modeChangeSpouse = v.findViewById(R.id.mode_change_spouse);
        modeChangeSD = v.findViewById(R.id.mode_change_son);
        modeChangeOther = v.findViewById(R.id.mode_change_other);
        currentService = v.findViewById(R.id.radio_current);
        serviceA = v.findViewById(R.id.radio_bus_a);
        serviceB = v.findViewById(R.id.radio_bus_b);

        step0 = v.findViewById(R.id.step0);
        step1 = v.findViewById(R.id.step1);
        step2 = v.findViewById(R.id.step2);
        step3 = v.findViewById(R.id.step3);
        next = v.findViewById(R.id.next);
        prev = v.findViewById(R.id.prev);
        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);
        three = v.findViewById(R.id.three);
        four = v.findViewById(R.id.four);


        publicDistance.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                publicDistanceValue.setEnabled(b);
            }
        });

        publicFare.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                publicFareValue.setEnabled(b);
            }
        });

        publicReliability.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                publicReliabilityValue.setEnabled(b);
            }
        });

        publicTravel.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                publicTravelValue.setEnabled(b);
            }
        });

        publicWaitTime.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                publicWaitTimeValue.setEnabled(b);
            }
        });

        privateCost.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privateCostValue.setEnabled(b);
            }
        });

        privateParking.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privateParkingValue.setEnabled(b);
            }
        });

        privateTravel.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                privateTravelValue.setEnabled(b);
            }
        });


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
                if (verifyStep2() && Home.STEP<Home.MAX) {
                    // proceed to save
                    step2.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(saveStep);
                    prev.setOnClickListener(fourToThree);
                    next.setImageDrawable(getResources().getDrawable(R.drawable.save));
                    three.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    four.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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
                next.setOnClickListener(twoToThree);
                prev.setOnClickListener(twoToOne);
                next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                three.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                four.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Home.STEP -= 1;
            }
        };

        saveStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save to firebase
                if (!verifyStep3()) return;
                updateFirebase();
            }
        };

        Home.STEP = 0; Home.MAX = 3;
        Home.backBtn = prev;
        next.setOnClickListener(oneToTwo);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Home.backBtn = null;
        currentService = null;
        serviceA = null;
        serviceB = null;
    }

    private void updateFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user/" + user.getUid());
        dbRef.child("preference_survey").setValue(preference).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public static void mark(View v) {
        currentService.setChecked(false);
        serviceA.setChecked(false);
        serviceB.setChecked(false);
        ( (RadioButton) ( (LinearLayout) v).getChildAt(17)).setChecked(true);
    }

    public static void markself(View v) {
        currentService.setChecked(false);
        serviceA.setChecked(false);
        serviceB.setChecked(false);
        ( (RadioButton) v).setChecked(true);
    }

    private boolean verifyStep0() {
        if (modeChangeSelf.getSelectedItemPosition() == 0) { ( (TextView) modeChangeSelf.getSelectedView()).setError("Required"); return false; }
        if (!Home.preferences.getString("spouseAge", "").equals("") && modeChangeSpouse.getSelectedItemPosition() == 0) {
            ( (TextView) modeChangeSpouse.getSelectedView()).setError("Required"); return false; }
        if (Home.preferences.getInt("children", 0) > 0 && modeChangeSD.getSelectedItemPosition() == 0) {
            ( (TextView) modeChangeSD.getSelectedView()).setError("Required"); return false; }

        preference.setChange_when_first_mode_not_available(modeChangeSelf.getSelectedItem().toString());
        preference.setChange_when_first_mode_not_available_spouse(modeChangeSpouse.getSelectedItem().toString());
        preference.setChange_when_first_mode_not_available_sd(modeChangeSD.getSelectedItem().toString());
        preference.setChange_when_first_mode_not_available_other(modeChangeOther.getSelectedItem().toString());
        return true;
    }

    private boolean verifyStep1() {
        if (publicWaitTime.isChecked() && publicWaitTimeValue.getText().toString().equals("")) { publicWaitTimeValue.setError("Required"); return false;}
        if (publicDistance.isChecked() && publicDistanceValue.getText().toString().equals("")) { publicDistanceValue.setError("Required"); return false;}
        if (publicTravel.isChecked() && publicTravelValue.getText().toString().equals("")) { publicTravelValue.setError("Required"); return false;}
        if (publicReliability.isChecked() && publicReliabilityValue.getText().toString().equals("")) { publicReliabilityValue.setError("Required"); return false;}
        if (publicFare.isChecked() && publicFareValue.getText().toString().equals("")) { publicFareValue.setError("Required"); return false;}

        Map<String, String> temp = new HashMap<String, String>();

        if (publicDistance.isChecked()) {
            temp.put("distance to stoppage", publicDistanceValue.getText().toString());
        } else temp.put("distance to stoppage", "No");

        if (publicWaitTime.isChecked()) {
            temp.put("wait time at stoppage", publicWaitTimeValue.getText().toString());
        } else temp.put("wait time at stoppage", "No");

        if (publicTravel.isChecked()) {
            temp.put("acceptable travel time", publicTravelValue.getText().toString());
        } else temp.put("acceptable travel time", "No");

        if (publicReliability.isChecked()) {
            temp.put("schedule reliability", publicReliabilityValue.getText().toString());
        } else temp.put("schedule reliability", "No");

        if (publicFare.isChecked()) {
            temp.put("affordable fare", publicFareValue.getText().toString());
        } else temp.put("affordable fare", "No");

        preference.setAttributes_public_transport(temp);

        return true;
    }

    private boolean verifyStep2() {
        if (privateTravel.isChecked() && privateTravelValue.getText().toString().equals("")) { privateTravelValue.setError("Required"); return false;}
        if (privateCost.isChecked() && privateCostValue.getText().toString().equals("")) { privateCostValue.setError("Required"); return false;}
        if (privateParking.isChecked() && privateParkingValue.getText().toString().equals("")) { privateParkingValue.setError("Required"); return false;}

        preference.setChange_when_first_mode_not_available(modeChangeSelf.getSelectedItem().toString());
        Map<String, String> temp = new HashMap<String, String>();

        if (privateTravel.isChecked()) {
            temp.put("acceptable travel time", privateTravelValue.getText().toString());
        } else temp.put("acceptable travel time", "No");

        if (privateCost.isChecked()) {
            temp.put("affordable travel cost", privateCostValue.getText().toString());
        } else temp.put("affordable travel cost", "No");

        if (privateParking.isChecked()) {
            temp.put("acceptable parking charges", privateParkingValue.getText().toString());
        } else temp.put("acceptable parking charges", "No");

        if (vehicleSharing.isChecked()) {
            temp.put("vehicle sharing", "Yes");
        } else temp.put("vehicle sharing", "No");

        preference.setAttributes_private_vehicle(temp);

        return true;
    }

    private boolean verifyStep3() {
        if (!currentService.isChecked() && !serviceB.isChecked() && !serviceA.isChecked()) { Snackbar.make(step2, "Please indicate your choice", Snackbar.LENGTH_LONG).show(); return false; }
        if (currentService.isChecked()) preference.setSituation_switch_to_public_transport("Current Service");
        else if (serviceA.isChecked()) preference.setSituation_switch_to_public_transport("Service A");
        else if (serviceB.isChecked()) preference.setSituation_switch_to_public_transport("Service B");
        return true;
    }

    private void updatePref() {
        Home.PARTBA = 1;
        Home.sharedPref.edit().putInt("partba", 1).apply();
    }

}
