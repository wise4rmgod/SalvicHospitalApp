package com.developer.wise4rmgod.salvichospitalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.wise4rmgod.salvichospitalapp.adapter.PatientAdapter;
import com.developer.wise4rmgod.salvichospitalapp.model.PatientClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner spinner;
    EditText age, fullname, email;
    RecyclerView recyclerView;
    PatientAdapter patientAdapter;
    String TAG;
    private List<PatientClass> mUserList = new ArrayList<>();
    int minteger = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TAG = "TAG";
        FloatingActionButton fab = findViewById(R.id.addbtn);
        spinner = findViewById(R.id.spinner);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        recyclerView = findViewById(R.id.patientrecyclerview);

         pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
         editor = pref.edit();


        String cout= (String) pref.getString("count","get");
        String username= (String) pref.getString("username","get");
        Toast.makeText(Main2Activity.this, "Welcome"+" "+username, Toast.LENGTH_SHORT).show();
       // Toast.makeText(Main2Activity.this, cout, Toast.LENGTH_SHORT).show();

     final int realcount = Integer.parseInt(cout);
        Toast.makeText(Main2Activity.this, realcount+"", Toast.LENGTH_SHORT).show();
        String sexarray[] = getResources().getStringArray(R.array.sex_arrays);
        if (pref.getInt("mainactivitycount", 1) == realcount){
            fab.setEnabled(false);
            Toast.makeText(Main2Activity.this, "Button Disabled", Toast.LENGTH_SHORT).show();
        }
    fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minteger = minteger + 1;
                editor.putInt("mainactivitycount",minteger);
                editor.apply();

                if (pref.getInt("mainactivitycount", 1) == realcount){
                    Toast.makeText(Main2Activity.this, "You Have reached the maximum number of patients", Toast.LENGTH_SHORT).show();
                }
                else {
                    //savepatientdetails();
                    Toast.makeText(Main2Activity.this, "Added"+minteger, Toast.LENGTH_SHORT).show();

                }


            }
        });

        // Check internet connection
        checkinternet();
        // The code below will retrieve the patients details
        retrievepatientdetails();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ArrayAdapter sex = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sexarray);
        sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sex);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_tools) {
            Intent feeds = new Intent(getApplicationContext(), Settings.class);
            startActivity(feeds);
        } else if (id == R.id.nav_clear) {
            clearpatientsdetails();
        } else if (id == R.id.nav_share) {
            share();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void share() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Salvic App");
        share.putExtra(Intent.EXTRA_TEXT, "Salvic Test App");

        startActivity(Intent.createChooser(share, "Salvic"));
    }

    public void savepatientdetails() {
        Map<String, String> user = new HashMap<>();
        user.put("fullname", fullname.getText().toString());
        user.put("age", age.getText().toString());
        user.put("email", email.getText().toString());
        user.put("sex", spinner.getSelectedItem().toString());

// Add a new document with a generated ID
        db.collection("users").document("total").collection("totalusers")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(Main2Activity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Log.w(TAG, "Error adding document", e);
                        Toast.makeText(Main2Activity.this, " Not Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clearpatientsdetails() {

        db.collection("users").document("total")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void retrievepatientdetails() {

        db.collection("users").document("total").collection("totalusers")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }


                        //  List<PatientClass> patientClasses = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            PatientClass patientClass = new PatientClass();
                            patientClass.setEmail(doc.getString("email"));
                            patientClass.setAge(doc.getString("age"));
                            patientClass.setSex(doc.getString("sex"));
                            patientClass.setFullname(doc.getString("fullname"));

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            mUserList.add(patientClass);
                            patientAdapter = new PatientAdapter(getApplicationContext(), mUserList);
                            recyclerView.setAdapter(patientAdapter);
                            // if (doc.get("name") != null) {
                            //  cities.add(doc.getString("name"));

                            // }
                        }
                        Log.d(TAG, "Current cites in CA: " + mUserList);
                    }
                });

    }

    public void checkinternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {

            connected = false;
            Toast.makeText(getApplicationContext(), getString(R.string.nonetwork), Toast.LENGTH_SHORT).show();
        }
    }

    public void checkpatientscounts(){
        db.collection("patientsallowed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        editor.clear();
        editor.commit();
        super.onStop();
    }

}
