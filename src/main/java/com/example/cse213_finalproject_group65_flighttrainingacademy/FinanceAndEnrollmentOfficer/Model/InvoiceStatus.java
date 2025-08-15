package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


import java.io.Serializable;

public enum InvoiceStatus implements Serializable {
    Draft, PaymentPending, PartiallyPaid, Paid, Disputed, Canceled
}
