package com.example.fittrackmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProgressPage extends AppCompatActivity{

    //for mco3
    //private ProgressBar progressBar;
    //private int CurrentProgress;

    RecyclerView horizontalRv;
    ArrayList<ProgFeatItem> dataSource;
    LinearLayoutManager linearLayoutManager;
    DashAdapter dashAdapter;

    /* Camera feature to implement in MCO3
    Button takePhoto;
    ImageView imageProfile;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // will implement fetching user action data to show progress in mco3 (i.e. steps, exercises, etc.)
        // progressBar = findViewById(R.id.progressBar);

        horizontalRv = findViewById(R.id.horizontalRv);
        String currentDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
        dataSource = new ArrayList<>();
        dataSource.add(new ProgFeatItem(currentDate, R.drawable.a));
        dataSource.add(new ProgFeatItem(currentDate, R.drawable.fimmies2));
        dataSource.add(new ProgFeatItem(currentDate, R.drawable.fimmies1));

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dashAdapter = new DashAdapter(dataSource);
        horizontalRv.setLayoutManager(linearLayoutManager);
        horizontalRv.setAdapter(dashAdapter);

        /* Take Photo Feature
        imageProfile = findViewById(R.id.image);
        takePhoto = findViewById(R.id.btnPhoto);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onCLick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        }); */
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
