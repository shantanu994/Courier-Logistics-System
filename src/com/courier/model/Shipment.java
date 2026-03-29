package com.courier.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Shipment {

    private int       shipmentId;
    private String    trackingNumber;
    private int       senderId;
    private int       receiverId;
    private int       courierId;
    private String    originCity;
    private String    destinationCity;
    private double    weight;
    private String    packageType;   // DOCUMENT, PARCEL, FRAGILE, HEAVY
    private String    status;        // PENDING, PICKED_UP, IN_TRANSIT, etc.
    private Date      estimatedDelivery;
    private Date      actualDelivery;
    private Timestamp createdAt;

    // no-arg constructor
    public Shipment() {}

    // parameterized constructor (without auto fields)
    public Shipment(String trackingNumber, int senderId, int receiverId,
                    int courierId, String originCity, String destinationCity,
                    double weight, String packageType,
                    String status, Date estimatedDelivery) {
        this.trackingNumber    = trackingNumber;
        this.senderId          = senderId;
        this.receiverId        = receiverId;
        this.courierId         = courierId;
        this.originCity        = originCity;
        this.destinationCity   = destinationCity;
        this.weight            = weight;
        this.packageType       = packageType;
        this.status            = status;
        this.estimatedDelivery = estimatedDelivery;
    }

    // getters
    public int       getShipmentId()        { return shipmentId; }
    public String    getTrackingNumber()     { return trackingNumber; }
    public int       getSenderId()           { return senderId; }
    public int       getReceiverId()         { return receiverId; }
    public int       getCourierId()          { return courierId; }
    public String    getOriginCity()         { return originCity; }
    public String    getDestinationCity()    { return destinationCity; }
    public double    getWeight()             { return weight; }
    public String    getPackageType()        { return packageType; }
    public String    getStatus()             { return status; }
    public Date      getEstimatedDelivery()  { return estimatedDelivery; }
    public Date      getActualDelivery()     { return actualDelivery; }
    public Timestamp getCreatedAt()          { return createdAt; }

    // setters
    public void setShipmentId(int shipmentId)               { this.shipmentId = shipmentId; }
    public void setTrackingNumber(String trackingNumber)     { this.trackingNumber = trackingNumber; }
    public void setSenderId(int senderId)                   { this.senderId = senderId; }
    public void setReceiverId(int receiverId)               { this.receiverId = receiverId; }
    public void setCourierId(int courierId)                 { this.courierId = courierId; }
    public void setOriginCity(String originCity)             { this.originCity = originCity; }
    public void setDestinationCity(String destinationCity)   { this.destinationCity = destinationCity; }
    public void setWeight(double weight)                     { this.weight = weight; }
    public void setPackageType(String packageType)           { this.packageType = packageType; }
    public void setStatus(String status)                     { this.status = status; }
    public void setEstimatedDelivery(Date estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
    public void setActualDelivery(Date actualDelivery)       { this.actualDelivery = actualDelivery; }
    public void setCreatedAt(Timestamp createdAt)            { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return trackingNumber + " | " + originCity +
               " → " + destinationCity + " [" + status + "]";
    }
}