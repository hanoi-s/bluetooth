package com.example.SmartBox_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BoxSettings extends AppCompatActivity {

    Button btn_connect;
    EditText et_ssid, et_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_settings);

        btn_connect = findViewById(R.id.btn_connect_wifi);
        et_ssid = findViewById(R.id.box_ssid);
        et_pw = findViewById(R.id.box_ssid_password);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_ssid.getText().toString().equals("") && et_pw.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"FILL UP ALL FIELDS",Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        String sent_ssid = et_ssid.getText().toString();
                        String sent_password = et_pw.getText().toString();

                        String msg = sent_ssid + " " + sent_password;
//                        msg+="\n";
                        MainActivity.outputStream.write(msg.getBytes());

                        et_ssid.setText("Smgm");
                        et_pw.setText("shanicka");
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });


    }


}