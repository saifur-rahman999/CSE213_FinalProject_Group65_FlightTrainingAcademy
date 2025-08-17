package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

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

    /** Legacy helper: append a line; swallows exceptions. */
    public static void appendLine(String txtPath, String line) {
        try {
            appendLineThrow(txtPath, line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** NEW: Append a line, but THROW on error so the UI can show a proper alert. */
    public static void appendLineThrow(String txtPath, String line) throws IOException {
        Path p = Path.of(txtPath);
        ensureParentDirs(p);
        Files.writeString(p, line + System.lineSeparator(), StandardCharsets.UTF_8, CREATE, APPEND);
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

    private static void ensureParentDirs(Path p) throws IOException {
        Path parent = p.toAbsolutePath().getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }
}
