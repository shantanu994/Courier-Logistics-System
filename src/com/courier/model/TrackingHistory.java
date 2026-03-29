package com.courier.model;

import java.sql.Timestamp;

public class TrackingHistory {

    private int       trackingId;
    private int       shipmentId;
    private String    status;
    private String    location;
    private String    remarks;
    private Timestamp updatedAt;

    // no-arg constructor
    public TrackingHistory() {}

    // parameterized constructor
    public TrackingHistory(int shipmentId, String status,
                           String location, String remarks) {
        this.shipmentId = shipmentId;
        this.status     = status;
        this.location   = location;
        this.remarks    = remarks;
    }

    // getters
    public int       getTrackingId()  { return trackingId; }
    public int       getShipmentId()  { return shipmentId; }
    public String    getStatus()      { return status; }
    public String    getLocation()    { return location; }
    public String    getRemarks()     { return remarks; }
    public Timestamp getUpdatedAt()   { return updatedAt; }

    // setters
    public void setTrackingId(int trackingId)     { this.trackingId = trackingId; }
    public void setShipmentId(int shipmentId)     { this.shipmentId = shipmentId; }
    public void setStatus(String status)           { this.status = status; }
    public void setLocation(String location)       { this.location = location; }
    public void setRemarks(String remarks)         { this.remarks = remarks; }
    public void setUpdatedAt(Timestamp updatedAt)  { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return status + " @ " + location + " (" + updatedAt + ")";
    }
}
