package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Model for a payment line persisted in payments.bin */
public class PaymentModel {
    public final String payId;
    public final String invId;
    public final double amount;
    public final String method;   // Bkash/Card/Cash
    public final LocalDate date;
    public final String refNo;

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PaymentModel(String payId, String invId, double amount, String method, LocalDate date, String refNo) {
        this.payId = payId;
        this.invId = invId;
        this.amount = amount;
        this.method = method == null ? "" : method.trim();
        this.date = date;
        this.refNo = refNo == null ? "" : refNo.trim();
    }

    /** payments.bin record: payId|invId|amount|method|date|refNo */
    public String toRecord() {
        return String.join("|",
                payId,
                invId,
                String.format(Locale.US, "%.2f", amount),
                method,
                date.format(DF),
                refNo
        );
    }

    public static PaymentModel fromRecord(String line) {
        if (line == null || line.isEmpty()) return null;
        String[] p = line.split("\\|");
        if (p.length < 6) return null;
        try {
            return new PaymentModel(
                    p[0].trim(),
                    p[1].trim(),
                    Double.parseDouble(p[2].trim()),
                    p[3].trim(),
                    LocalDate.parse(p[4].trim(), DF),
                    p[5].trim()
            );
        } catch (Exception ex) {
            return null;
        }
    }

    public String toReceiptText(String appId, double invoiceTotal, double newPaid, double newBalance, String newStatus) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Payment Receipt ===\n");
        sb.append("Payment ID : ").append(payId).append("\n");
        sb.append("Invoice ID : ").append(invId).append("\n");
        sb.append("Applicant  : ").append(appId == null ? "-" : appId).append("\n");
        sb.append("-----------------------\n");
        sb.append("Method     : ").append(method).append("\n");
        sb.append("Date       : ").append(date.format(DF)).append("\n");
        sb.append("Ref No     : ").append(refNo).append("\n");
        sb.append("Amount     : ").append(String.format(Locale.US, "%.2f", amount)).append("\n");
        sb.append("-----------------------\n");
        sb.append("Invoice Total : ").append(String.format(Locale.US, "%.2f", invoiceTotal)).append("\n");
        sb.append("Total Paid    : ").append(String.format(Locale.US, "%.2f", newPaid)).append("\n");
        sb.append("New Balance   : ").append(String.format(Locale.US, "%.2f", newBalance)).append("\n");
        sb.append("Invoice Status: ").append(newStatus).append("\n");
        sb.append("=======================\n");
        return sb.toString();
    }
}
