package com.example.fittrackmobileapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetails extends AppCompatActivity {

    private VideoView mVideoView;
    private String exerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        exerciseName = getIntent().getStringExtra("EXERCISE_NAME");

        Button button30DayChallenge = findViewById(R.id.button30DayChallenge);
        Button buttonNthMinute = findViewById(R.id.buttonNthMinute);

        mVideoView = findViewById(R.id.videoView);
        setupVideoView();

        button30DayChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 30 Day Challenge selection
            }
        });

        buttonNthMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 30 Day Challenge selection

            }
        });
    }

    private void setupVideoView() {
        int videoResId = 0;
        switch (exerciseName) {
            case "Yoga":
                videoResId = R.raw.yogavid;
                break;
            case "Pilates":
                videoResId = R.raw.pilatesvid;
                break;
            case "Weight Loss":
                videoResId = R.raw.weightlossvid;
                break;
            case "Muscle Building":
                videoResId = R.raw.musclebuildingvid;
                break;
            case "Endurance Training":
                videoResId = R.raw.endurancevid;
                break;
            case "HIIT":
                videoResId = R.raw.hiitvid;
                break;
        }

        if (videoResId != 0) {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
            mVideoView.setVideoURI(uri);
            mVideoView.start();
            mVideoView.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
        }
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
