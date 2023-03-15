package com.example.SmartBox_2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BoxSettings extends AppCompatActivity {

//    Button btn_connect, btn_save;
//    EditText et_ssid, et_pw, et_username, et_simcard, et_pin;
//    FirebaseAuth fAuth;
//    DatabaseReference mDatabase;
//    DatabaseReference mDatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_settings);

        // Fields
//        et_ssid = findViewById(R.id.box_ssid);
//        et_pw = findViewById(R.id.box_ssid_password);
//        et_username = findViewById(R.id.box_username);
//        et_simcard = findViewById(R.id.box_sim_card);
//        et_pin = findViewById(R.id.box_pin);
//
//        // Buttons
//        btn_connect = findViewById(R.id.btn_connect_wifi);
//        btn_save = findViewById(R.id.btn_save);
//
//        // Database
//        // Firebase Authentication
//        fAuth = FirebaseAuth.getInstance();
//        // Firebase Realtime Database
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());
//        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("users");
//
//        // Get box's sim card
//        mDatabase.child("box_simcard").get().addOnCompleteListener(task -> {
//
//            String phoneFromDB = String.valueOf(task.getResult().getValue());
//
//            String country_code = "0";
//
//            String reverse = country_code + phoneFromDB.substring(3);
//
//            et_simcard.setText(reverse);
//        });
//
//        // Get box's pin
//        mDatabase.child("box_pin").get().addOnCompleteListener(task -> {
//            et_pin.setText(String.valueOf(task.getResult().getValue()));
//        });
//
//        mDatabase.child("username").get().addOnCompleteListener(task2 -> {
//           et_username.setText(String.valueOf(task2.getResult().getValue()));
//        });
//
//        btn_save.setOnClickListener(view -> {
//            String et_simcard2 = et_simcard.getText().toString().trim();
//            String et_pin2 = et_pin.getText().toString().trim();
//
//            // Displays error if Box's sim card field is empty
//            if(TextUtils.isEmpty(et_simcard2)) {
//                et_simcard.setError("Box's sim card is required");
//                et_simcard.requestFocus();
//                return;
//            }
//
//            // Displays error if Box's sim card field is empty
//            if(TextUtils.isEmpty(et_pin2)) {
//                et_pin.setError("Box's pin is required");
//                et_pin.requestFocus();
//                return;
//            }
//
//            // Displays error if Password is less than 6 characters
//            if(et_pin2.length() != 6) {
//                et_pin.setError("PIN must be 6 digits");
//                et_pin.requestFocus();
//                return;
//            }
//
//            // Displays error if Password is less than 6 characters
//            if(et_simcard2.length() != 11) {
//                et_simcard.setError("Box's sim card should be 11 digits");
//                et_simcard.requestFocus();
//                return;
//            }
//
//            // +63 instead of 0 for phone numbers
//            // final_phone1 will now be "+639...."
//            String country_code = "+63";
//            String final_phone1 = country_code + et_simcard2.substring(1);
//
//            // Checks if sim card is already used in the database by other users
//            mDatabase2.orderByChild("box_simcard").equalTo(final_phone1).get().addOnCompleteListener(task -> {
//                String result = String.valueOf(task.getResult().getValue());
//                // If box's sim card is already in use
//                if(result != "null") {
//                    et_simcard.setError("This sim card is already in use");
//                    et_simcard.requestFocus();
//                } else {
//                    // Saves changes
//                    mDatabase.child("box_simcard").setValue(final_phone1);
//                    mDatabase.child("box_pin").setValue(et_pin2);
//                }
//            });
//        });

//        btn_connect.setOnClickListener(v -> {
//            if (et_ssid.getText().toString().equals("") && et_pw.getText().toString().equals("")) {
//                Toast.makeText(getApplicationContext(),"FILL UP ALL FIELDS",Toast.LENGTH_SHORT).show();
//            }
//            else {
//                try{
//                    String sent_ssid = et_ssid.getText().toString();
//                    String sent_password = et_pw.getText().toString();
//
//                    String msg = sent_ssid + " " + sent_password;
////                        msg+="\n";
////                    MainActivity.outputStream.write(msg.getBytes());
//
//                    et_ssid.setText("Smgm");
//                    et_pw.setText("shanicka");
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });


    }
}