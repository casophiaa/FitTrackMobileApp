package com.example.fittrackmobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class StepCount extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor stepSensor;
    private int totalSteps = 0;
    private int previewsTotalSteps = 0;
    private ProgressBar progressBar;
    private TextView steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step_count);

        progressBar = findViewById(R.id.progressBar);
        steps = findViewById(R.id.stepsCount);

        progressBar.setMax(6000); // Set goal to 6000 steps

        resetSteps();
        loadData();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
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
            totalSteps = (int) event.values[0];
            int currentSteps = totalSteps - previewsTotalSteps;

            steps.setText(String.valueOf(currentSteps));
            progressBar.setProgress(currentSteps);

            // Debugging logs
            Log.d("StepCounter", "Total steps: " + totalSteps);
            Log.d("StepCounter", "Previous steps: " + previewsTotalSteps);
            Log.d("StepCounter", "Current steps: " + currentSteps);
        }
    }

    private void resetSteps() {
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StepCount.this, "Long press to reset steps", Toast.LENGTH_SHORT).show();
            }
        });

        steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                previewsTotalSteps = totalSteps;
                steps.setText("0");
                progressBar.setProgress(0);
                saveData();
                return true;
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("key1", previewsTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        previewsTotalSteps = sharedPref.getInt("key1", 0); // Load the saved step count correctly
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
