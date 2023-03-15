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

public class ChangeEmailSettings extends AppCompatActivity {

    ImageView btn_back;
    EditText curr_email, new_email;
    Button btn_update;
    FirebaseAuth fAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_settings);

        btn_back = findViewById(R.id.btn_changeemail_back);
        curr_email = findViewById(R.id.et_current_email);
        new_email = findViewById(R.id.et_new_email);
        btn_update = findViewById(R.id.btn_changeemail_save);

        // Firebase Authentication
        fAuth = FirebaseAuth.getInstance();
        // Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());

        mDatabase.child("email").get().addOnCompleteListener(task1 -> {
            String result1 =  String.valueOf(task1.getResult().getValue());
            curr_email.setText(result1);
        });

        // Images
        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AccountInformationSettings.class));
        });

        btn_update.setOnClickListener(view -> {
            // Gets the user input
            String new_email2 = new_email.getText().toString().trim();

            fAuth.getCurrentUser().updateEmail(new_email2)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Email updated successfully
                            Toast.makeText(getApplicationContext(),
                                    "Email updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to update email
                            Toast.makeText(getApplicationContext(),
                                    "Failed to update email", Toast.LENGTH_SHORT).show();
                        }
                    });

        });
    }
}