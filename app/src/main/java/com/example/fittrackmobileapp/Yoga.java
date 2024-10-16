package com.example.fittrackmobileapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Yoga extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);

        Button button30DayChallenge = findViewById(R.id.button30DayChallenge);
        Button buttonNthMinute = findViewById(R.id.buttonNthMinute);

        mVideoView = (VideoView) findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.yogavid);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

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

    public void workoutSuggestion(View v) {
        Intent i = new Intent(this, PlanPage.class);
        startActivity(i);
    }

    public void checkProgress(View v) {
        Intent i = new Intent(this, ProgressPage.class);
        startActivity(i);
    }

    public void dashboard (View v) {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }
}
