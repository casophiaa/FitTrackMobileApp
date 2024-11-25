package com.example.fittrackmobileapp;

import java.util.List;

public class ChallengeData {
    private String dayNum;
    private String dayDesc;
    private String exerciseMove;
    private List<ChallengeMoveDetails> exerciseDetails;

    // Default constructor required for calls to DataSnapshot.getValue
    public ChallengeData() {}

    // Parameterized constructor
    public ChallengeData(String dayNum, String dayDesc, String exerciseMove, List<ChallengeMoveDetails> exerciseDetails) {
        this.dayNum = dayNum;
        this.dayDesc = dayDesc;
        this.exerciseMove = exerciseMove;
        this.exerciseDetails = exerciseDetails;
    }

    // Getters and Setters
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

    public String getExerciseMove() {
        return exerciseMove;
    }

    public void setExerciseMove(String exerciseMove) {
        this.exerciseMove = exerciseMove;
    }

    public List<ChallengeMoveDetails> getExerciseDetails() {
        return exerciseDetails;
    }

    public void setExerciseDetails(List<ChallengeMoveDetails> exerciseDetails) {
        this.exerciseDetails = exerciseDetails;
    }
}
