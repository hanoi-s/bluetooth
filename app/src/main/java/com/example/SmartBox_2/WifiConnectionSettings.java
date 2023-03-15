package com.example.SmartBox_2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class WifiConnectionSettings extends AppCompatActivity {

    ImageView btn_back;
    EditText et_ssid, et_pw;
    Button btn_connect_wifi;

    // Bluetooth
    BluetoothAdapter bluetoothAdapter2;
    BluetoothSocket bluetoothSocket2;
    BluetoothDevice bluetoothDevice2;

    public static OutputStream outputStream2;
    public static InputStream inputStream2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_connection_settings);

        btn_back = findViewById(R.id.btn_wificonnection_back);
        et_ssid = findViewById(R.id.box_ssid);
        et_pw = findViewById(R.id.box_ssid_password);
        btn_connect_wifi = findViewById(R.id.btn_connect_wifi);

        try{
            FindBluetoothDevice2();
            openBluetooth2();
        } catch (Exception ex){ ex.printStackTrace(); }

        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), BoxSettings0.class));
        });

        btn_connect_wifi.setOnClickListener(v -> {
            if (et_ssid.getText().toString().equals("") && et_pw.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),"FILL UP ALL FIELDS",Toast.LENGTH_SHORT).show();
            }
            else {
                try{
                    String code = "WIFI";
                    String sent_ssid = et_ssid.getText().toString();
                    String sent_password = et_pw.getText().toString();
                    String msg = code + " " + sent_ssid + " " + sent_password;
                    WifiConnectionSettings.outputStream2.write(msg.getBytes());


                    //et_ssid.setText("Smgm");
                    //et_pw.setText("shanicka");

                    bluetoothSocket2.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    void FindBluetoothDevice2(){
        try{
            bluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter2 == null){
//                Toast.makeText(this,"No Bluetooth Adapter found",Toast.LENGTH_SHORT).show();
            }
            if(bluetoothAdapter2.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT,0);
            }
            Set<BluetoothDevice> pairedDevice = bluetoothAdapter2.getBondedDevices();

            int flag = 0;
            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev:pairedDevice){

                    if(pairedDev.getName().equals("HC-05")){
                        bluetoothDevice2 = pairedDev;
                        Toast.makeText(this,"Application ready",Toast.LENGTH_SHORT).show();
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    Toast.makeText(this,"Error. Check bluetooth connection.",Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    void openBluetooth2() throws IOException {
        try{
            //Standard uuid from string //
            UUID uuidString = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket2 = bluetoothDevice2.createRfcommSocketToServiceRecord(uuidString);
            bluetoothSocket2.connect();
            outputStream2 = bluetoothSocket2.getOutputStream();
            inputStream2 = bluetoothSocket2.getInputStream();
        }catch (Exception ex){
            Toast.makeText(this,"Bluetooth not connected.",Toast.LENGTH_SHORT).show();
        }
    }
}