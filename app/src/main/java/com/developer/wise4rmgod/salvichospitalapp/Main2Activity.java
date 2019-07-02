package com.developer.wise4rmgod.salvichospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner spinner;
    EditText age, fullname, email;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.addbtn);
        spinner = findViewById(R.id.spinner);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        recyclerView = findViewById(R.id.patientrecyclerview);

        String sexarray[] = getResources().getStringArray(R.array.sex_arrays);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savepatientdetails();
            }
        });
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
        } else if (id == R.id.nav_share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Salvic App");
            share.putExtra(Intent.EXTRA_TEXT, "Salvic Test App");

            startActivity(Intent.createChooser(share, "Salvic"));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void savepatientdetails() {
        Map<String, String> user = new HashMap<>();
        user.put("fullname", fullname.getText().toString());
        user.put("age", age.getText().toString());
        user.put("email", email.getText().toString());
        user.put("sex", spinner.getSelectedItem().toString());

// Add a new document with a generated ID
        db.collection("users")
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

    public void retrievepatientdetails() {

    }
}
