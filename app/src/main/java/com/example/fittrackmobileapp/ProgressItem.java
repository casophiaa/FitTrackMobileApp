package com.example.fittrackmobileapp;

import android.graphics.Bitmap;

public class ProgressItem {
    private String title;
    private Bitmap bitmap;

    public ProgressItem(String title, Bitmap bitmap) {
        this.title = title;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setImage(Bitmap bitmap){
        this.bitmap = bitmap;
    }
}

