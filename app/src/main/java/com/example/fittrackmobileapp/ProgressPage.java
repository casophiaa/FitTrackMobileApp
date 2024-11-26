package com.example.fittrackmobileapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgressPage extends AppCompatActivity{

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private List<String> xValues = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

    RecyclerView horizontalRv;
    ArrayList<ProgressItem> dataSource;
    GridLayoutManager gridLayoutManager;
    ProgAdapter progAdapter;
    ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // For the weekly steps count
        BarChart barChart = findViewById(R.id.progressSteps);
        barChart.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0,100f));
        entries.add(new BarEntry(1,100f));
        entries.add(new BarEntry(2,100f));
        entries.add(new BarEntry(3,100f));
        entries.add(new BarEntry(4,100f));
        entries.add(new BarEntry(5,100f));
        entries.add(new BarEntry(6,100f));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(100f);
        yAxis.setAxisMaximum(5000f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet dataSet = new BarDataSet(entries, "Daily Steps");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1F);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getDescription().setText(".");

        // For progress capture
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
