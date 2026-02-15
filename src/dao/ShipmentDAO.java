package dao;

import models.Shipment;
import java.sql.*;

public class ShipmentDAO {

    public static void addShipment(Shipment s) {
        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps =
                con.prepareStatement("INSERT INTO shipment(tracking,status) VALUES(?,?)");

            ps.setString(1,s.tracking);
            ps.setString(2,s.status);

            ps.executeUpdate();
            con.close();

        } catch(Exception e){ e.printStackTrace(); }
    }
}
