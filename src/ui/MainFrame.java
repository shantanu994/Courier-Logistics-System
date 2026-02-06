package ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Courier System");
        setSize(400,300);
        setLayout(new JTabbedPane());

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Courier", new CourierPanel());
        tabs.add("Customer", new CustomerPanel());
        tabs.add("Employee", new EmployeePanel());
        tabs.add("Shipment", new ShipmentPanel());

        add(tabs);
        setVisible(true);
    }
}
