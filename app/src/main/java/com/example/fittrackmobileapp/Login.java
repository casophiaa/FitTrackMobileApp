package com.example.fittrackmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    private Button loginBtn;
    private EditText userEmail, userPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = findViewById(R.id.loginBtn);
        TextView backToRegBtn = findViewById(R.id.loginPageRegBtn);

        userEmail = findViewById(R.id.EmailTxt);
        userPW = findViewById(R.id.passwordTxt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitLogin();
            }
        });

        backToRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToReg();
            }
        });

    }

    private boolean areInputsValid() {
        if (userEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userPW.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true; // All inputs are valid
    }

    public void submitLogin() {
        // Check if all inputs are valid
        if (areInputsValid()) {
            // Proceed with the registration if inputs are valid
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
    }

    public void backToReg(){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();
    }
}