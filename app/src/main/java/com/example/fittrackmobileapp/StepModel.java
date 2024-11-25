package com.example.fittrackmobileapp;

public class StepModel {
    private String date;
    private int stepCount;

    public StepModel() {
        // Default constructor required for calls to DataSnapshot.getValue(StepModel.class)
    }

    public StepModel(String date, int stepCount) {
        this.date = date;
        this.stepCount = stepCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
}

