package com.developer.wise4rmgod.salvichospitalapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class Settings extends AppCompatActivity {

    EditText username,patientsallowed,numberofpatients;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final TextView test = findViewById(R.id.test);
        username = findViewById(R.id.username);
        patientsallowed  = findViewById(R.id.numberofpatientsallowed);
        numberofpatients  = findViewById(R.id.numberofpatients);
        save  = findViewById(R.id.savebtn);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

       // Toast.makeText(this, pref.getInt("count", 1) +"", Toast.LENGTH_SHORT).show();
      //  int num1 = 0;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  minteger = minteger + 1;
               // test.setText(minteger+"");
                editor.putString("count", patientsallowed.getText().toString());
                editor.putString("username",username.getText().toString());
                editor.apply();
                Toast.makeText(getApplicationContext(), "Max:"+ pref.getString("count","get"), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
