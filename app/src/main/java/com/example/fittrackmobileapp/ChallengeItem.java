package com.example.fittrackmobileapp;

public class ChallengeItem {

    int imageSource;
    String day, exercise, desc;

    public ChallengeItem(String day, String exercise, String desc, int imageSource){
        this.day = day;
        this.exercise =exercise;
        this.desc = desc;
        this.imageSource = imageSource;
    }

    public String getDay(){
        return day;
    }

    public String getExercise(){
        return exercise;
    }

    public String getDesc(){
        return desc;
    }

    public int getImageSource(){
        return imageSource;
    }
}
