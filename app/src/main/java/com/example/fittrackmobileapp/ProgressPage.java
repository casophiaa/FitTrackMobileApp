package com.example.fittrackmobileapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProgressPage extends AppCompatActivity{

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;
    private List<String> xValues = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

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
        yAxis.setLabelCount(4);

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

        gridLayoutManager = new GridLayoutManager(this, 2);
        horizontalRv.setLayoutManager(gridLayoutManager);

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    dataSource.add(new ProgressItem(currentDate, imageBitmap));
                    progAdapter = new ProgAdapter(dataSource);
                    horizontalRv.setAdapter(progAdapter);
                    progAdapter.notifyDataSetChanged();

                    uploadDataToFirebase(currentDate, imageBitmap);
                }
            }
        });

        Button btnCapture = findViewById(R.id.btnPhoto);
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

    public String convertBitmap(Bitmap pic){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);  // PNG format
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void uploadDataToFirebase(String date, Bitmap pic){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference imagesRef = database.getReference("bitmap");
        DatabaseReference dateRef = database.getReference("title");

        Map<String, Object> captureData = new HashMap<>();
        

        String base64Bitmap = convertBitmap(pic);

        DatabaseReference newImageRef = imagesRef.push();
        DatabaseReference newDateRef = dateRef.push();
        newImageRef.setValue(base64Bitmap);
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
