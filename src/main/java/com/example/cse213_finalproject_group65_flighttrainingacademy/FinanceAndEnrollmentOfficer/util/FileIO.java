package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public final class FileIO {
    private FileIO() {}

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> readList(String binPath) {
        File f = new File(binPath);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) return (ArrayList<T>) obj;
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> boolean writeList(String binPath, ArrayList<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binPath))) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void appendLine(String txtPath, String line) {
        try {
            Files.writeString(Path.of(txtPath), line + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    Files.exists(Path.of(txtPath)) ? java.nio.file.StandardOpenOption.APPEND
                            : java.nio.file.StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean fileHasLine(String txtPath, String needle) {
        try {
            Path p = Path.of(txtPath);
            if (!Files.exists(p)) return false;
            return Files.readAllLines(p, StandardCharsets.UTF_8)
                    .stream().anyMatch(l -> l.trim().equalsIgnoreCase(needle.trim()));
        } catch (IOException e) {
            return false;
        }
    }
}
