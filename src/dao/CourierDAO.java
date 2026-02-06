package courier.dao;

import courier.model.Courier;
import courier.util.DBConnection;

import java.sql.*;
import java.util.*;

public class CourierDAO {

    public static void addCourier(Courier c) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO courier(sender,receiver,address,tracking,status) VALUES(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.sender);
            ps.setString(2, c.receiver);
            ps.setString(3, c.address);
            ps.setString(4, c.tracking);
            ps.setString(5, c.status);

            ps.executeUpdate();
            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Courier> getAll() {
        List<Courier> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM courier");

            while(rs.next()) {
                Courier c = new Courier();
                c.id = rs.getInt("id");
                c.sender = rs.getString("sender");
                c.receiver = rs.getString("receiver");
                c.address = rs.getString("address");
                c.tracking = rs.getString("tracking");
                c.status = rs.getString("status");
                list.add(c);
            }

            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
