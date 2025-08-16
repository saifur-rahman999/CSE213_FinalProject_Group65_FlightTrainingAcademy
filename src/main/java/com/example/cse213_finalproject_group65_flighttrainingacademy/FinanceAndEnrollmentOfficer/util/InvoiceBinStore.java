package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util;


import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class InvoiceBinStore {
    private InvoiceBinStore() {}

    public static File chooseBinFile(Window owner) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select/Create Invoices File");
        fc.setInitialFileName("invoices.bin"); // suggest invoices.bin
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary files (*.bin)", "*.bin"));
        return fc.showSaveDialog(owner); // create new OR overwrite existing; also allows picking existing
    }

    @SuppressWarnings("unchecked")
    public static List<Invoice> readAll(File file) throws IOException {
        if (file == null || !file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) return (List<Invoice>) obj;
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            throw new IOException("Stored data has unknown type.", e);
        }
    }

    public static void writeAll(File file, List<Invoice> invoices) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(invoices);
        }
    }
}
