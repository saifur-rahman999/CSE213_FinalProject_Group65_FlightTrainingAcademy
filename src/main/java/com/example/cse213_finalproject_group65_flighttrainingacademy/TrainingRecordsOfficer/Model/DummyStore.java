package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyStore {
    public static final List<ClassSession> sessions = new ArrayList<>();
    public static final List<Instructor> instructors = new ArrayList<>();

    // ===== your existing code =====
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

    // ===== NEW: minimal sessions seeder for Quick Analytics =====
    public static void seedSessionsIfEmpty() {
        if (!sessions.isEmpty()) return;

        Random r = new Random(65);
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusMonths(6).withDayOfMonth(1);

        String[] traineeIds = {"TP-101", "TP-102", "TP-103", "TP-104"};
        String[] devices    = {"C172", "DA40", "A320-Sim", "B737-Sim"};
        int nextId = 1000;

        LocalDate d = start;
        while (!d.isAfter(today)) {
            // ~2 weekday sessions randomly
            if (d.getDayOfWeek().getValue() <= 5 && r.nextDouble() < 0.35) {
                SessionType type = r.nextBoolean() ? SessionType.FLIGHT : SessionType.SIMULATOR;
                double dur = round1(0.8 + r.nextInt(4) * 0.5); // 0.8, 1.3, 1.8, 2.3, 2.8
                String trainee = traineeIds[r.nextInt(traineeIds.length)];
                String eq = devices[r.nextInt(devices.length)];

                sessions.add(new ClassSession(
                        nextId++, d, type, trainee, eq, dur, ""  // uses your existing constructor
                ));
            }
            d = d.plusDays(1);
        }

        // fallback so it's never empty
        if (sessions.isEmpty()) {
            sessions.add(new ClassSession(1001, today.minusDays(10), SessionType.FLIGHT, "TP-101", "C172", 1.5, ""));
            sessions.add(new ClassSession(1002, today.minusDays(5),  SessionType.SIMULATOR,    "TP-102", "A320-Sim", 2.0, ""));
        }
    }

    private static double round1(double v) { return Math.round(v * 10.0) / 10.0; }
}
