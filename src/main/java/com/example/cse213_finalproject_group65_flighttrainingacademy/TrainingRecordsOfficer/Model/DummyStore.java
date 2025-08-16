package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DummyStore {
    public static final List<ClassSession> sessions = new ArrayList<>();
    public static final List<Instructor> instructors = new ArrayList<>();

    public static void seedInstructors() {
        if (!instructors.isEmpty()) return;

        Instructor a = new Instructor("FI-101", "M. Rahman");
        a.getRatings().add(new Rating("CPL", LocalDate.now().plusMonths(10)));
        a.getRatings().add(new Rating("IR",  LocalDate.now().plusMonths(6)));

        Instructor b = new Instructor("FI-102", "S. Ahmed");
        b.getRatings().add(new Rating("ME",  LocalDate.now().plusMonths(12)));

        Instructor c = new Instructor("FI-103", "T. Khan");

        instructors.add(a);
        instructors.add(b);
        instructors.add(c);
    }
}
