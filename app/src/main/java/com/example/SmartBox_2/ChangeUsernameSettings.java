package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeUsernameSettings extends AppCompatActivity {

    ImageView btn_back;
    EditText curr_uname, new_uname;
    Button btn_update;
    FirebaseAuth fAuth;
    DatabaseReference mDatabase;
    DatabaseReference mDatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username_settings);

        btn_back = findViewById(R.id.btn_changeuname_back);
        curr_uname = findViewById(R.id.et_current_username);
        new_uname = findViewById(R.id.et_new_username);
        btn_update = findViewById(R.id.btn_changeuname_save);

        // Firebase Authentication
        fAuth = FirebaseAuth.getInstance();
        // Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("users");

        mDatabase.child("username").get().addOnCompleteListener(task1 -> {
            String result1 =  String.valueOf(task1.getResult().getValue());
            curr_uname.setText(result1);
        });

        // Images
        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AccountInformationSettings.class));
        });

        btn_update.setOnClickListener(view -> {
            // Gets the user input
            String curr_uname2 = curr_uname.getText().toString().trim();
            String new_uname2 = new_uname.getText().toString().trim();

            // Displays error if Username field is empty
            if(TextUtils.isEmpty(new_uname2)) {
                new_uname.setError("New PIN is required");
                new_uname.requestFocus();
                return;
            }

            // Displays error if Password is less than 6 characters
            if(new_uname2.length() < 6) {
                new_uname.setError("Username should be at least 6 characters long");
                new_uname.requestFocus();
                return;
            }

            mDatabase2.orderByChild("username").equalTo(new_uname2).get().addOnCompleteListener(task2 -> {
                String result = String.valueOf(task2.getResult().getValue());
                // If box's sim card is already in use
                if(result != "null") {
                    new_uname.setError("This username is already in use");
                    new_uname.requestFocus();
                } else {
                    mDatabase.child("username").setValue(new_uname2);
                    Toast.makeText(this,"Update successful",Toast.LENGTH_SHORT).show();
                    // Get box's pin
                    mDatabase.child("username").get().addOnCompleteListener(task3 -> {
                        curr_uname.setText(String.valueOf(task3.getResult().getValue()));
                    });
                }
            })
;

        });



    }
}