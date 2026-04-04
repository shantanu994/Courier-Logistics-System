package com.courier.ui.panels;

import com.courier.dao.*;
import com.courier.model.Shipment;
import com.courier.util.DatabaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

public class ShipmentPanel extends JPanel {

    private ShipmentDAO dao        = new ShipmentDAOImpl();
    private CustomerDAO customerDao = new CustomerDAOImpl();

    private JTable            table;
    private DefaultTableModel tableModel;

    private JTextField  idField, trackingField, originField,
                        destinationField, weightField, deliveryField;
    private JComboBox<String> packageTypeBox, statusBox;
    private JTextField  senderIdField, receiverIdField, courierIdField;

    private JButton addBtn, updateStatusBtn, deleteBtn, clearBtn, trackBtn;

    public ShipmentPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildFormPanel(),  BorderLayout.SOUTH);
        loadTable();
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // search bar
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton    searchBtn   = new JButton("Track by Number");
        searchBtn.addActionListener(e -> trackShipment(searchField.getText().trim()));
        searchBar.add(new JLabel("Tracking No:"));
        searchBar.add(searchField);
        searchBar.add(searchBtn);
        panel.add(searchBar, BorderLayout.NORTH);

        String[] cols = {"ID", "Tracking No", "Sender ID", "Receiver ID",
                         "Origin", "Destination", "Weight", "Type", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getSelectionModel().addListSelectionListener(
            e -> { if (!e.getValueIsAdjusting()) fillFormFromTable(); });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildFormPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(10, 0));
        wrapper.setBorder(BorderFactory.createTitledBorder("Shipment Details"));

        JPanel fields = new JPanel(new GridLayout(5, 4, 8, 8));

        idField          = makeField(fields, "ID (auto)");
        trackingField    = makeField(fields, "Tracking No");
        senderIdField    = makeField(fields, "Sender ID");
        receiverIdField  = makeField(fields, "Receiver ID");
        courierIdField   = makeField(fields, "Courier ID");
        originField      = makeField(fields, "Origin City");
        destinationField = makeField(fields, "Destination City");
        weightField      = makeField(fields, "Weight (kg)");

        fields.add(new JLabel("Package Type"));
        packageTypeBox = new JComboBox<>(new String[]{
            "DOCUMENT", "PARCEL", "FRAGILE", "HEAVY"});
        fields.add(packageTypeBox);

        fields.add(new JLabel("Status"));
        statusBox = new JComboBox<>(new String[]{
            "PENDING","PICKED_UP","IN_TRANSIT",
            "OUT_FOR_DELIVERY","DELIVERED","RETURNED"});
        fields.add(statusBox);

        deliveryField = makeField(fields, "Est. Delivery (YYYY-MM-DD)");

        idField.setEditable(false);
        idField.setBackground(new Color(240, 240, 240));
        trackingField.setEditable(false);
        trackingField.setBackground(new Color(240, 240, 240));

        JPanel btnPanel = new JPanel(new GridLayout(5, 1, 6, 6));
        addBtn          = makeButton("Book Shipment", new Color(46, 139, 87));
        updateStatusBtn = makeButton("Update Status", new Color(70, 130, 180));
        trackBtn        = makeButton("Track",         new Color(153, 102, 0));
        deleteBtn       = makeButton("Delete",        new Color(178, 34, 34));
        clearBtn        = makeButton("Clear",         new Color(105, 105, 105));

        btnPanel.add(addBtn);
        btnPanel.add(updateStatusBtn);
        btnPanel.add(trackBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        wrapper.add(fields,   BorderLayout.CENTER);
        wrapper.add(btnPanel, BorderLayout.EAST);

        addBtn.addActionListener(e          -> addShipment());
        updateStatusBtn.addActionListener(e -> updateStatus());
        deleteBtn.addActionListener(e       -> deleteShipment());
        clearBtn.addActionListener(e        -> clearForm());
        trackBtn.addActionListener(e        -> {
            if (!idField.getText().isEmpty())
                trackShipment(trackingField.getText());
        });

        return wrapper;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (Shipment s : dao.getAllShipments()) {
                tableModel.addRow(new Object[]{
                    s.getShipmentId(), s.getTrackingNumber(),
                    s.getSenderId(), s.getReceiverId(),
                    s.getOriginCity(), s.getDestinationCity(),
                    s.getWeight(), s.getPackageType(), s.getStatus()
                });
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        idField.setText(tableModel.getValueAt(row, 0).toString());
        trackingField.setText(tableModel.getValueAt(row, 1).toString());
        senderIdField.setText(tableModel.getValueAt(row, 2).toString());
        receiverIdField.setText(tableModel.getValueAt(row, 3).toString());
        originField.setText(tableModel.getValueAt(row, 4).toString());
        destinationField.setText(tableModel.getValueAt(row, 5).toString());
        weightField.setText(tableModel.getValueAt(row, 6).toString());
        packageTypeBox.setSelectedItem(tableModel.getValueAt(row, 7).toString());
        statusBox.setSelectedItem(tableModel.getValueAt(row, 8).toString());
    }

    private void addShipment() {
        try {
            String tracking = "TRK" + System.currentTimeMillis();
            Date delivery = deliveryField.getText().isEmpty() ? null
                : Date.valueOf(deliveryField.getText().trim());

            Shipment s = new Shipment(
                tracking,
                Integer.parseInt(senderIdField.getText().trim()),
                Integer.parseInt(receiverIdField.getText().trim()),
                courierIdField.getText().isEmpty() ? 0
                    : Integer.parseInt(courierIdField.getText().trim()),
                originField.getText().trim(),
                destinationField.getText().trim(),
                Double.parseDouble(weightField.getText().trim()),
                packageTypeBox.getSelectedItem().toString(),
                statusBox.getSelectedItem().toString(),
                delivery
            );
            dao.addShipment(s);
            JOptionPane.showMessageDialog(this,
                "Shipment booked!\nTracking Number: " + tracking);
            clearForm();
            loadTable();
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Check all fields are filled correctly.",
                "Validation", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateStatus() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a shipment first.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.updateStatus(Integer.parseInt(idField.getText()),
                statusBox.getSelectedItem().toString());
            JOptionPane.showMessageDialog(this, "Status updated successfully!");
            loadTable();
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void trackShipment(String trackingNo) {
        if (trackingNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a tracking number.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Shipment s = dao.getShipmentByTracking(trackingNo);
            if (s == null) {
                JOptionPane.showMessageDialog(this, "No shipment found for: " + trackingNo);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Tracking: "    + s.getTrackingNumber() + "\n" +
                    "Route: "       + s.getOriginCity() + " → " + s.getDestinationCity() + "\n" +
                    "Status: "      + s.getStatus() + "\n" +
                    "Est. Delivery: "+ s.getEstimatedDelivery(),
                    "Shipment Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteShipment() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a shipment to delete.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this shipment?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            dao.deleteShipment(Integer.parseInt(idField.getText()));
            JOptionPane.showMessageDialog(this, "Shipment deleted.");
            clearForm();
            loadTable();
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText(""); trackingField.setText("");
        senderIdField.setText(""); receiverIdField.setText("");
        courierIdField.setText(""); originField.setText("");
        destinationField.setText(""); weightField.setText("");
        deliveryField.setText("");
        packageTypeBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
        table.clearSelection();
    }

    private JTextField makeField(JPanel p, String label) {
        p.add(new JLabel(label));
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        p.add(tf);
        return tf;
    }

    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}