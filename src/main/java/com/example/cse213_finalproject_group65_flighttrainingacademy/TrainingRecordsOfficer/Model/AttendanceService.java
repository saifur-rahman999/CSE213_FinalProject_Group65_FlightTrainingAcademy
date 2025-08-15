package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import java.io.*;
import java.util.List;

public class AttendanceService {
    private static final String FILE = "attendance.bin";

    public void appendAll(List<AttendanceRecord> records) throws IOException {
        File f = new File(FILE);
        boolean append = f.exists() && f.length() > 0;

        try (FileOutputStream fos = new FileOutputStream(f, true);
             ObjectOutputStream oos = append ? new AppendableObjectOutputStream(fos)
                     : new ObjectOutputStream(fos)) {
            for (AttendanceRecord r : records) oos.writeObject(r);
        }
    }
}
