package com.example.fittrackmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlanPage extends AppCompatActivity{

    RecyclerView recyclerView;
    ExerciseAdapter adapter;
    List<ExerciseItem> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
    }

    public void beginnerLevel(View v) {
        Intent i = new Intent(this, PlanBeginner.class);
        startActivity(i);
    }

    public void intermediateLevel(View v) {
        Intent i = new Intent(this, PlanIntermediate.class);
        startActivity(i);
    }

    public void advancedLevel(View v) {
        Intent i = new Intent(this, PlanAdvanced.class);
        startActivity(i);
    }

    public void customExercise(View v) {

    }

    public void workoutSuggestion(View v) {
        Intent i = new Intent(this, PlanPage.class);
        startActivity(i);
    }

    public void checkProgress(View v) {
        Intent i = new Intent(this, ProgressPage.class);
        startActivity(i);
    }

    public void dashboard(View v) {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }
}
