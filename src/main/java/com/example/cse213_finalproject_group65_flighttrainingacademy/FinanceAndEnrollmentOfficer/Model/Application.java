package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;



/**
 * Plain model for an application. NOTE: This class name can clash with
 * javafx.application.Application if you auto-import the wrong one.
 * In controllers, either import this explicitly or refer to it with full name.
 */
public class Application {
    private final int id;
    private final String name;
    private final String email;
    private final String intakeMonth; // e.g. "Sep 2025"
    private final String course;      // e.g. "PPL"
    private final ApplicationStatus status;

    public Application(int id, String name, String email, String intakeMonth, String course, ApplicationStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.intakeMonth = intakeMonth;
        this.course = course;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getIntakeMonth() { return intakeMonth; }
    public String getCourse() { return course; }
    public ApplicationStatus getStatus() { return status; }

    /** Serialize to a robust pipe-delimited line for applications.txt */
    public String toLine() {
        String safeName   = sanitize(name);
        String safeEmail  = sanitize(email);
        String safeIntake = sanitize(intakeMonth);
        String safeCourse = sanitize(course);
        return id + "|" + safeName + "|" + safeEmail + "|" + safeIntake + "|" + safeCourse + "|" + status;
    }

    /** Parse from a line (lenient). Returns null if badly formatted. */
    public static Application fromLine(String line) {
        if (line == null) return null;
        String[] p = line.split("\\|", -1);
        if (p.length < 6) return null;
        try {
            int id = Integer.parseInt(p[0].trim());
            String name = p[1];
            String email = p[2];
            String intake = p[3];
            String course = p[4];
            ApplicationStatus status = ApplicationStatus.valueOf(p[5].trim());
            return new Application(id, name, email, intake, course, status);
        } catch (Exception e) {
            return null;
        }
    }

    private static String sanitize(String s) {
        if (s == null) return "";
        return s.replace("|", "/").replace("\r", " ").replace("\n", " ").trim();
    }

    @Override
    public String toString() {
        return "#" + id + " - " + name + " (" + email + "), " + course + " @ " + intakeMonth + " [" + status + "]";
    }
}
