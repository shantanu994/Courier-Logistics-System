package com.courier.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Courier System Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton btnViewParcels = new JButton("View Parcels");
        panel.add(btnViewParcels);

        add(panel, BorderLayout.CENTER);
    }
}
