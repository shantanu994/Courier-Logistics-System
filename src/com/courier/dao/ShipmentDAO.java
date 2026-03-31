package com.courier.dao;

import com.courier.model.Shipment;
import com.courier.util.DatabaseException;
import java.util.ArrayList;

public interface ShipmentDAO {
    void addShipment(Shipment shipment)                      throws DatabaseException;
    ArrayList<Shipment> getAllShipments()                     throws DatabaseException;
    Shipment getShipmentByTracking(String trackingNumber)    throws DatabaseException;
    ArrayList<Shipment> getShipmentsByStatus(String status)  throws DatabaseException;
    void updateStatus(int shipmentId, String status)         throws DatabaseException;
    void assignCourier(int shipmentId, int courierId)        throws DatabaseException;
    void deleteShipment(int id)                              throws DatabaseException;
}