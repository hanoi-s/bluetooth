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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, mobile;
    Button btn_register;
    TextView btn_login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.reg_et_username);
        email = findViewById(R.id.reg_et_email);
        password = findViewById(R.id.reg_et_password);
        mobile = findViewById(R.id.reg_et_user_mobile);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.reg_btn_login);

        fAuth = FirebaseAuth.getInstance();
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
            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();

            // Displays error if Email field is empty
            if(TextUtils.isEmpty(email2)) {
                email.setError("Email is required");
                email.requestFocus();
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

            // Displays the progress bar
            progressBar.setVisibility(View.VISIBLE);

            // Registers the user in Firebase
            fAuth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(task -> {
                // This is what happens when the User creation was successful
                if(task.isSuccessful()) {

                    // Sends verification link to user's given email
                    FirebaseUser user = fAuth.getCurrentUser();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this, "Verification has been sent to your email", Toast.LENGTH_SHORT);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Register Activity", "onFailure: Email not sent " + e.getMessage());
                        }
                    });


                    // Prompt for successful user creation
                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    // Takes the user to the Main Activity (App's main menu / dashboard)
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    // This is what happens when the User creation was NOT successful

                    // Displays error why user creation was not successful
                    Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    // Removes progress bar
                    progressBar.setVisibility(View.GONE);
                }
            });

        });

    }
}