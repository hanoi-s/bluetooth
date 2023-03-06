package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btn_verify, btn_profile, btn_box;
    TextView tv_unverifiedWarning;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        btn_verify = findViewById(R.id.btn_verify);
        btn_box = findViewById(R.id.btn_box_settings);
        tv_unverifiedWarning = findViewById(R.id.tv_unverifiedWarning);

        FirebaseUser user = fAuth.getCurrentUser();

        // This is what happens when the user's email address is not verified
        if(!user.isEmailVerified()) {
            // Warnings are displayed
            tv_unverifiedWarning.setVisibility(View.VISIBLE);
            btn_verify.setVisibility(View.VISIBLE);

            // What happens when the "Verify Now" button is clicked
            btn_verify.setOnClickListener(v ->
                    user.sendEmailVerification().addOnSuccessListener(unused ->
                                    Toast.makeText(v.getContext(), "Verification email has been sent", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Log.d("Main: ", "onFailure: Email was not sent " + e.getMessage())));
        }

        btn_box.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), BoxSettings.class));
        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}
