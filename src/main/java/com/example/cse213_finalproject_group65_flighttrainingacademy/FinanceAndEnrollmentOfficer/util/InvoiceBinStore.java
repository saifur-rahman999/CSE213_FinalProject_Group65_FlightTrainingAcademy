package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util;

import com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model.Invoice;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

public final class InvoiceBinStore {
    private InvoiceBinStore() {}

    public static File chooseBinFile(Window owner) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose/Confirm Invoices .bin");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary files (*.bin)", "*.bin"));
        fc.setInitialFileName("invoices.bin");
        // Use Save dialog so user can create a new file if it doesn't exist
        return fc.showSaveDialog(owner);
    }

    public static List<Invoice> readAll(File file) {
        return BinStore.loadList(file.getAbsolutePath(), Invoice.class);
    }

    public static void writeAll(File file, List<Invoice> list) {
        BinStore.saveList(file.getAbsolutePath(), list);
    }
}
