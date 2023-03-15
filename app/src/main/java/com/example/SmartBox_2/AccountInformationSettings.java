package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AccountInformationSettings extends AppCompatActivity {

    ImageView btn_back;
    TextView uname1, uname2, mobile1, mobile2, email1, email2;
    TextView warning;
    Button btn_verify;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information_settings);

        fAuth = FirebaseAuth.getInstance();

        btn_back = findViewById(R.id.btn_accinfo_back);
        uname1 = findViewById(R.id.label_username);
        uname2 = findViewById(R.id.label_username2);
        mobile1 = findViewById(R.id.label_phone_number);
        mobile2 = findViewById(R.id.label_phone_number2);
        email1 = findViewById(R.id.label_email);
        email2 = findViewById(R.id.label_email2);
//        warning = findViewById(R.id.warning_unverified_email);
//        btn_verify = findViewById(R.id.btn_verify_email);

        //FirebaseUser user = fAuth.getCurrentUser();

        // This is what happens when the user's email address is not verified
//        if(!user.isEmailVerified()) {
//            // Warnings are displayed
//            warning.setVisibility(View.VISIBLE);
//            btn_verify.setVisibility(View.VISIBLE);
//
//            // What happens when the "Verify Now" button is clicked
//            btn_verify.setOnClickListener(v ->
//                    user.sendEmailVerification().addOnSuccessListener(unused ->
//                                    Toast.makeText(v.getContext(), "Verification email has been sent", Toast.LENGTH_SHORT).show())
//                            .addOnFailureListener(e -> Log.d("Main: ", "onFailure: Email was not sent " + e.getMessage())));
//        }

        // Back button
        btn_back.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ProfileSettings.class)); });

        // Username option
        uname1.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ChangeUsernameSettings.class)); });
        uname2.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ChangeUsernameSettings.class)); });

        // Username option
        mobile1.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), MobileNumberSettings.class)); });
        mobile2.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), MobileNumberSettings.class)); });

        // Email option
        email1.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ChangeEmailSettings.class)); });
        email2.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ChangeEmailSettings.class)); });


    }
}