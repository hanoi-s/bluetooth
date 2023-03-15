package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_profile, btn_box;
    Button btn_notifications, btn_pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_box = findViewById(R.id.btn_box_settings);
        btn_profile = findViewById(R.id.btn_profile_settings);
        btn_notifications = findViewById(R.id.btn_notif_section);
        btn_pics = findViewById(R.id.btn_pictures);

        btn_box.setOnClickListener(v -> { startActivity(new Intent(getApplicationContext(), BoxSettings0.class)); });
        btn_profile.setOnClickListener(v -> { startActivity(new Intent(getApplicationContext(), ProfileSettings.class)); });
        btn_notifications.setOnClickListener(v -> { startActivity(new Intent(getApplicationContext(), NotificationActivity.class)); });
        btn_pics.setOnClickListener(v -> { startActivity(new Intent(getApplicationContext(), PictureActivity.class)); });

    }

}
