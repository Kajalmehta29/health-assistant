package com.health.healthassistant.dto;

import java.time.LocalDate;

public class UserResponse {

    private String name;
    private String email;
    private int age;
    private String gender;
    private double height;
    private double weight;
    private String allergies;

    public UserResponse(String name, String email, int age,
                        String gender, double height,
                        double weight, String allergies) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
    }

    // Getters

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getAllergies() { return allergies; }
}