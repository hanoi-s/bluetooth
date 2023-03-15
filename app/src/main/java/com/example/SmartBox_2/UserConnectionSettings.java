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

public class UserConnectionSettings extends AppCompatActivity {

    EditText et_username;
    Button btn_connect_username;

    ImageView btn_back;

    BluetoothAdapter bluetoothAdapter1;
    BluetoothSocket bluetoothSocket1;
    BluetoothDevice bluetoothDevice1;

    public static OutputStream outputStream1;
    public static InputStream inputStream1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_connection_settings);

        et_username = findViewById(R.id.box_username2);
        btn_connect_username = findViewById(R.id.btn_connect_account);
        btn_back = findViewById(R.id.btn_userconnection_back);

        try{
            FindBluetoothDevice1();
            openBluetooth1();
        } catch (Exception ex){ ex.printStackTrace(); }

        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), BoxSettings0.class));
        });

        btn_connect_username.setOnClickListener(v -> {
            if (et_username.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),"FILL UP ALL FIELDS",Toast.LENGTH_SHORT).show();
            }
            else {
                try{
                    String sent_username = et_username.getText().toString();
                    String code = "USERNAME";
                    String msg = code + " " + sent_username;
                    UserConnectionSettings.outputStream1.write(msg.getBytes());

                    et_username.setText("shanikoy");

                    bluetoothSocket1.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

    }

    void FindBluetoothDevice1(){
        try{
            bluetoothAdapter1 = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter1 == null){
//                Toast.makeText(this,"No Bluetooth Adapter found",Toast.LENGTH_SHORT).show();
            }
            if(bluetoothAdapter1.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT,0);
            }
            Set<BluetoothDevice> pairedDevice = bluetoothAdapter1.getBondedDevices();

            int flag = 0;
            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev:pairedDevice){

                    if(pairedDev.getName().equals("HC-05")){
                        bluetoothDevice1 = pairedDev;
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

    void openBluetooth1() throws IOException {
        try{
            //Standard uuid from string //
            UUID uuidString = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket1 = bluetoothDevice1.createRfcommSocketToServiceRecord(uuidString);
            bluetoothSocket1.connect();
            outputStream1 = bluetoothSocket1.getOutputStream();
            inputStream1 = bluetoothSocket1.getInputStream();
        }catch (Exception ex){
            Toast.makeText(this,"Bluetooth not connected.",Toast.LENGTH_SHORT).show();
        }
    }
}