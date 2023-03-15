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

public class SimCardSettings extends AppCompatActivity {

    ImageView btn_changesim_back;
    EditText et_current_sim_card, et_new_sim_card;
    Button btn_changesim_save;

    FirebaseAuth fAuth;
    DatabaseReference mDatabase;
    DatabaseReference mDatabase2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_card_settings);

        btn_changesim_back = findViewById(R.id.btn_changesim_back);
        et_current_sim_card = findViewById(R.id.et_current_sim_card);
        et_new_sim_card = findViewById(R.id.et_new_sim_card);
        btn_changesim_save = findViewById(R.id.btn_changesim_save);

        // Firebase Authentication
        fAuth = FirebaseAuth.getInstance();
        // Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("users");

        mDatabase.child("box_simcard").get().addOnCompleteListener(task1 -> {
           String result1 =  String.valueOf(task1.getResult().getValue());
            et_current_sim_card.setText(result1);
        });

        // Images
        btn_changesim_back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), BoxSettings0.class));
        });

//        // Get box's pin
//        mDatabase.child("box_simcard").get().addOnCompleteListener(task2 -> {
//            et_current_sim_card.setText(String.valueOf(task2.getResult().getValue()));
//        });


        btn_changesim_save.setOnClickListener(view -> {
            // Gets the user input
            String newsim2 = et_new_sim_card.getText().toString().trim();
            // +63 instead of 0 for phone numbers; final_phone1 will now be "+639...."
            String country_code = "+63";
            String final_phone1;

            if (newsim2.length() < 11) {
                et_new_sim_card.setError("Insufficient digits");
                et_new_sim_card.requestFocus();
                return;
            }

            if (newsim2.length() > 13) {
                et_new_sim_card.setError("Too many digits");
                et_new_sim_card.requestFocus();
                return;
            }

            // If sim card is 11 digits
            if(newsim2.length() == 11) {
                // Replace 11 digits with +63 country code
                final_phone1 = country_code + newsim2.substring(1);
                mDatabase2.orderByChild("box_simcard").equalTo(final_phone1).get().addOnCompleteListener(task3 -> {
                    String result = String.valueOf(task3.getResult().getValue());
                    // If box's sim card is already in use
                    if(result != "null") {
                        et_new_sim_card.setError("This sim card is already in use");
                        et_new_sim_card.requestFocus();
                    } else {
                        mDatabase.child("box_simcard").setValue(final_phone1);
                        Toast.makeText(this,"Update successful",Toast.LENGTH_SHORT).show();
                        // Get box's pin
                        mDatabase.child("box_simcard").get().addOnCompleteListener(task2 -> {
                            et_current_sim_card.setText(String.valueOf(task2.getResult().getValue()));
                        });
                    }
                });
            } else if (newsim2.length() == 13) {
                final_phone1 = newsim2;
                mDatabase2.orderByChild("box_simcard").equalTo(final_phone1).get().addOnCompleteListener(task4 -> {
                    String result = String.valueOf(task4.getResult().getValue());
                    // If box's sim card is already in use
                    if(result != "null") {
                        et_new_sim_card.setError("This sim card is already in use");
                        et_new_sim_card.requestFocus();
                    } else {
                        mDatabase.child("box_simcard").setValue(final_phone1);
                        Toast.makeText(this,"Update successful",Toast.LENGTH_SHORT).show();
                        // Get box's pin
                        mDatabase.child("box_simcard").get().addOnCompleteListener(task2 -> {
                            et_current_sim_card.setText(String.valueOf(task2.getResult().getValue()));
                        });
                    }
                });
            }
        });
    }
}