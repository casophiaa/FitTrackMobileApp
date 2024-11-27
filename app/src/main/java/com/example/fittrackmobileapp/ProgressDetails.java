package com.example.fittrackmobileapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressDetails extends AppCompatActivity {

    private Drawable wholePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog_details);


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
