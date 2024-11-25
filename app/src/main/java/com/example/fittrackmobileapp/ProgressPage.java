package com.example.fittrackmobileapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProgressPage extends AppCompatActivity{

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    RecyclerView horizontalRv;
    ArrayList<ProgressItem> dataSource;
    GridLayoutManager gridLayoutManager;
    ProgAdapter progAdapter;
    ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        horizontalRv = findViewById(R.id.horizontalRv);
        dataSource = new ArrayList<>();
        Button btnCapture = findViewById(R.id.btnPhoto);

        gridLayoutManager = new GridLayoutManager(this, 2);
        progAdapter = new ProgAdapter(dataSource);
        horizontalRv.setLayoutManager(gridLayoutManager);
        horizontalRv.setAdapter(progAdapter);

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    dataSource.add(new ProgressItem(currentDate, imageBitmap));
                    // progAdapter.notifyDataSetChanged();
                }
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    takePictureLauncher.launch(takePictureIntent);
                } else {
                    Toast.makeText(ProgressPage.this, "there is no app that supports this action", Toast.LENGTH_SHORT).show();
                }
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

    public void dashboard(View v) {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

}
