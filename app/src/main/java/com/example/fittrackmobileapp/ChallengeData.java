package com.example.fittrackmobileapp;

public class ChallengeData {

    private String dayNum;
    private String dayDesc;
    private Integer exerciseMove;
    private String exerciseDetails;

    public ChallengeData(String dayNum, String dayDesc, Integer exerciseMove, String exerciseDetails) {
        this.dayNum = dayNum;
        this.dayDesc = dayDesc  ;
        this.exerciseMove = exerciseMove;
        this.exerciseDetails = exerciseDetails;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getDayDesc() {
        return dayDesc;
    }

    public void setDayDesc(String dayDesc) {
        this.dayDesc = dayDesc;
    }

    public Integer getExerciseMove() {
        return exerciseMove;
    }

    public void setExerciseMove(Integer exerciseMove) {
        this.exerciseMove = exerciseMove;
    }

    public String getExerciseDetails() {
        return this.exerciseDetails;
    }

    public void setExerciseDetails(String exerciseDetails){
        this.exerciseDetails = exerciseDetails;
    }

}
