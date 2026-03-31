package com.courier.dao;

import com.courier.db.DBConnection;
import com.courier.model.Employee;
import com.courier.util.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    // helper to build Employee from current ResultSet row
    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("employee_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("role"),
            rs.getDouble("salary"),
            rs.getDate("hire_date"),
            rs.getString("status")
        );
    }

    @Override
    public void addEmployee(Employee e) throws DatabaseException {
        String sql = "INSERT INTO employees (name, email, phone, role, salary, hire_date, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, e.getName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getRole());
            ps.setDouble(5, e.getSalary());
            ps.setDate(6,   e.getHireDate());
            ps.setString(7, e.getStatus());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Failed to add employee: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ArrayList<Employee> getAllEmployees() throws DatabaseException {
        ArrayList<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch employees: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public ArrayList<Employee> getCouriers() throws DatabaseException {
        ArrayList<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE role = 'COURIER' AND status = 'ACTIVE'";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch couriers: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Employee getEmployeeById(int id) throws DatabaseException {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        Employee e = null;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) e = mapRow(rs);
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Failed to fetch employee: " + ex.getMessage(), ex);
        }
        return e;
    }

    @Override
    public void updateEmployee(Employee e) throws DatabaseException {
        String sql = "UPDATE employees SET name=?, email=?, phone=?, "
                   + "role=?, salary=?, hire_date=?, status=? "
                   + "WHERE employee_id=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, e.getName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getRole());
            ps.setDouble(5, e.getSalary());
            ps.setDate(6,   e.getHireDate());
            ps.setString(7, e.getStatus());
            ps.setInt(8,    e.getEmployeeId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Failed to update employee: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteEmployee(int id) throws DatabaseException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete employee: " + e.getMessage(), e);
        }
    }
}