package com.example.fittrackmobileapp;

public class ProgFeatItem {
    private String title;
    private int imageResId;

    public ProgFeatItem(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}
