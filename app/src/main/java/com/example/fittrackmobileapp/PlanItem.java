package com.example.fittrackmobileapp;

public class PlanItem {
    private String name;
    private String desc;
    private int image;
    private String details;

    public PlanItem(String name, String desc, int image, String details){
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.details = details;
    }

    public String obtainName(){
        return name;
    }

    public String obtainDesc(){
        return desc;
    }

    public int obtainImage(){
        return image;
    }

    public String obtainDetails(){
        return details;
    }
}
