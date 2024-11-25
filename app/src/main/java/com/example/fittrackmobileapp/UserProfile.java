package com.example.fittrackmobileapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_user_profile);

        heightResult = findViewById(R.id.heightResult);
        weightResult = findViewById(R.id.weightResult);
        genderResult = findViewById(R.id.genderResult);
        contactNumber = findViewById(R.id.contactNumber);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        fetchUserProfile();
    }

    private void fetchUserProfile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Extract individual fields
                    String height = dataSnapshot.child("height").getValue(String.class);
                    String weight = dataSnapshot.child("weight").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String contact = dataSnapshot.child("contactNumber").getValue(String.class);

                    // Set the data to TextViews
                    heightResult.setText(height != null ? height : "N/A");
                    weightResult.setText(weight != null ? weight : "N/A");
                    genderResult.setText(gender != null ? gender : "N/A");
                    contactNumber.setText(contact != null ? contact : "N/A");
                } else {
                    Toast.makeText(UserProfile.this, "User info not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserProfile.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnClicked(View v) {
        finish();
    }
}
