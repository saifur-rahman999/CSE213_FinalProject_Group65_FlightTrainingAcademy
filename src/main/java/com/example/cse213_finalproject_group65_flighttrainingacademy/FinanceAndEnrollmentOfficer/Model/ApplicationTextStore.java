package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Tiny text-store: appends/reads lines in data/applications.txt */
public final class ApplicationTextStore {
    private ApplicationTextStore() {}

    private static final Path STORE = Paths.get("data", "applications.txt");

    private static void ensureStore() throws IOException {
        if (STORE.getParent() != null && !Files.exists(STORE.getParent())) {
            Files.createDirectories(STORE.getParent());
        }
        if (!Files.exists(STORE)) {
            Files.createFile(STORE);
        }
    }

    /** Returns next id as (max existing id + 1); starts at 1001 if empty/missing. */
    public static int nextId() throws IOException {
        ensureStore();
        int last = 1000;
        try (BufferedReader br = Files.newBufferedReader(STORE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length > 0) {
                    try {
                        last = Math.max(last, Integer.parseInt(parts[0].trim()));
                    } catch (NumberFormatException ignored) { /* skip malformed id */ }
                }
            }
        }
        return last + 1;
    }

    /** Append one application as a line. */
    public static void append(Application app) throws IOException {
        ensureStore();
        String line = app.toLine() + System.lineSeparator();
        Files.write(STORE, line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }

    /** Load all rows from file. Useful for your table reload button. */
    public static List<Application> loadAll() throws IOException {
        ensureStore();
        List<Application> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(STORE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                Application a = Application.fromLine(line);
                if (a != null) list.add(a);
            }
        }
        return list;
    }

    public static Optional<Path> storePath() {
        return Optional.of(STORE);
    }
}
