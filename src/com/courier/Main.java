package com.courier;

import com.courier.ui.LoginPanel;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Courier Logistics — Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 420);
            frame.setLocationRelativeTo(null);
            frame.add(new LoginPanel());
            frame.setVisible(true);
        });
    }
}