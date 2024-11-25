package com.example.fittrackmobileapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExerciseWithMe extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterExercise adapter;
    List<ItemsExercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_with_me);

        recyclerView = findViewById(R.id.recyclerView);

        exerciseList = new ArrayList<>();

        adapter = new AdapterExercise(this, exerciseList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("exercise");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exerciseList.clear();

                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {
                    String exerciseName = exerciseSnapshot.child("name").getValue(String.class);
                    String imageResource = exerciseSnapshot.child("imageResource").getValue(String.class);

                    if (exerciseName != null && imageResource != null) {
                        exerciseList.add(new ItemsExercise(exerciseName, imageResource));
                    } else {
                        Log.e("ExerciseWithMe", "Missing data for exercise: " + exerciseSnapshot.getKey());
                    }
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExerciseWithMe.this, "Failed to load exercises", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
