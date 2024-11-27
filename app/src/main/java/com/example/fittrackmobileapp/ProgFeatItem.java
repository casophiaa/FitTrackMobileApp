package com.example.fittrackmobileapp;

import android.graphics.drawable.Drawable;

public class ProgFeatItem {
    private String title;
    private Drawable imageResId;

    public ProgFeatItem(String title, Drawable imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getImageResId() {
        return imageResId;
    }
}
