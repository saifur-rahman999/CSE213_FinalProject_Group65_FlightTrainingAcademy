package com.example.cse213_finalproject_group65_flighttrainingacademy.AtcCoordinator.model;

import java.time.LocalDate;

public class AssignedSchedule {
    private final String empId;
    private final String scheduleId;
    private LocalDate assignmentDate;

    public AssignedSchedule(String empId, String scheduleId, LocalDate assignmentDate) {
        this.empId = empId;
        this.scheduleId = scheduleId;
        this.assignmentDate = assignmentDate;
    }
}
