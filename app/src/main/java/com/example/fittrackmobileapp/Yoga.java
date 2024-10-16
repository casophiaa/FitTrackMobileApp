package com.example.fittrackmobileapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Yoga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);

        Button button30DayChallenge = findViewById(R.id.button30DayChallenge);
        Button buttonNthMinute = findViewById(R.id.buttonNthMinute);

        button30DayChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 30 Day Challenge selection
            }
        });

        buttonNthMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Nth Minute Workout selection
            }
        });
    }
}
