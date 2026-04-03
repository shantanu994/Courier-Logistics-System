package com.courier.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;

    // simple hardcoded credentials for now
    // you can later wire this to the employees table
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    public LoginPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));

        // card panel
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210), 1),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // title
        JLabel title = new JLabel("Courier Logistics System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(50, 50, 120));
        gbc.gridy = 0;
        card.add(title, gbc);

        // subtitle
        JLabel subtitle = new JLabel("Please sign in to continue", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(130, 130, 140));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 8, 24, 8);
        card.add(subtitle, gbc);

        // username label
        gbc.insets = new Insets(6, 8, 2, 8);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        card.add(userLabel, gbc);

        // username field
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(280, 36));
        gbc.gridy = 3;
        card.add(usernameField, gbc);

        // password label
        gbc.gridy = 4;
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        card.add(passLabel, gbc);

        // password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(280, 36));
        gbc.gridy = 5;
        card.add(passwordField, gbc);

        // error label (hidden by default)
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setForeground(new Color(200, 50, 50));
        gbc.gridy = 6;
        gbc.insets = new Insets(4, 8, 4, 8);
        card.add(errorLabel, gbc);

        // login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(50, 50, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(280, 40));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 7;
        gbc.insets = new Insets(16, 8, 8, 8);
        card.add(loginButton, gbc);

        add(card);

        // allow pressing Enter on password field to login
        passwordField.addActionListener(e -> handleLogin());
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame) { // ← JDK 11 compatible
                JFrame frame = (JFrame) window; // ← explicit cast
                frame.dispose();
            }
            new MainFrame().setVisible(true);
        } else {
            errorLabel.setText("Invalid username or password.");
            passwordField.setText("");
        }
    }
}