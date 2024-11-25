package com.example.fittrackmobileapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private TextView heightResult, weightResult, genderResult, contactNumber;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth and Database Reference
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI components
        heightResult = findViewById(R.id.heightResult);
        weightResult = findViewById(R.id.weightResult);
        genderResult = findViewById(R.id.genderResult);
        contactNumber = findViewById(R.id.contactNumber);

        // Fetch user details
        fetchUserProfile();
    }

    private void fetchUserProfile() {
        String userId = firebaseAuth.getCurrentUser().getUid(); // Get current user's UID

        // Fetch user data from the database
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Extract user details
                    String height = dataSnapshot.child("height").getValue(String.class);
                    String weight = dataSnapshot.child("weight").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String contact = dataSnapshot.child("email").getValue(String.class);

                    // Display details in the TextViews
                    heightResult.setText(height != null ? height : "N/A");
                    weightResult.setText(weight != null ? weight : "N/A");
                    genderResult.setText(gender != null ? gender : "N/A");
                    contactNumber.setText(contact != null ? contact : "N/A");
                } else {
                    Toast.makeText(UserProfile.this, "User profile not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserProfile.this, "Failed to load user profile: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void btnClicked(View v) {
        finish();
    }


}

