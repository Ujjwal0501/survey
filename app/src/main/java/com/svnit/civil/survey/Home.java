package com.svnit.civil.survey;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svnit.civil.survey.fragments.Brief;
import com.svnit.civil.survey.fragments.Part_a_a;
import com.svnit.civil.survey.fragments.Part_a_b;
import com.svnit.civil.survey.fragments.Part_a_c;
import com.svnit.civil.survey.fragments.Part_b_a;
import com.svnit.civil.survey.fragments.Part_b_b;
import com.svnit.civil.survey.helpers.LocationHelper;
import com.svnit.civil.survey.models.UserAddress;
import com.svnit.civil.survey.services.AutoService;

public class Home extends AppCompatActivity {
    public static UserAddress userAddress;
    public static FirebaseDatabase db;
    public static FirebaseAuth mAuth;
    public static FirebaseUser user;
    public static DataSnapshot snapshot;
    public static int STEP = 0, MAX = 0;
    public static ImageView backBtn;
    public LocationHelper locationHelper;
    private DatabaseReference dbRef;
    public static FragmentManager fragmentManager;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setTitle("");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        // enable back button
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.drawer);

        fragmentManager = getSupportFragmentManager();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, getString(R.string.unexpected_logout), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Splash.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("reason", "unexpected logout"));
        }
        dbRef = db.getReference(user.getUid());
        // keep data synced even when not required, only for provided reference
        dbRef.keepSynced(true);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // do some stuff once
//                userAddress = snapshot.child("address").getValue(UserAddress.class);
//                Home.snapshot = snapshot;
////                Userinfo.updateInfo();
//                if (userAddress != null) {
//                    if (userAddress.getCity().equals("") || userAddress.getState().equals("") || userAddress.getPincode().equals("")) {
//                        // show dialog to update address
//                        showPrompt("Your address is incomplete. Do you want to update your city now?", "city");
//
//                    } else if (userAddress.getLocality().equals("")) {
//                        // show dialog to update address
//                        showPrompt("Your address is incomplete. Do you want to update your locality now?", "locality");
//
//                    }
//                } else {
//                    // address is null
//                    // initialise for smooth working
//                    userAddress = new UserAddress();
//                    showPrompt("Your address is incomplete. Do you want to update your city now?", "city");
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                String title = menuItem.getTitle().toString();
                if (title.equals(getString(R.string.about_us))) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
//                    fragmentManager.popBackStack();
//                    fragmentManager.beginTransaction().replace(R.id.container, new About()).addToBackStack(null).commit();
                } else if (title.equals(getString(R.string.core_team))) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
//                    fragmentManager.popBackStack();
//                    fragmentManager.beginTransaction().replace(R.id.container, new Team()).addToBackStack(null).commit();
                } else if (title.equals(getString(R.string.donate))) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
//                    fragmentManager.popBackStack();
//                    fragmentManager.beginTransaction().replace(R.id.container, new Donate()).addToBackStack(null).commit();
                } else if (title.equals(getString(R.string.gallery))) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
//                    fragmentManager.popBackStack();
//                    fragmentManager.beginTransaction().replace(R.id.container, new DonationHistory()).addToBackStack(null).commit();
                } else if (title.equals(getString(R.string.user_profile))) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(false);
//                    startActivity(new Intent(getApplicationContext(), Userinfo.class));
                }

                return true;
            }
        });

        // update user details in navigation drawer
        if (user != null) {
            ((TextView) navigationView.findViewById(R.id.name)).setText(user.getDisplayName());
            ((TextView) navigationView.findViewById(R.id.email)).setText(user.getEmail());
            ((TextView) navigationView.findViewById(R.id.phone)).setText(user.getPhoneNumber());
        } else {
            Toast.makeText(this, getString(R.string.unexpected_logout), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Splash.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("reason", "unexpected logout"));
            this.finish();
        }

        // check if a fragment was set, and do not replace the old fragment onCreate
        // onCreate is executed on every run of activity (resume, restart, etc)
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.container, new Brief()).commit();
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        locationHelper = new LocationHelper();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!locationHelper.checkPermission(this)) locationHelper.reqPermission(Home.this, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // set listener to show userinfo activity
        navigationView.findViewById(R.id.user_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();
//                startActivity(new Intent(getApplicationContext(), Userinfo.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

        int fragmentCount = fragmentManager.getBackStackEntryCount();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else if (STEP > 0) {
            // call static function inside Fragment
            if (backBtn != null) backBtn.performClick();
        } else if (fragmentCount == 0) {
            super.onBackPressed();
        } else {
            fragmentManager.popBackStack();
            navigationView.getMenu().getItem(0).setChecked(true);
            STEP = 0;
            MAX = 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showPrompt(String msg, final String key) {
        AlertDialog.Builder testDialog = new AlertDialog.Builder(Home.this);
        testDialog.setMessage(msg);
        testDialog.setCancelable(true);

        testDialog.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // sign out current user
//                        startActivity(new Intent(getApplicationContext(), Userinfo.class).putExtra(key, "show"));
                        dialog.dismiss();
                    }
                });

        testDialog.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = testDialog.create();
        alert11.show();

    }

    public void part(View v) {
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, new Part_a_a()).addToBackStack(null).commit();
    }

    public void partab(View v) {
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, new Part_a_b()).addToBackStack(null).commit();
    }

    public void partac(View v) {
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, new Part_a_c()).addToBackStack(null).commit();
    }

    public void partba(View v) {
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, new Part_b_a()).addToBackStack(null).commit();
    }

    public void partbb(View v) {
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container, new Part_b_b()).addToBackStack(null).commit();
    }

    public void mark(View v) {
        Part_b_a.mark(v);
    }

    public void markself(View v) {
        Part_b_a.markself(v);
    }

    public void startService(View v) {
        run();
    }

    public void run() {

        if (!locationHelper.checkPermission(this)) {
            locationHelper.reqPermission(Home.this, this);
            return;
        }

        System.out.println(Thread.currentThread().getId());
        if (!isMyServiceRunning(AutoService.class)) {
            new Thread() {
                @Override
                public void run() {
                    startService(new Intent(getApplicationContext(), AutoService.class));
                }
            }.start();
            Snackbar.make(navigationView, "Service started successfully.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(navigationView, "Service already running.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LocationHelper.LOCATION_RC: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    if (!locationHelper.isEnabled(Home.this)) locationHelper.reqEnable(Home.this);

                    run();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}


