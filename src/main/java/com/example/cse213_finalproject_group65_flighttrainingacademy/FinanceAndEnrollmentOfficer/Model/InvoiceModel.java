package com.example.cse213_finalproject_group65_flighttrainingacademy.FinanceAndEnrollmentOfficer.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String invoiceId;
    private final String appId;
    private final String applicantName;
    private final String applicantEmail;

    private final BigDecimal tuition;
    private final BigDecimal lab;
    private final BigDecimal misc;

    private final BigDecimal discountPercent; // 0..100
    private final BigDecimal discountAmount;  // absolute
    private final BigDecimal tax;             // computed
    private final BigDecimal subtotal;        // tuition+lab+misc
    private final BigDecimal total;           // subtotal - discounts + tax

    private BigDecimal paid;
    private BigDecimal balance;
    private final LocalDate dueDate;

    private String status; // Pending, PaymentPending, PartiallyPaid, Paid, Canceled
    private final LocalDateTime createdAt;

    public InvoiceModel(String invoiceId, String appId, String applicantName, String applicantEmail,
                        BigDecimal tuition, BigDecimal lab, BigDecimal misc,
                        BigDecimal discountPercent, BigDecimal discountAmount,
                        BigDecimal tax, BigDecimal subtotal, BigDecimal total,
                        BigDecimal paid, BigDecimal balance,
                        LocalDate dueDate, String status, LocalDateTime createdAt) {
        this.invoiceId = invoiceId;
        this.appId = appId;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.tuition = tuition;
        this.lab = lab;
        this.misc = misc;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.tax = tax;
        this.subtotal = subtotal;
        this.total = total;
        this.paid = paid;
        this.balance = balance;
        this.dueDate = dueDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    // --- getters ---
    public String getInvoiceId() { return invoiceId; }
    public String getAppId() { return appId; }
    public String getApplicantName() { return applicantName; }
    public String getApplicantEmail() { return applicantEmail; }
    public BigDecimal getTuition() { return tuition; }
    public BigDecimal getLab() { return lab; }
    public BigDecimal getMisc() { return misc; }
    public BigDecimal getDiscountPercent() { return discountPercent; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getTax() { return tax; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTotal() { return total; }
    public BigDecimal getPaid() { return paid; }
    public BigDecimal getBalance() { return balance; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setPaid(BigDecimal paid) { this.paid = paid; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setStatus(String status) { this.status = status; }

    /** An invoice is "open" if it is not fully settled/canceled. */
    public boolean isOpen() {
        String s = status == null ? "" : status.toLowerCase();
        return s.equals("pending") || s.equals("paymentpending") || s.equals("partiallypaid");
    }
}
