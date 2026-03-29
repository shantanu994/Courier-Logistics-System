package com.courier.model;

import java.sql.Timestamp;

public class Bill {

    private int       billId;
    private int       shipmentId;
    private int       customerId;
    private double    baseAmount;
    private double    taxAmount;
    private double    totalAmount;
    private String    paymentStatus;  // PENDING, PAID, OVERDUE
    private String    paymentMethod;  // CASH, CARD, UPI, NET_BANKING
    private Timestamp billDate;
    private Timestamp paidDate;

    // no-arg constructor
    public Bill() {}

    // parameterized constructor
    public Bill(int shipmentId, int customerId, double baseAmount,
                double taxAmount, double totalAmount,
                String paymentStatus, String paymentMethod) {
        this.shipmentId     = shipmentId;
        this.customerId     = customerId;
        this.baseAmount     = baseAmount;
        this.taxAmount      = taxAmount;
        this.totalAmount    = totalAmount;
        this.paymentStatus  = paymentStatus;
        this.paymentMethod  = paymentMethod;
    }

    // getters
    public int       getBillId()        { return billId; }
    public int       getShipmentId()    { return shipmentId; }
    public int       getCustomerId()    { return customerId; }
    public double    getBaseAmount()    { return baseAmount; }
    public double    getTaxAmount()     { return taxAmount; }
    public double    getTotalAmount()   { return totalAmount; }
    public String    getPaymentStatus() { return paymentStatus; }
    public String    getPaymentMethod() { return paymentMethod; }
    public Timestamp getBillDate()      { return billDate; }
    public Timestamp getPaidDate()      { return paidDate; }

    // setters
    public void setBillId(int billId)                   { this.billId = billId; }
    public void setShipmentId(int shipmentId)           { this.shipmentId = shipmentId; }
    public void setCustomerId(int customerId)           { this.customerId = customerId; }
    public void setBaseAmount(double baseAmount)         { this.baseAmount = baseAmount; }
    public void setTaxAmount(double taxAmount)           { this.taxAmount = taxAmount; }
    public void setTotalAmount(double totalAmount)       { this.totalAmount = totalAmount; }
    public void setPaymentStatus(String paymentStatus)   { this.paymentStatus = paymentStatus; }
    public void setPaymentMethod(String paymentMethod)   { this.paymentMethod = paymentMethod; }
    public void setBillDate(Timestamp billDate)           { this.billDate = billDate; }
    public void setPaidDate(Timestamp paidDate)           { this.paidDate = paidDate; }

    @Override
    public String toString() {
        return "Bill#" + billId + " | ₹" + totalAmount +
               " [" + paymentStatus + "]";
    }
}