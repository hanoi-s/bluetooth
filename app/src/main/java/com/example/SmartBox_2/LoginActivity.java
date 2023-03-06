package com.example.SmartBox_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btn_login;
    TextView btn_register;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.log_et_email);
        password = findViewById(R.id.log_et_password);
        btn_register = findViewById(R.id.log_btn_register);
        btn_login = findViewById(R.id.btn_login);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.log_progressBar);

        btn_login.setOnClickListener(v -> {
            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();

            if(TextUtils.isEmpty(email2)) {
                email.setError("Email is required");
                return;
            }

            if(TextUtils.isEmpty(password2)) {
                password.setError("Password is required");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // Register the user in Firebase

            fAuth.signInWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

        });

        btn_register.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });



    }
}