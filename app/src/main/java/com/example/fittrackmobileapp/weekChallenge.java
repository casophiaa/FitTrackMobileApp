package com.example.fittrackmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class weekChallenge extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChallengeAdapter challengeAdapter;
    private ArrayList<ChallengeData> challengeDataList = new ArrayList<>();
    private String exerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty);

        // Get the selected exercise name
        exerciseName = getIntent().getStringExtra("EXERCISE_NAME");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter
        challengeAdapter = new ChallengeAdapter(challengeDataList, this);
        recyclerView.setAdapter(challengeAdapter);

        // Fetch data from Firebase
        fetchDayChallenges();
    }

    private void fetchDayChallenges() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("exercise")
                .child(exerciseName)
                .child("dayChallenges");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                challengeDataList.clear(); // Clear the list to avoid duplication
                for (DataSnapshot daySnapshot : snapshot.getChildren()) {
                    String dayNum = daySnapshot.child("DayNum").getValue(String.class);
                    String dayDesc = daySnapshot.child("DayDesc").getValue(String.class);
                    String exerciseMove = daySnapshot.child("ExerciseMove").getValue(String.class);

                    // Parse ExerciseDetails
                    List<ChallengeMoveDetails> exerciseDetails = new ArrayList<>();

                    DataSnapshot exerciseDetailsSnapshot = daySnapshot.child("ExerciseDetails");
                    if (exerciseDetailsSnapshot.exists()) {
                        for (DataSnapshot moveSnapshot : exerciseDetailsSnapshot.getChildren()) {
                            String moveName = moveSnapshot.child("moveName").getValue(String.class);
                            String duration = moveSnapshot.child("duration").getValue(String.class);
                            String imageUrl = moveSnapshot.child("imageUrl").getValue(String.class);

                            // Handle potential nulls
                            moveName = (moveName != null) ? moveName : "No Move Name";
                            duration = (duration != null) ? duration : "No Duration";
                            imageUrl = (imageUrl != null) ? imageUrl : "";

                            exerciseDetails.add(new ChallengeMoveDetails(moveName, duration, imageUrl));
                        }
                    }

                    // Handle ExerciseMove
                    String exerciseMoveUrl = (exerciseMove != null) ? exerciseMove : "";

                    // Create ChallengeData object
                    ChallengeData challengeData = new ChallengeData(dayNum, dayDesc, exerciseMoveUrl, exerciseDetails);
                    challengeDataList.add(challengeData);
                }
                challengeAdapter.notifyDataSetChanged(); // Update RecyclerView
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FirebaseError", "Failed to read data", error.toException());
                Toast.makeText(weekChallenge.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
