package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileSettings extends AppCompatActivity {

    ImageView btn_back, btn_account, btn_password;
    TextView main_account, label_account, main_password, label_password;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        // ImageViews
        btn_back = findViewById(R.id.btn_profilesettings_back);
        btn_account = findViewById(R.id.img_profile);
        btn_password = findViewById(R.id.img_lock);

        // TextViews
        main_account = findViewById(R.id.label_profilesettings_account_information);
        label_account = findViewById(R.id.label_info_account_information);
        main_password = findViewById(R.id.label_profilesettings_change_pw);
        label_password = findViewById(R.id.label_info_change_pw);

        // Button
        btn_logout = findViewById(R.id.btn_profile_settings_logout);

        // OnClick Listeners
        // Back button
        btn_back.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), MainActivity.class)); });

        // Account Information option
        // TO DO: CHANGE DESTINATION ACTIVITY
        btn_account.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), AccountInformationSettings.class)); });
        main_account.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), AccountInformationSettings.class)); });
        label_account.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), AccountInformationSettings.class)); });

        // Account Information option
        // TO DO: CHANGE DESTINATION ACTIVITY
        btn_password.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ChangePasswordSettings.class)); });
        main_password.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ChangePasswordSettings.class)); });
        label_password.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), ChangePasswordSettings.class)); });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}