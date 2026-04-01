package com.courier.dao;

import com.courier.db.DBConnection;
import com.courier.model.Bill;
import com.courier.util.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class BillingDAOImpl implements BillingDAO {

    private Bill mapRow(ResultSet rs) throws SQLException {
        Bill b = new Bill(
            rs.getInt("shipment_id"),
            rs.getInt("customer_id"),
            rs.getDouble("base_amount"),
            rs.getDouble("tax_amount"),
            rs.getDouble("total_amount"),
            rs.getString("payment_status"),
            rs.getString("payment_method")
        );
        b.setBillId(rs.getInt("bill_id"));
        b.setBillDate(rs.getTimestamp("bill_date"));
        b.setPaidDate(rs.getTimestamp("paid_date"));
        return b;
    }

    @Override
    public void createBill(Bill b) throws DatabaseException {
        String sql = "INSERT INTO billing (shipment_id, customer_id, base_amount, "
                   + "tax_amount, total_amount, payment_status, payment_method) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,    b.getShipmentId());
            ps.setInt(2,    b.getCustomerId());
            ps.setDouble(3, b.getBaseAmount());
            ps.setDouble(4, b.getTaxAmount());
            ps.setDouble(5, b.getTotalAmount());
            ps.setString(6, b.getPaymentStatus());
            ps.setString(7, b.getPaymentMethod());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create bill: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Bill> getAllBills() throws DatabaseException {
        ArrayList<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM billing ORDER BY bill_date DESC";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch bills: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Bill getBillByShipmentId(int shipmentId) throws DatabaseException {
        String sql = "SELECT * FROM billing WHERE shipment_id = ?";
        Bill b = null;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, shipmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) b = mapRow(rs);
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch bill: " + e.getMessage(), e);
        }
        return b;
    }

    @Override
    public void updatePaymentStatus(int billId, String status,
                                    String method) throws DatabaseException {
        String sql = "UPDATE billing SET payment_status=?, payment_method=?, "
                   + "paid_date=NOW() WHERE bill_id=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, method);
            ps.setInt(3,    billId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update payment: " + e.getMessage(), e);
        }
    }
}