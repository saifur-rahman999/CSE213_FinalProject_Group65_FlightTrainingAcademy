package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InvoiceBinStore {
    private static final Path PATH = Paths.get("data", "invoices.bin"); // fixed file location

    private InvoiceBinStore() {}

    /** Absolute path to the backing file (useful for alerts). */
    public static Path path() { return PATH.toAbsolutePath(); }

    /** Read all invoices from data/invoices.bin. Returns empty list if file is missing/empty. */
    @SuppressWarnings("unchecked")
    public static List<Invoice> readAll() {
        try {
            // If no file or zero bytes -> treat as empty list.
            if (Files.notExists(PATH) || Files.size(PATH) == 0) {
                return new ArrayList<>();
            }
        } catch (IOException ignored) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(PATH))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Invoice>) obj;
            }
        } catch (EOFException | FileNotFoundException e) {
            // empty or missing file -> treat as empty list
        } catch (Exception e) {
            // Any deserialization problem -> start fresh instead of crashing
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /** Overwrite the file with the given list. Creates the data/ folder if needed. */
    public static void writeAll(List<Invoice> list) throws IOException {
        if (PATH.getParent() != null) {
            Files.createDirectories(PATH.getParent());
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(PATH))) {
            oos.writeObject(list);
            oos.flush();
        }
    }

    /** Utility: find an invoice by id in a list already loaded into memory. */
    public static Optional<Invoice> findById(List<Invoice> list, long id) {
        for (Invoice inv : list) {
            if (inv.getId() == id) return Optional.of(inv);
        }
        return Optional.empty();
    }
}
