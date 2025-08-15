package com.example.cse213_finalproject_group65_flighttrainingacademy.TrainingRecordsOfficer.Model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    @Override
    protected void writeStreamHeader() throws IOException {
        // do not write a new header on append
        reset();
    }
}
