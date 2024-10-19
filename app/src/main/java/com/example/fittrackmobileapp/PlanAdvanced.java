package com.example.fittrackmobileapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlanAdvanced extends AppCompatActivity {
    RecyclerView recyclerView;
    ExerciseAdapter adapter;
    List<ExerciseItem> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_with_me);
    }
}
