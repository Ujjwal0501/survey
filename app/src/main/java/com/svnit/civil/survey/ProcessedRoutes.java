package com.svnit.civil.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svnit.civil.survey.adapters.ProcessedRoutesAdapter;
import com.svnit.civil.survey.models.Route;
import com.svnit.civil.survey.services.ProcessRouteService;

import java.util.ArrayList;

public class ProcessedRoutes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Route> arrayList = new ArrayList<>();
    FirebaseUser user;
    DatabaseReference processed, confirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processed_routes);

        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        processed = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/routes");
        confirmed = FirebaseDatabase.getInstance().getReference("location/" + user.getUid()+"/travel_details/routes_confirmed");
        initAndSetAdapter();
    }

    private void initAndSetAdapter() {
        processed.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    arrayList.add(snapshot.getValue(Route.class));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProcessedRoutes.this, "Operation cancelled", Toast.LENGTH_LONG).show();
                ProcessedRoutes.this.finish();
            }
        });
    }

    private void setAdapter() {
        if (arrayList.size() == 0) {
            showPrompt("No processed routes to show.");
            return;
        }
        ProcessedRoutesAdapter adapter = new ProcessedRoutesAdapter(arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void showPrompt(String msg) {
        AlertDialog.Builder testDialog = new AlertDialog.Builder(this);
        testDialog.setMessage(msg);
        testDialog.setCancelable(true);

        testDialog.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // sign out current user
//                        startActivity(new Intent(getApplicationContext(), Userinfo.class).putExtra(key, "show"));
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = testDialog.create();
        alert11.show();

    }
}
