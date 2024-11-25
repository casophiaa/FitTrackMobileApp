package com.example.fittrackmobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StepCount extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor stepSensor;
    private int totalSteps = 0;
    private int previousTotalSteps = 0;
    private ProgressBar progressBar;
    private TextView totalStepsView, previousStepsView, previousTimestampView, currentStepsView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private RecyclerView dailyStepsRecyclerView;
    private StepAdapter stepAdapter;
    private List<StepModel> stepList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is logged in
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not authenticated. Redirecting to login.", Toast.LENGTH_SHORT).show();
            finish(); // End this activity
            return;
        }

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI elements
        dailyStepsRecyclerView = findViewById(R.id.dailyStepsRecyclerView);
        dailyStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepAdapter = new StepAdapter(stepList);
        dailyStepsRecyclerView.setAdapter(stepAdapter);

        fetchStepsFromFirebase();

        progressBar = findViewById(R.id.progressBar);
        totalStepsView = findViewById(R.id.totalStepsCount);
        previousStepsView = findViewById(R.id.previousStepsView);
        previousTimestampView = findViewById(R.id.previousTimestampView);
        currentStepsView = findViewById(R.id.currentStepsCount);

        progressBar.setMax(6000); // Set goal to 6000 steps

        resetSteps();
        loadData();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    private void fetchStepsFromFirebase() {
        String userID = mAuth.getCurrentUser().getUid();

        if (userID != null) {
            mDatabase.child("users").child(userID).child("steps").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    stepList.clear();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String date = snapshot.getKey();
                        int stepCount = snapshot.child("stepCount").getValue(Integer.class);
                        stepList.add(new StepModel(date, stepCount));
                    }
                    stepAdapter.notifyDataSetChanged();
                } else {
                    Log.e("StepCount", "Error fetching steps", task.getException());
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (stepSensor == null) {
            Toast.makeText(this, "This device has no step sensor", Toast.LENGTH_SHORT).show();
        } else {
            mSensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (previousTotalSteps == 0) {
                previousTotalSteps = (int) event.values[0];
            }
            totalSteps = (int) event.values[0];
            int currentSteps = totalSteps - previousTotalSteps;

            // Update TextViews
            currentStepsView.setText("" + currentSteps);
            totalStepsView.setText("" + totalSteps);

            // Fetch and display previous steps and timestamp from Firebase
            fetchPreviousStepsFromFirebase(previousStepsView, previousTimestampView);

            // Save steps to Firebase
            saveStepsToFirebase(totalSteps);

            // Debugging logs
            Log.d("StepCounter", "Total steps: " + totalSteps);
            Log.d("StepCounter", "Previous steps: " + previousTotalSteps);
            Log.d("StepCounter", "Current steps: " + currentSteps);
        }
    }


    private void fetchPreviousStepsFromFirebase(TextView previousStepsView, TextView previousTimestampView) {
        String userID = mAuth.getCurrentUser().getUid();

        if (userID != null) {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            mDatabase.child("users").child(userID).child("steps").child(currentDate).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DataSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                Integer previousSteps = snapshot.child("stepCount").getValue(Integer.class);
                                String timestamp = snapshot.child("timestamp").getValue(String.class);

                                // Update TextViews separately
                                previousStepsView.setText("" + (previousSteps != null ? previousSteps : "0"));
                                previousTimestampView.setText("" + (timestamp != null ? timestamp : "--"));
                            } else {
                                previousStepsView.setText("Previous Steps: 0");
                                previousTimestampView.setText("--");
                            }
                        } else {
                            Log.e("StepCount", "Error fetching previous steps", task.getException());
                        }
                    });
        }
    }



    private void resetSteps() {
        totalStepsView.setOnClickListener(view -> Toast.makeText(StepCount.this, "Long press to reset steps", Toast.LENGTH_SHORT).show());

        totalStepsView.setOnLongClickListener(view -> {
            previousTotalSteps = totalSteps;
            totalSteps = 0;
            totalStepsView.setText("0");
            progressBar.setProgress(0);
            saveData();
            return true;
        });
    }


    private void saveData() {
        SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("key1", previousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        previousTotalSteps = sharedPref.getInt("key1", 0);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    private void saveStepsToFirebase(int steps) {
        String userID = mAuth.getCurrentUser().getUid();

        if (userID != null) {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            // Save steps and timestamp under the respective userID and date
            mDatabase.child("users").child(userID).child("steps").child(currentDate).child("stepCount").setValue(steps);
            mDatabase.child("users").child(userID).child("steps").child(currentDate).child("timestamp").setValue(currentTime)
                    .addOnSuccessListener(aVoid -> Log.d("StepCount", "Steps saved successfully for " + currentDate))
                    .addOnFailureListener(e -> Log.e("StepCount", "Failed to save steps", e));
        }
    }
}
