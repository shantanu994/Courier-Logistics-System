package com.courier.ui;

import com.courier.ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private JLabel      statusBar;

    public MainFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Courier Logistics System");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // open in center of screen

        // menu bar
        setJMenuBar(buildMenuBar());

        // main layout
        setLayout(new BorderLayout());

        // header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(40, 40, 120));
        header.setPreferredSize(new Dimension(0, 55));
        JLabel headerLabel = new JLabel("  Courier Logistics System", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        header.add(headerLabel, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // tabbed pane with 4 panels
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tabbedPane.addTab("Customers",  new CustomerPanel());
        tabbedPane.addTab("Shipments",  new ShipmentPanel());
        tabbedPane.addTab("Employees",  new EmployeePanel());
        tabbedPane.addTab("Billing",    new BillingPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // status bar at bottom
        statusBar = new JLabel("  Ready");
        statusBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.setForeground(new Color(100, 100, 100));
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        add(statusBar, BorderLayout.SOUTH);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        exitItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
        fileMenu.add(exitItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
            this,
            "Courier Logistics System v1.0\nBuilt with Java Swing + JDBC",
            "About",
            JOptionPane.INFORMATION_MESSAGE
        ));
        helpMenu.add(aboutItem);

        // Logout
        JMenu logoutMenu = new JMenu("Logout");
        logoutMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        logoutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Logout and return to login screen?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    JFrame loginFrame = new JFrame("Login");
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.setSize(500, 400);
                    loginFrame.setLocationRelativeTo(null);
                    loginFrame.add(new LoginPanel());
                    loginFrame.setVisible(true);
                }
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(Box.createHorizontalGlue()); // pushes logout to right
        menuBar.add(logoutMenu);

        return menuBar;
    }

    // helper so panels can update the status bar
    public void setStatus(String message) {
        statusBar.setText("  " + message);
    }
}