package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;


import java.io.*;
import java.util.*;

public class RosterService {
    // Expected optional file: "session_roster_<sessionId>.bin" (ArrayList<RosterEntry>)
    public List<RosterEntry> fetchRoster(int sessionId) {
        File f = new File("session_roster_" + sessionId + ".bin");
        if (!f.exists()) {
            // demo fallback
            ArrayList<RosterEntry> demo = new ArrayList<>();
            demo.add(new RosterEntry(230001, "A. Rahman"));
            demo.add(new RosterEntry(230002, "B. Khatun"));
            demo.add(new RosterEntry(230003, "C. Uddin"));
            return demo;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?> list) {
                @SuppressWarnings("unchecked")
                ArrayList<RosterEntry> typed = (ArrayList<RosterEntry>) list;
                return typed;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Set<Integer> validIdSetForSession(int sessionId) {
        List<RosterEntry> roster = fetchRoster(sessionId);
        Set<Integer> ids = new HashSet<>();
        for (RosterEntry r : roster) ids.add(r.getTraineeId());
        return ids;
    }
}
