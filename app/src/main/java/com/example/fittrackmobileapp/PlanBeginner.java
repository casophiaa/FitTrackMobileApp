package com.example.fittrackmobileapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlanBeginner extends AppCompatActivity {
    RecyclerView recyclerView;
    ExerciseAdapter adapter;
    List<ExerciseItem> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_with_me);

        recyclerView = findViewById(R.id.recyclerView);

        exerciseList = new ArrayList<>();
        exerciseList.add(new ExerciseItem("Yoga", R.drawable.yoga));
        exerciseList.add(new ExerciseItem("Pilates", R.drawable.pilates));
        exerciseList.add(new ExerciseItem("Weight Loss", R.drawable.weightloss));
        exerciseList.add(new ExerciseItem("Muscle Building", R.drawable.muscle));
        exerciseList.add(new ExerciseItem("Endurance Training", R.drawable.endurance));
        exerciseList.add(new ExerciseItem("HIIT", R.drawable.hiit));

        adapter = new ExerciseAdapter(this, exerciseList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}
