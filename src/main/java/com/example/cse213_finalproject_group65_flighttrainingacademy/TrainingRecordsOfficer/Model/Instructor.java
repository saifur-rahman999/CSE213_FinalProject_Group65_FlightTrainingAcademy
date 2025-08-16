package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private String id;
    private String name;
    private List<Rating> ratings = new ArrayList<>();

    public Instructor() {}
    public Instructor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Rating> getRatings() { return ratings; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRatings(List<Rating> ratings) { this.ratings = ratings; }
}
