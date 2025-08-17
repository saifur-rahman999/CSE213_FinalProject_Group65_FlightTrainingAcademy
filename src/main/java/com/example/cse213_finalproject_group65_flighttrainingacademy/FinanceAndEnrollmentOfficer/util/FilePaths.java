package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.util;

public final class FilePaths {
    private FilePaths() {}

    public static final String INVOICES_BIN    = "invoices.bin";
    public static final String PAYMENTS_BIN    = "payments.bin";
    public static final String APPROVALS_TXT   = "approvals.txt";   // lines: approverId,invoiceId,YYYY-MM-DD,note
    public static final String REMINDERS_TXT   = "reminders.txt";   // append reminder address lines
    public static final String DISPUTES_TXT    = "disputes.txt";    // append resolution records

    // NEW for FEO-1 (writes to .txt, no table)
    public static final String APPLICATIONS_TXT = "data/applications.txt";
}
