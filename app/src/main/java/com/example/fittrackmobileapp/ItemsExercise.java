package com.example.fittrackmobileapp;

public class ItemsExercise {

    private String name;
    private String imageResource;

    public ItemsExercise(String name, String imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getImageResource() {
        return imageResource;
    }
}
