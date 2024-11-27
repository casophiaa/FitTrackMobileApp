package com.example.fittrackmobileapp;

import android.graphics.Bitmap;

public class ProgressItem {
    private String title;
    private String bitmap;

    // public Capture() {}

    public ProgressItem(String title, String bitmap) {
        this.title = title;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setImage(String bitmap){
        this.bitmap = bitmap;
    }
}

