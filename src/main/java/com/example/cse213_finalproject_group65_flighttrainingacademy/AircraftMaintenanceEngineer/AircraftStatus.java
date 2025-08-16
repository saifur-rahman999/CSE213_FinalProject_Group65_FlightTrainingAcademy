package com.example.cse213_finalproject_group65_flighttrainingacademy.AircraftMaintenanceEngineer;

import java.time.LocalDate;

public class AircraftStatus {
    public class AircraftStatus {
        private final String aircraftId;
        private final String status;
        private final LocalDate date;

        public AircraftStatus(String aircraftId, String status, LocalDate date) {
            this.aircraftId = aircraftId;
            this.status = status;
            this.date = date;
        }

        public String getAircraftId() {
            return aircraftId;
        }

        public String getStatus() {
            return status;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}