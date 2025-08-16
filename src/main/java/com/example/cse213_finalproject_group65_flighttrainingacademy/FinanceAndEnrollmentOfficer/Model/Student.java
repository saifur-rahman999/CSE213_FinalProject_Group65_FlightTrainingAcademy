package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


public class Student {
    private final int id;
    private final String name;
    private final int attendanceMissed; // days missed
    private final String grade;         // e.g., A/B/C/D/E/F

    public Student(int id, String name, int attendanceMissed, String grade) {
        this.id = id;
        this.name = name;
        this.attendanceMissed = attendanceMissed;
        this.grade = grade;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAttendanceMissed() { return attendanceMissed; }
    public String getGrade() { return grade; }

    @Override
    public String toString() {
        return id + " — " + name + " (missed: " + attendanceMissed + ", grade: " + grade + ")";
    }
}
