package com.example.fittrackmobileapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView timerTextView;
    private Button startButton, pauseButton, doneButton;
    private Handler timerHandler = new Handler();
    private int timeElapsed = 0; // In seconds
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        videoView = findViewById(R.id.videoView);
        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        doneButton = findViewById(R.id.doneButton);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.workvid);
        videoView.setVideoURI(videoUri);

        startButton.setOnClickListener(v -> startWorkout());
        pauseButton.setOnClickListener(v -> pauseWorkout());
        doneButton.setOnClickListener(v -> doneWorkout());

        ImageView exerciseMoveImage = findViewById(R.id.imageview);
        TextView textDayNum = findViewById(R.id.textDayNum);
        TextView textDayDesc = findViewById(R.id.textDayDesc);
        TextView textExerciseDets = findViewById(R.id.textsummary);

        Intent i = getIntent();
        exerciseMoveImage.setImageResource(i.getIntExtra("image", R.drawable.yogaone));
        textDayNum.setText(i.getStringExtra("dayNum"));
        textDayDesc.setText(i.getStringExtra("dayDesc"));
        textExerciseDets.setText(i.getStringExtra("exerciseDets"));

    }

    private void startWorkout() {
        if (!isPlaying) {
            videoView.start();
            isPlaying = true;
            timerHandler.postDelayed(updateTimer, 1000);
        }
    }

    private void pauseWorkout() {
        if (isPlaying) {
            videoView.pause();
            isPlaying = false;
            timerHandler.removeCallbacks(updateTimer);
        }
    }

    private void doneWorkout() {
        videoView.stopPlayback();
        timerHandler.removeCallbacks(updateTimer);
        timeElapsed = 0;

        Toast.makeText(ChallengeActivity.this, "Well Done! See you again tomorrow!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ExerciseWithMe.class);
        startActivity(intent);
        finish();
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeElapsed++;
            int minutes = timeElapsed / 60;
            int seconds = timeElapsed % 60;
            timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 1000);
        }
    };

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