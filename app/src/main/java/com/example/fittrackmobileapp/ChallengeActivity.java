package com.example.fittrackmobileapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChallengeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChallengeMoveAdapter adapter;
    private List<ChallengeMoveDetails> exerciseList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        ImageView exerciseMoveImage = findViewById(R.id.imageview);
        TextView textDayNum = findViewById(R.id.textDayNum);
        TextView textDayDesc = findViewById(R.id.textDayDesc);

        Intent i = getIntent();
        exerciseList = i.getParcelableArrayListExtra("exerciseDetails");

        String imageUrl = i.getStringExtra("image"); // Retrieve URL
        Glide.with(this)
                .load(imageUrl)
                .into(exerciseMoveImage);
        textDayNum.setText(i.getStringExtra("DayNum"));
        textDayDesc.setText(i.getStringExtra("DayDesc"));

        // Set up RecyclerView
        recyclerView = findViewById(R.id.exerciseRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass data to adapter
        adapter = new ChallengeMoveAdapter(exerciseList, this);
        recyclerView.setAdapter(adapter);

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