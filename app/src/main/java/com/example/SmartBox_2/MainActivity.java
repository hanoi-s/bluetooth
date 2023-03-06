package com.example.SmartBox_2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btn_verify, btn_profile, btn_box;
    TextView tv_unverifiedWarning;
    FirebaseAuth fAuth;

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;

    public static OutputStream outputStream;
    public static InputStream inputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            FindBluetoothDevice();
            openBluetooth();

        }catch (Exception ex){
            ex.printStackTrace();
        }

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

    void FindBluetoothDevice(){
        try{
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter==null){
                Toast.makeText(this,"No Bluetooth Adapter found",Toast.LENGTH_SHORT).show();
            }
            if(bluetoothAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT,0);
            }
            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            int flag = 0;
            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev:pairedDevice){

                    // My Bluetoth printer name is BTP_F09F1A
                    if(pairedDev.getName().equals("HC-05")){
                        bluetoothDevice=pairedDev;
                        Toast.makeText(this,"Application Ready !",Toast.LENGTH_SHORT).show();
                        flag = 1;
                        break;
                    }
                }

                if (flag == 0) {
                    Toast.makeText(this,"Application Error Check Bluetooth Connection !!",Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    void openBluetooth() throws IOException {
        try{

            //Standard uuid from string //
            UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
            bluetoothSocket.connect();
            outputStream=bluetoothSocket.getOutputStream();
            inputStream=bluetoothSocket.getInputStream();

//            startActivity(new Intent(getApplicationContext(), BoxSettings.class));

        }catch (Exception ex){
            Toast.makeText(this,"bluetooth not connected....",Toast.LENGTH_SHORT).show();
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}
