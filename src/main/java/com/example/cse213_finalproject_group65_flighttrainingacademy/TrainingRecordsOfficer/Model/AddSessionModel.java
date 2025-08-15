package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AddSessionModel implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private LocalDate date;
    private String type;            // "FLIGHT" or "SIM"
    private String aircraftOrDevice;
    private double durationHours;
    private String traineeId;
    private String instructorId;
    private String remarks;

    public AddSessionModel(LocalDate date, String type, String aircraftOrDevice,
                           double durationHours, String traineeId, String instructorId, String remarks) {
        this.date = Objects.requireNonNull(date, "date");
        this.type = Objects.requireNonNull(type, "type");
        this.aircraftOrDevice = Objects.requireNonNull(aircraftOrDevice, "aircraftOrDevice");
        this.durationHours = durationHours; // validated in controller/service
        this.traineeId = Objects.requireNonNull(traineeId, "traineeId");
        this.instructorId = Objects.requireNonNull(instructorId, "instructorId");
        this.remarks = remarks == null ? "" : remarks.trim();
        if (this.remarks.length() > 500) { // soft cap
            this.remarks = this.remarks.substring(0, 500);
        }
    }

    public static AddSessionModel of(LocalDate date, String type, String aircraftOrDevice,
                                     double durationHours, String traineeId, String instructorId, String remarks) {
        return new AddSessionModel(date, type, aircraftOrDevice, durationHours, traineeId, instructorId, remarks);
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = Objects.requireNonNull(date); }

    public String getType() { return type; }
    public void setType(String type) { this.type = Objects.requireNonNull(type); }

    public String getAircraftOrDevice() { return aircraftOrDevice; }
    public void setAircraftOrDevice(String aircraftOrDevice) { this.aircraftOrDevice = Objects.requireNonNull(aircraftOrDevice); }

    public double getDurationHours() { return durationHours; }
    public void setDurationHours(double durationHours) { this.durationHours = durationHours; }

    public String getTraineeId() { return traineeId; }
    public void setTraineeId(String traineeId) { this.traineeId = Objects.requireNonNull(traineeId); }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = Objects.requireNonNull(instructorId); }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks == null ? "" : remarks.trim(); }

    @Override public String toString() {
        return String.format("[%s] %s (%s) - %.1f hrs", date, type, traineeId, durationHours);
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddSessionModel that)) return false;
        return Double.compare(that.durationHours, durationHours) == 0
                && Objects.equals(date, that.date)
                && Objects.equals(type, that.type)
                && Objects.equals(aircraftOrDevice, that.aircraftOrDevice)
                && Objects.equals(traineeId, that.traineeId)
                && Objects.equals(instructorId, that.instructorId)
                && Objects.equals(remarks, that.remarks);
    }
    @Override public int hashCode() {
        return Objects.hash(date, type, aircraftOrDevice, durationHours, traineeId, instructorId, remarks);
    }
}
