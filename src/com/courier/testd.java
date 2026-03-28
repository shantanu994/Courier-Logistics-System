package com.courier;

import java.sql.Connection;
import java.sql.DriverManager;

public class testd {
    public static void main(String[] args) throws Exception {
        String url  = "jdbc:mysql://localhost:3306/courier_logistics";
        String user = "root";
        String pass = "@Shan2006";

        Connection conn = DriverManager.getConnection(url, user, pass);
        System.out.println("Connected to MySQL successfully!");
        conn.close();
    }
}