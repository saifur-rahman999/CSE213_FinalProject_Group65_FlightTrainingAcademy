package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SessionService {
    public List<Session> loadUpcomingAndRecentSessions() {
        // TODO: replace with real file source if you have one
        List<Session> list = new ArrayList<>();
        list.add(new Session(101, "JavaFX Basics (Intake S25)", LocalDate.now()));
        list.add(new Session(102, "OOP Review (Intake S25)", LocalDate.now().minusDays(1)));
        list.add(new Session(103, "Data Structures (Intake S25)", LocalDate.now().plusDays(1)));
        return list;
    }
}
