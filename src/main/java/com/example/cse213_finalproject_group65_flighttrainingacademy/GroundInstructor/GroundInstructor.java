package com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor;

import com.example.cse213_finalproject_group65_flighttrainingacademy.User;

import java.time.LocalDate;

public class GroundInstructor extends User {



    public GroundInstructor(String id, String name, String phoneNo, String email, String address, String gender, String password, LocalDate dob) {
        super(id, name, phoneNo, email, address, gender, password, dob);
    }
}
