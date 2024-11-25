package com.example.fittrackmobileapp;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthday;
    private String gender;
    private String weight;
    private String height;
    private List<Boolean> fitnessGoals;

    // Default constructor for Firebase
    public User() {}

    // Full constructor
    public User(String firstName, String lastName, String email, String password, String birthday,
                String gender, String weight, String height, List<Boolean> fitnessGoals) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.fitnessGoals = fitnessGoals;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getBirthday() { return birthday; }
    public String getGender() { return gender; }
    public String getWeight() { return weight; }
    public String getHeight() { return height; }
    public List<Boolean> getFitnessGoals() { return fitnessGoals; }
    public void setFitnessGoals(List<Boolean> fitnessGoals) { this.fitnessGoals = fitnessGoals; }
}
