package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, mobile, pin, simcard;
    Button btn_register;
    TextView btn_login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.reg_et_username);
        email = findViewById(R.id.reg_et_email);
        password = findViewById(R.id.reg_et_password);
        mobile = findViewById(R.id.reg_et_user_mobile);
        pin = findViewById(R.id.reg_et_box_pin);
        simcard = findViewById(R.id.reg_et_box_simcard);

        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.reg_btn_login);

        // Firebase Authentication
        fAuth = FirebaseAuth.getInstance();
        // Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        progressBar = findViewById(R.id.reg_progressBar);

        // Validation; checks if user is already logged in
        if(fAuth.getCurrentUser() != null) {
            // If there is already a user logged in, user is taken to the Main activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        // This is what happens when the Login button link is clicked
        btn_login.setOnClickListener(v -> {
            // Takes the user to the Login page
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });

        // This is what happens when the Register button is clicked
        btn_register.setOnClickListener(v -> {

            // Gets the user input for email and password
            String username2 = username.getText().toString().trim();
            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();
            String mobile2 = mobile.getText().toString().trim();
            String pin2 = pin.getText().toString().trim();
            String simcard2 = simcard.getText().toString().trim();

            // Displays error if Username field is empty
            if(TextUtils.isEmpty(username2)) {
                username.setError("Username is required");
                username.requestFocus();
                return;
            }

            // Displays error if Email field is empty
            if(TextUtils.isEmpty(email2)) {
                email.setError("Email is required");
                email.requestFocus();
                return;
            }

            // Displays error if Mobile Number field is empty
            if(TextUtils.isEmpty(mobile2)) {
                mobile.setError("Mobile Number is required");
                mobile.requestFocus();
                return;
            }

            // Displays error if Mobile Number field is empty
            if(TextUtils.isEmpty(simcard2)) {
                simcard.setError("Box's Sim Card Number is required");
                simcard.requestFocus();
                return;
            }

            // Displays error if Password field is empty
            if(TextUtils.isEmpty(password2)) {
                password.setError("Password is required");
                password.requestFocus();
                return;
            }

            // Displays error if Password is less than 6 characters
            if(password2.length() < 6) {
                password.setError("Password must be more than 6 characters");
                password.requestFocus();
                return;
            }

            // Displays error if Password is less than 6 characters
            if(pin2.length() != 6) {
                pin.setError("PIN must be 6 digits");
                pin.requestFocus();
                return;
            }

            // Displays the progress bar
            progressBar.setVisibility(View.VISIBLE);

            // In order to traverse through the UID,
            // Realtime Database > Rules >
            // add the desired fields (username, notifications, mobile number)
            mDatabase.orderByChild("username").equalTo(username2).get().addOnCompleteListener(task1 -> {
                String result1 = String.valueOf(task1.getResult().getValue());
                // If username already exists
                if (result1 != "null") {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                    progressBar.setVisibility(View.GONE);
                } else {
                    mDatabase.orderByChild("mobile_number").equalTo(mobile2).get().addOnCompleteListener(task2 -> {
                       String result2 = String.valueOf(task2.getResult().getValue());
                       // If mobile number is already in use
                        if(result2 != "null") {
                            Toast.makeText(this, "Mobile number is already in use", Toast.LENGTH_SHORT).show();
                            mobile.requestFocus();
                            progressBar.setVisibility(View.GONE);
                        } else {

                            mDatabase.orderByChild("box_simcard").equalTo(simcard2).get().addOnCompleteListener(task3 -> {
                               String result3 = String.valueOf(task3.getResult().getValue());
                               // If box's sim card is already in use
                                if(result3 != "null") {
                                    Toast.makeText(this, "Box's sim card is already in use", Toast.LENGTH_SHORT).show();
                                    simcard.requestFocus();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    // Registers the user in Firebase Authentication
                                    fAuth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(task -> {
                                        // This is what happens when the User creation was successful
                                        if(task.isSuccessful()) {
                                            mDatabase.child(fAuth.getCurrentUser().getUid()).child("username").setValue(username2);
                                            mDatabase.child(fAuth.getCurrentUser().getUid()).child("email").setValue(email2);
                                            mDatabase.child(fAuth.getCurrentUser().getUid()).child("mobile_number").setValue(mobile2);
                                            mDatabase.child(fAuth.getCurrentUser().getUid()).child("box_simcard").setValue(simcard2);
                                            mDatabase.child(fAuth.getCurrentUser().getUid()).child("box_pin").setValue(pin2);

                                            // Sends verification link to user's given email
                                            FirebaseUser user = fAuth.getCurrentUser();
                                            user.sendEmailVerification().addOnSuccessListener(unused ->
                                                            Toast.makeText(RegisterActivity.this, "Verification has been sent to your email", Toast.LENGTH_SHORT))
                                                    .addOnFailureListener(e -> Log.d("Register Activity", "onFailure: Email not sent " + e.getMessage()));

                                            // Prompt for successful user creation
                                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                            // Takes the user to the Main Activity (App's main menu / dashboard)
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        } else {
                                            // This is what happens when the User creation was NOT successful

                                            // Displays error why user creation was not successful
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            // Removes progress bar
                                            progressBar.setVisibility(View.GONE);

                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        });
    }
}