package com.example.cse213_finalproject_group65_flighttrainingacademy.GroundInstructor;

import java.time.LocalDate;

public class ClassSession {
    private String courseId;
    private String topic;
    private LocalDate date;
    private String time;
    public ClassSession(String courseId, String topic, LocalDate date, String time){

        this.courseId = courseId;
        this.topic = topic;
        this.date = date;
        this.time = time;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTopic() {
        return topic;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
