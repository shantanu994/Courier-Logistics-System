package com.courier.dao;

import com.courier.db.DBConnection;
import com.courier.model.Shipment;
import com.courier.util.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class ShipmentDAOImpl implements ShipmentDAO {

    private Shipment mapRow(ResultSet rs) throws SQLException {
        Shipment s = new Shipment(
            rs.getString("tracking_number"),
            rs.getInt("sender_id"),
            rs.getInt("receiver_id"),
            rs.getInt("courier_id"),
            rs.getString("origin_city"),
            rs.getString("destination_city"),
            rs.getDouble("weight"),
            rs.getString("package_type"),
            rs.getString("status"),
            rs.getDate("estimated_delivery")
        );
        s.setShipmentId(rs.getInt("shipment_id"));
        s.setActualDelivery(rs.getDate("actual_delivery"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        return s;
    }

    @Override
    public void addShipment(Shipment s) throws DatabaseException {
        String sql = "INSERT INTO shipments (tracking_number, sender_id, receiver_id, "
                   + "courier_id, origin_city, destination_city, weight, "
                   + "package_type, status, estimated_delivery) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getTrackingNumber());
            ps.setInt(2,    s.getSenderId());
            ps.setInt(3,    s.getReceiverId());
            ps.setInt(4,    s.getCourierId());
            ps.setString(5, s.getOriginCity());
            ps.setString(6, s.getDestinationCity());
            ps.setDouble(7, s.getWeight());
            ps.setString(8, s.getPackageType());
            ps.setString(9, s.getStatus());
            ps.setDate(10,  s.getEstimatedDelivery());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add shipment: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Shipment> getAllShipments() throws DatabaseException {
        ArrayList<Shipment> list = new ArrayList<>();
        String sql = "SELECT * FROM shipments ORDER BY created_at DESC";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch shipments: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Shipment getShipmentByTracking(String trackingNumber) throws DatabaseException {
        String sql = "SELECT * FROM shipments WHERE tracking_number = ?";
        Shipment s = null;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, trackingNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) s = mapRow(rs);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch shipment: " + e.getMessage(), e);
        }
        return s;
    }

    @Override
    public ArrayList<Shipment> getShipmentsByStatus(String status) throws DatabaseException {
        ArrayList<Shipment> list = new ArrayList<>();
        String sql = "SELECT * FROM shipments WHERE status = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch shipments by status: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void updateStatus(int shipmentId, String status) throws DatabaseException {
        String sql = "UPDATE shipments SET status = ? WHERE shipment_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, shipmentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update status: " + e.getMessage(), e);
        }
    }

    @Override
    public void assignCourier(int shipmentId, int courierId) throws DatabaseException {
        String sql = "UPDATE shipments SET courier_id = ? WHERE shipment_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courierId);
            ps.setInt(2, shipmentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to assign courier: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteShipment(int id) throws DatabaseException {
        String sql = "DELETE FROM shipments WHERE shipment_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete shipment: " + e.getMessage(), e);
        }
    }
}