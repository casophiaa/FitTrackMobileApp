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
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    RecyclerView horizontalRv;
    ArrayList<ProgFeatItem> dataSource;
    LinearLayoutManager linearLayoutManager;
    DashAdapter dashAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
    }

    public void trackMyWorkout(View v) {
        Intent i = new Intent(Dashboard.this, TrackMyWorkout.class);
        startActivity(i);
    }

    public void exerciseWithMe(View v) {
        Intent i = new Intent(Dashboard.this, ExerciseWithMe.class);
        startActivity(i);
    }
}
