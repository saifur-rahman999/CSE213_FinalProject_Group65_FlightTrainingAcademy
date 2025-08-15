package com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor;

import com.example.cse213_finalproject_group65_flighttrainingacademy.User;

import java.time.LocalDate;

public class GroundInstructor extends User {
    private String specialization;

    public GroundInstructor(String id, String name, String phoneNo, String email, String address, String gender, String password, LocalDate dob, String specialization) {

        super(id, name, phoneNo, email, address, gender, password, dob);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "GroundInstructor{" +
                "specialization='" + specialization + '\'' +
                '}';
    }

    GroundInstructor gi = new GroundInstructor(
            "0005", "Vladimir Putin", "01711540789", "nazmul@gmail.com",
            "Dhaka", "Male", "ginstructor", LocalDate.of(2001, 5, 14),
            "Air Law");



}
