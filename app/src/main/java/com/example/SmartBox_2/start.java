package com.example.SmartBox_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class start extends AppCompatActivity {

    EditText et_ssid;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnsend = findViewById(R.id.btnsend);
        et_ssid = findViewById(R.id.ssid);
        et_password = findViewById(R.id.password);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_ssid.getText().toString().equals("") && et_password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"FILL UP ALL FIELDS",Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        String sent_ssid = et_ssid.getText().toString();
                        String sent_password = et_password.getText().toString();

                        String msg = sent_ssid + " " + sent_password;
//                        msg+="\n";
                        BoxSettings.outputStream.write(msg.getBytes());

                        et_ssid.setText("PLDTHOMEFIBR5G6Uh4K");
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
