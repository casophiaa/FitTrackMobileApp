package com.example.fittrackmobileapp;

public class ExerciseItem {

    private String name;
    private int imageResource;

    public ExerciseItem(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
