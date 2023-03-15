package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BoxSettings0 extends AppCompatActivity {

    ImageView btn_back, btn_wifi, btn_user, btn_sim, btn_pin;
    TextView main_wifi, label_wifi, main_user, label_user, main_sim, label_sim, main_pin, label_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_settings0);

        // Images
        btn_back = findViewById(R.id.btn_boxsettings_back);
        btn_wifi = findViewById(R.id.img_wifi);
        btn_user = findViewById(R.id.img_user);
        btn_sim = findViewById(R.id.img_sim);
        btn_pin = findViewById(R.id.img_pin);
        // TextViews
        main_wifi = findViewById(R.id.label_boxsettings_wifi_connection);
        label_wifi = findViewById(R.id.label_info_wifi_connection);
        main_user = findViewById(R.id.label_boxsettings_user);
        label_user = findViewById(R.id.label_info_user);
        main_sim = findViewById(R.id.label_boxsettings_sim_card);
        label_sim = findViewById(R.id.label_info_sim_card);
        main_pin = findViewById(R.id.label_boxsettings_pin_code);
        label_pin = findViewById(R.id.label_info_pin_code);

        // Back button
        btn_back.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), MainActivity.class)); });

        // Wifi option
        btn_wifi.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), WifiConnectionSettings.class)); });
        main_wifi.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), WifiConnectionSettings.class)); });
        label_wifi.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), WifiConnectionSettings.class)); });

        // User option
        btn_user.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), UserConnectionSettings.class)); });
        main_user.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), UserConnectionSettings.class)); });
        label_user.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), UserConnectionSettings.class)); });

        // Sim card option
        btn_sim.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), SimCardSettings.class)); });
        main_sim.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), SimCardSettings.class)); });
        label_sim.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), SimCardSettings.class)); });

        // Pin option
        btn_pin.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), PinSettings.class)); });
        main_pin.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), PinSettings.class)); });
        label_pin.setOnClickListener(view -> { startActivity(new Intent(getApplicationContext(), PinSettings.class)); });

    }
}