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

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText firstNameTxt, lastNameTxt, emailTxt, passwordTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get references to all input fields
        firstNameTxt = findViewById(R.id.FirstNameTxt);
        lastNameTxt = findViewById(R.id.LastNameTxt);
        emailTxt = findViewById(R.id.EmailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);

        Button nextBtn = findViewById(R.id.nextBtn);
        TextView loginBtn = findViewById(R.id.regPageLoginBtn);

        // Handle login button click
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {gotoLogin();}
        });

        // Handle register button click
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMoreInfo();
            }
        });
    }

    // Method to check if all inputs are valid
    private boolean areInputsValid() {
        if (firstNameTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastNameTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (emailTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passwordTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true; // All inputs are valid
    }

    public void gotoMoreInfo() {
        // Get Firebase instance
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Retrieve user input values
        String firstName = firstNameTxt.getText().toString();
        String lastName = lastNameTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        // Check if all inputs are valid
        if (areInputsValid()) {
            // Register user in Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User registered successfully!", Toast.LENGTH_SHORT).show();

                            // Pass data to the next activity
                            Intent intent = new Intent(Register.this, RegMoreInfo.class);
                            intent.putExtra("firstName", firstName);
                            intent.putExtra("lastName", lastName);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    public void gotoLogin() {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }
}