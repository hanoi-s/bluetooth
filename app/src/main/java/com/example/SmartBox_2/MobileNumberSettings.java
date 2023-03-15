package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MobileNumberSettings extends AppCompatActivity {

    ImageView btn_back;
    EditText curr_phone, new_phone;
    Button btn_save;

    FirebaseAuth fAuth;
    DatabaseReference mDatabase;
    DatabaseReference mDatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_settings);

        btn_back = findViewById(R.id.btn_changemobile_back);
        curr_phone = findViewById(R.id.et_current_phone_number);
        new_phone = findViewById(R.id.et_new_phone_number);
        btn_save = findViewById(R.id.btn_changephone_save);

        // Firebase Authentication
        fAuth = FirebaseAuth.getInstance();
        // Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("users");

        mDatabase.child("mobile_number").get().addOnCompleteListener(task1 -> {
            String result1 =  String.valueOf(task1.getResult().getValue());
            curr_phone.setText(result1);
        });

        // Images
        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AccountInformationSettings.class));
        });

        btn_save.setOnClickListener(view -> {
            // Gets the user input
            String newphone2 = new_phone.getText().toString().trim();
            // +63 instead of 0 for phone numbers; final_phone1 will now be "+639...."
            String country_code = "+63";
            String final_phone1;

            if (newphone2.length() < 11) {
                new_phone.setError("Insufficient digits");
                new_phone.requestFocus();
                return;
            }

            if (newphone2.length() > 13) {
                new_phone.setError("Too many digits");
                new_phone.requestFocus();
                return;
            }

            // If sim card is 11 digits
            if(newphone2.length() == 11) {
                // Replace 11 digits with +63 country code
                final_phone1 = country_code + newphone2.substring(1);
                mDatabase2.orderByChild("mobile_number").equalTo(final_phone1).get().addOnCompleteListener(task3 -> {
                    String result = String.valueOf(task3.getResult().getValue());
                    // If box's sim card is already in use
                    if(result != "null") {
                        new_phone.setError("This sim card is already in use");
                        new_phone.requestFocus();
                    } else {
                        mDatabase.child("mobile_number").setValue(final_phone1);
                        Toast.makeText(this,"Update successful",Toast.LENGTH_SHORT).show();
                        // Get box's pin
                        mDatabase.child("mobile_number").get().addOnCompleteListener(task2 -> {
                            curr_phone.setText(String.valueOf(task2.getResult().getValue()));
                        });
                    }
                });
            } else if (newphone2.length() == 13) {
                final_phone1 = newphone2;
                mDatabase2.orderByChild("mobile_number").equalTo(final_phone1).get().addOnCompleteListener(task4 -> {
                    String result = String.valueOf(task4.getResult().getValue());
                    // If box's sim card is already in use
                    if(result != "null") {
                        new_phone.setError("This sim card is already in use");
                        new_phone.requestFocus();
                    } else {
                        mDatabase.child("mobile_number").setValue(final_phone1);
                        Toast.makeText(this,"Update successful",Toast.LENGTH_SHORT).show();
                        // Get box's pin
                        mDatabase.child("mobile_number").get().addOnCompleteListener(task2 -> {
                            curr_phone.setText(String.valueOf(task2.getResult().getValue()));
                        });
                    }
                });
            }
        });
    }
}