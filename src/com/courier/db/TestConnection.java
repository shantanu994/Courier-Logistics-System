package com.courier.db;

import com.courier.util.DatabaseException;
import java.sql.Connection;

public class TestConnection {
    
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Connection object: " + conn);
            DBConnection.closeConnection();
        } catch (DatabaseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}