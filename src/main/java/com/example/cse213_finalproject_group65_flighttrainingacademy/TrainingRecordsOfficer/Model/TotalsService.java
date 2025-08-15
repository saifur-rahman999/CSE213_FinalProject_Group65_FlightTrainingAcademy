package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalsService {
    private static final String FILE = "traineeTotals.bin";

    @SuppressWarnings("unchecked")
    private Map<Integer, TraineeTotals> loadMap() throws IOException, ClassNotFoundException {
        File f = new File(FILE);
        if (!f.exists() || f.length()==0) return new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Map<?,?> m) return (Map<Integer, TraineeTotals>) m;
        }
        return new HashMap<>();
    }

    private void saveMap(Map<Integer, TraineeTotals> map) throws IOException {
        File tmp = new File(FILE + ".tmp");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmp))) {
            oos.writeObject(map);
        }
        File f = new File(FILE);
        if (f.exists()) f.delete();
        tmp.renameTo(f);
    }

    public void applySessionRows(List<AttendanceRow> rows) throws IOException, ClassNotFoundException {
        Map<Integer, TraineeTotals> map = loadMap();
        for (AttendanceRow r : rows) {
            map.computeIfAbsent(r.getTraineeId(), TraineeTotals::new).apply(r.isPresent());
        }
        saveMap(map);
    }
}
