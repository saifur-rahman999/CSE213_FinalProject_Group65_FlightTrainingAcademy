package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinStore {

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadList(String filename, Class<T> clazz) {
        File f = new File(filename);
        if (!f.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<T>) obj;
            }
        } catch (Exception ignored) { }
        return new ArrayList<>();
        // Beginner friendly: on any error, return empty list
    }

    public static <T> void saveList(String filename, List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace(); // Simple: print; you can show Alert if you prefer
        }
    }
}
