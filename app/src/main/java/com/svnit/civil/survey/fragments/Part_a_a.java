package com.svnit.civil.survey.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.svnit.civil.survey.Home;
import com.svnit.civil.survey.R;
import com.svnit.civil.survey.models.Info;
import com.svnit.civil.survey.models.Social;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Part_a_a extends Fragment {
    private Social social = new Social();
    private int boyCount = 0, girlCount = 0;
    private ArrayList<View> list = new ArrayList<View>();
    private EditText selfAge, spouseAge;
    private Spinner selfOccupation, spouseOccupation, daughter, son;
    private View.OnClickListener nextStep, saveStep, backStep;
    private CardView step0, step1, one, two;
    private ImageView next, prev;
    private Context context;
    private View v;


    public Part_a_a() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_part_a_a, container, false);
        context = v.getContext();
        getActivity().setTitle("Social Details");

        selfAge = v.findViewById(R.id.self_age);
        spouseAge = v.findViewById(R.id.spouse_age);
        selfOccupation = v.findViewById(R.id.self_ocu);
        spouseOccupation = v.findViewById(R.id.spouse_ocu);
        daughter = v.findViewById(R.id.daughters);
        son = v.findViewById(R.id.sons);

        step0 = v.findViewById(R.id.step0);
        step1 = v.findViewById(R.id.step1);
        next = v.findViewById(R.id.next);
        prev = v.findViewById(R.id.prev);
        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);

        nextStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyStep0() && Home.STEP < Home.MAX) {

                    if (boyCount == 0 && girlCount == 0) {
                        next.setOnClickListener(saveStep);
                        next.performClick();
                        return ;
                    }

                    step0.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
                    ((CardView) prev.getParent()).setVisibility(View.VISIBLE);
                    next.setOnClickListener(saveStep);
                    prev.setOnClickListener(backStep);
                    next.setImageDrawable(getResources().getDrawable(R.drawable.save));
                    one.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    two.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

//                    Toast.makeText(context, "boys: "+boyCount+"\ngirls: "+girlCount, Toast.LENGTH_LONG).show();

                    ( (LinearLayout) step1.getChildAt(0)).removeAllViews();
                    LayoutInflater layoutInflater = getLayoutInflater();
                    LinearLayout bv = null, gv = null;

                    if (girlCount != 0) {
                        gv = (LinearLayout) layoutInflater.inflate(R.layout.child_container, (LinearLayout) step1.getChildAt(0));
                        for (int i = 0; i < girlCount; i++) {
                            View vv = layoutInflater.inflate(R.layout.child_layout, gv);
                            list.add(vv);
                        }
                    }

                    if (boyCount != 0) {
                        bv = (LinearLayout) layoutInflater.inflate(R.layout.child_container, (LinearLayout) step1.getChildAt(0));
                        for (int i = 0; i < boyCount; i++) {
                            View vv = layoutInflater.inflate(R.layout.child_layout, bv);
                            list.add(vv);
                        }
                    }

                    if (bv != null) ( (TextView) bv.findViewById(R.id.title)).setText("Sons");
                    if (gv != null) ( (TextView) gv.findViewById(R.id.title)).setText("Daughters");
                    Home.STEP += 1;
                }
            }
        };

        backStep = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Home.STEP < 1) return;
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
                if (boyCount == 0 && girlCount == 0) {
                    updateFirebase();
                    return;
                }
                // save to firebase
                if (verifyStep1()) updateFirebase();
                next.setOnClickListener(nextStep);
            }
        };

        Home.STEP = 0;
        Home.MAX = 2;
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
        Home.preferences.edit().putString("", "")
                .putString("selfAge", social.getSelf().getAge())
                .putString("spouseAge", social.getSpouse().getAge())
                .putString("selfOccupation", social.getSelf().getOccupation())
                .putString("spouseOccupation", social.getSpouse().getOccupation())
                .putInt("children", social.getChildren().size()).apply();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = firebaseDatabase.getReference("user/" + user.getUid());
        dbRef.child("social_info").setValue(social).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        Info self = new Info(), spouse = new Info();

        self.setAge(selfAge.getText().toString());
        self.setOccupation(selfOccupation.getSelectedItem().toString());

        boyCount = son.getSelectedItemPosition() - 1;
        girlCount = daughter.getSelectedItemPosition() - 1;

        if (self.getAge().equals("")) {
            selfAge.setError("Age required.");
            return false;
        }
        if (self.getOccupation().equals("Occupation")) {
            ((TextView) selfOccupation.getSelectedView()).setError("Choose occupation");
            return false;
        }

        if (girlCount < 0) {
            ((TextView) daughter.getSelectedView()).setError("Your Daughters");
            return false;
        }
        if (boyCount < 0) {
            ((TextView) son.getSelectedView()).setError("Your Sons");
            return false;
        }

        if (boyCount > 0 || girlCount > 0) {

            if (spouse.getAge().equals("")) {
                spouseAge.setError("Age required.");
                return false;
            }
            if (spouse.getOccupation().equals("Occupation")) {
                ((TextView) spouseOccupation.getSelectedView()).setError("Choose occupation");
                return false;
            }

            spouse.setAge(spouseAge.getText().toString());
            spouse.setOccupation(spouseOccupation.getSelectedItem().toString());
        }

        if (!spouseAge.getText().toString().equals("") || spouseOccupation.getSelectedItemPosition() > 0) {

            if (spouse.getAge().equals("")) {
                spouseAge.setError("Age required.");
                return false;
            }
            if (spouse.getOccupation().equals("Occupation")) {
                ((TextView) spouseOccupation.getSelectedView()).setError("Choose occupation");
                return false;
            }

            spouse.setAge(spouseAge.getText().toString());
            spouse.setOccupation(spouseOccupation.getSelectedItem().toString());
        }

        social.setSelf(self);
        social.setSpouse(spouse);
        return true;
    }

    private boolean verifyStep1() {
        ArrayList<Info> temp = new ArrayList<Info>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Info tt = new Info();
            Spinner spinner =  (Spinner) ( (LinearLayout) list.get(i)).findViewById(R.id.occupation);
            EditText editText =  (EditText) ( (LinearLayout) list.get(i)).findViewById(R.id.age);
            tt.setOccupation(spinner.getSelectedItem().toString());
            tt.setAge(editText.getText().toString());

            if (tt.getOccupation().equals("Occupation")) { ( (TextView) spinner.getSelectedView()).setError("Required"); return false;}
            if (tt.getAge().equals("")) { editText.setError("Required"); return false; }

            temp.add(tt);
        }

        social.setChildren(temp);
        return true;
    }

    private void updatePref() {
        Home.PARTAA = 1;
        Home.sharedPref.edit().putInt("partaa", 1).apply();
    }

}
