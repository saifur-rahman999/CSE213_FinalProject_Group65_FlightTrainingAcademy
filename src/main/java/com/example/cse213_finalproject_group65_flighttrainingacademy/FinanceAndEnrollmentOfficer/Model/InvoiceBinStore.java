package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InvoiceBinStore {
    private static final Path PATH = Paths.get("data", "invoices.bin"); // fixed location

    private InvoiceBinStore() {}

    public static Path path() { return PATH; }

    @SuppressWarnings("unchecked")
    public static List<Invoice> readAll() {
        try {
            if (Files.notExists(PATH) || Files.size(PATH) == 0) {
                return new ArrayList<>();
            }
        } catch (IOException ignored) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(PATH))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) return (List<Invoice>) obj;
        } catch (EOFException | FileNotFoundException e) {
            // empty or missing file -> return empty list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeAll(List<Invoice> list) throws IOException {
        if (PATH.getParent() != null) Files.createDirectories(PATH.getParent());
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(PATH))) {
            oos.writeObject(list);
            oos.flush();
        }
    }

    public static Optional<Invoice> findById(List<Invoice> list, long id) {
        for (Invoice inv : list) if (inv.getId() == id) return Optional.of(inv);
        return Optional.empty();
    }
}
