package com.tep.sustainability.survey.fragments;


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
import com.tep.sustainability.survey.Home;
import com.tep.sustainability.survey.R;
import com.tep.sustainability.survey.models.Economic;

/**
 * A simple {@link Fragment} subclass.
 */
public class Part_a_b extends Fragment {

    private Economic economic = new Economic();
    private EditText  monthlyEMI, monthlyFuelCost, monthlyMaintenance, monthlyParkingCost, registration;
    private Spinner monthlyIncome, residenceType, carCount, twoWheelerCount, bicycleCount,
            carMileage, carAge, carFuel, twoWheelerAge, twoWheelerFuel, twoWheelerMileage, bicycleAge;
    private View.OnClickListener nextStep, saveStep, backStep;
    private CardView step0, step1, one, two;
    private ImageView next, prev;
    private Context context;
    private View v;


    public Part_a_b() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_part_a_b, container, false);
        context = v.getContext();
        getActivity().setTitle("Economic Details");

        monthlyEMI = v.findViewById(R.id.m_emi);
        monthlyFuelCost = v.findViewById(R.id.mfuel_cost);
        monthlyMaintenance = v.findViewById(R.id.m_maint);
        monthlyParkingCost = v.findViewById(R.id.mpark_cost);
        registration = v.findViewById(R.id.reg_charge);
        monthlyIncome = v.findViewById(R.id.m_income);
        residenceType = v.findViewById(R.id.res_type);
        carCount = v.findViewById(R.id.car_count);
        twoWheelerCount = v.findViewById(R.id.twow_count);
        bicycleCount = v.findViewById(R.id.bicylce_count);
        carMileage = v.findViewById(R.id.car_mileage);
        carAge = v.findViewById(R.id.car_age);
        carFuel = v.findViewById(R.id.car_fuel);
        twoWheelerAge = v.findViewById(R.id.twow_age);
        twoWheelerFuel = v.findViewById(R.id.twow_fuel);
        twoWheelerMileage = v.findViewById(R.id.twow_milage);
        bicycleAge = v.findViewById(R.id.bicylce_age);

        step0 = v.findViewById(R.id.step0);
        step1 = v.findViewById(R.id.step1);
        next = v.findViewById(R.id.next);
        prev = v.findViewById(R.id.prev);
        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);

        nextStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyStep0() && Home.STEP<Home.MAX) {

                    step0.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(saveStep);
                    prev.setOnClickListener(backStep);
                    next.setImageDrawable(getResources().getDrawable(R.drawable.save));
                    one.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                    two.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
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
                one.setCardBackgroundColor(getResources().getColor(R.color.secondaryDark));
                two.setCardBackgroundColor(getResources().getColor(R.color.primaryMain));
                Home.STEP -= 1;
            }
        };

        saveStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save to firebase
                if (!verifyStep1()) return;
                updateFirebase();
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

    private void updateFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user/" + user.getUid());
        dbRef.child("economic_info").setValue(economic).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        if (monthlyIncome.getSelectedItemPosition() == 0) { ( (TextView) monthlyIncome.getSelectedView()).setError("Select choice"); return false;}
        if (residenceType.getSelectedItemPosition() == 0) { ( (TextView) residenceType.getSelectedView()).setError("Select choice"); return false;}

        if (carCount.getSelectedItemPosition() == 0) {
            ( (TextView) carCount.getSelectedView()).setError("Select choice"); return false;
        } else if (carCount.getSelectedItemPosition() > 1) {
//            if (carMileage.getSelectedItemPosition() == 0) { ( (TextView) carMileage.getSelectedView()).setError("Select choice"); return false;}
            if (carAge.getSelectedItemPosition() == 0) { ( (TextView) carAge.getSelectedView()).setError("Select choice"); return false;}
            if (carFuel.getSelectedItemPosition() == 0) { ( (TextView) carFuel.getSelectedView()).setError("Select choice"); return false;}

            economic.setCar_mileage(carMileage.getSelectedItem().toString());
            economic.setCar_age(carAge.getSelectedItem().toString());
            economic.setCar_fuel(carFuel.getSelectedItem().toString());
        }

        if (twoWheelerCount.getSelectedItemPosition() == 0) {
            ( (TextView) twoWheelerCount.getSelectedView()).setError("Select choice"); return false;
        } else if (twoWheelerCount.getSelectedItemPosition() > 1) {
            if (twoWheelerAge.getSelectedItemPosition() == 0) { ( (TextView) twoWheelerAge.getSelectedView()).setError("Select choice"); return false;}
            if (twoWheelerFuel.getSelectedItemPosition() == 0) { ( (TextView) twoWheelerFuel.getSelectedView()).setError("Select choice"); return false;}
//            if (twoWheelerMileage.getSelectedItemPosition() == 0) { ( (TextView) twoWheelerMileage.getSelectedView()).setError("Select choice"); return false;}

            economic.setTwow_age(twoWheelerAge.getSelectedItem().toString());
            economic.setTwow_fuel(twoWheelerFuel.getSelectedItem().toString());
            economic.setTwow_milage(twoWheelerMileage.getSelectedItem().toString());
        }

        if (bicycleCount.getSelectedItemPosition() == 0) {
            ( (TextView) bicycleCount.getSelectedView()).setError("Select choice"); return false;
        } else if (bicycleCount.getSelectedItemPosition() > 1) {
            if (bicycleCount.getSelectedItemPosition() == 0) { ( (TextView) bicycleCount.getSelectedView()).setError("Select choice"); return false;}

            economic.setBicylce_age(bicycleAge.getSelectedItem().toString());
        }

        economic.setBicycle(bicycleCount.getSelectedItemPosition() - 1 + "");
        economic.setCar(carCount.getSelectedItemPosition() - 1 + "");
        economic.setTwo_wheeler(twoWheelerCount.getSelectedItemPosition() - 1 + "");
        economic.setMonthly_income(monthlyIncome.getSelectedItem().toString());
        economic.setResidence_type(residenceType.getSelectedItem().toString());

        return true;
    }

    private boolean verifyStep1() {
        if (monthlyEMI.getText() == null || monthlyEMI.getText().toString().equals("")) { monthlyEMI.setError("Age required."); return false; }
        if (monthlyFuelCost.getText() == null || monthlyFuelCost.getText().toString().equals("")) { monthlyFuelCost.setError("Age required."); return false; }
        if (monthlyMaintenance.getText() == null || monthlyMaintenance.getText().toString().equals("")) { monthlyMaintenance.setError("Age required."); return false; }
//        if (registration.getText() == null || registration.getText().toString().equals("")) { registration.setError("Age required."); return false; }
//        if (monthlyParkingCost.getText() == null || monthlyParkingCost.getText().toString().equals("")) { monthlyParkingCost.setError("Age required."); return false; }

        economic.setMonthly_emi(monthlyEMI.getText().toString());
        economic.setMonthly_fuel_cost(monthlyFuelCost.getText().toString());
        economic.setMonthly_parking_charges(monthlyParkingCost.getText().toString());
        economic.setVehicle_registration(registration.getText().toString());
        economic.setMonthly_maintenance_cost(monthlyMaintenance.getText().toString());

        return true;
    }

    private void updatePref() {
        Home.PARTAB = 1;
        Home.sharedPref.edit().putInt("partab", 1).apply();
    }

}
