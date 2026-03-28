package com.courier.db;

import com.courier.util.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // --- change these 3 values to match your MySQL setup ---
    private static final String URL      = "jdbc:mysql://localhost:3306/courier_logistics";
    private static final String USER     = "root";
    private static final String PASSWORD = "@Shan2006";
    // --------------------------------------------------------

    private static Connection connection = null;

    // private constructor — nobody should create an instance of this class
    private DBConnection() {}

    public static Connection getConnection() throws DatabaseException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            }
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("MySQL Driver not found. Check your lib/ folder.", e);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to connect to database: " + e.getMessage(), e);
        }
        return connection;
    }

    public static void closeConnection() throws DatabaseException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to close connection: " + e.getMessage(), e);
        }
    }
}