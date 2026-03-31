package com.courier.dao;

import com.courier.db.DBConnection;
import com.courier.model.Customer;
import com.courier.util.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void addCustomer(Customer c) throws DatabaseException {
        String sql = "INSERT INTO customers (name, email, phone, address, city, state, pincode) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getCity());
            ps.setString(6, c.getState());
            ps.setString(7, c.getPincode());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add customer: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws DatabaseException {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("pincode")
                );
                list.add(c);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch customers: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Customer getCustomerById(int id) throws DatabaseException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        Customer c = null;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("pincode")
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch customer: " + e.getMessage(), e);
        }
        return c;
    }

    @Override
    public void updateCustomer(Customer c) throws DatabaseException {
        String sql = "UPDATE customers SET name=?, email=?, phone=?, "
                   + "address=?, city=?, state=?, pincode=? "
                   + "WHERE customer_id=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getCity());
            ps.setString(6, c.getState());
            ps.setString(7, c.getPincode());
            ps.setInt(8,    c.getCustomerId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update customer: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCustomer(int id) throws DatabaseException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete customer: " + e.getMessage(), e);
        }
    }
}