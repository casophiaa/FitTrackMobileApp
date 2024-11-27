package com.example.fittrackmobileapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
        horizontalRv.setAdapter(progAdapter);

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    uploadDataToFirebase(imageBitmap);
                }
                getPicFromFirebase();
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

    private void getPicFromFirebase() {
        String userID = firebaseAuth.getCurrentUser().getUid();

        if (userID != null) {
            databaseReference.child("users").child(userID).child("capture").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String date = snapshot.getKey();
                        String picture = snapshot.child("capturedPic").getValue(String.class);
                        dataSource.add(new ProgressItem(date, picture));
                    }
                    progAdapter.notifyDataSetChanged();
                }else {
                    Log.e("Capture", "Error fetching Picture", task.getException());
                }
            });
        }
    }

    // added Bitmap conversion function to read the image easily
    public String convertBitmap(Bitmap pic){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);  // PNG format
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void uploadDataToFirebase(Bitmap pic){
        String userID = firebaseAuth.getCurrentUser().getUid();
        String base64Bitmap = convertBitmap(pic);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        if (userID != null) {
            databaseReference.child("users").child(userID).child("capture").child(date).child("nowDate").setValue(date);
            databaseReference.child("users").child(userID).child("capture").child(date).child("capturedPic").setValue(base64Bitmap)
                    .addOnSuccessListener(aVoid -> Log.d("Capture", "Steps saved successfully for " + date))
                    .addOnFailureListener(e -> Log.e("Capture", "Failed to save picture", e));
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
