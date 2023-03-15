package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PinSettings extends AppCompatActivity {

    ImageView btn_changepin_back;
    EditText et_current_pin, et_new_pin;
    Button btn_changepin_save;
    FirebaseAuth fAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_settings);

        btn_changepin_back = findViewById(R.id.btn_changepin_back);
        et_current_pin = findViewById(R.id.et_current_pin);
        et_new_pin = findViewById(R.id.et_new_pin);
        btn_changepin_save = findViewById(R.id.btn_changepin_save);

        // Firebase Authentication
        fAuth = FirebaseAuth.getInstance();
        // Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());

        // Images
        btn_changepin_back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), BoxSettings0.class));
        });

        btn_changepin_save.setOnClickListener(view -> {
            // Gets the user input
            String pin2 = et_current_pin.getText().toString().trim();
            String newpin2 = et_new_pin.getText().toString().trim();

            // Displays error if Username field is empty
            if(TextUtils.isEmpty(newpin2)) {
                et_new_pin.setError("New PIN is required");
                et_new_pin.requestFocus();
                return;
            }

            // Displays error if Password is less than 6 characters
            if(newpin2.length() != 6) {
                et_new_pin.setError("PIN must be 6 digits");
                et_new_pin.requestFocus();
                return;
            }

            mDatabase.child("box_pin").get().addOnCompleteListener(task1 -> {
                String result1 = String.valueOf(task1.getResult().getValue());

                if(!result1.equals(pin2)) {
                    et_current_pin.setError("Wrong pin");
                    et_current_pin.requestFocus();
                } else {
                    mDatabase.child("box_pin").setValue(newpin2);
                    et_current_pin.getText().clear();
                }
            });


        });


    }
}