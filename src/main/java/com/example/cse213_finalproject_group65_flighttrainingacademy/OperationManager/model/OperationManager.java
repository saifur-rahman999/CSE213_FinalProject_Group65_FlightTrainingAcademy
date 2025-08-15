package com.example.cse213_finalproject_group65_flighttrainingacademy.OperationManager.model;

public class OperationManager {

    private String empId;
    private String name;

    public OperationManager(String empId, String name) {
        this.empId = empId;
        this.name = name;
    }

    public String getEmpId() { return empId; }
}
