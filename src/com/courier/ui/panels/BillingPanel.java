package com.courier.ui.panels;

import com.courier.dao.BillingDAO;
import com.courier.dao.BillingDAOImpl;
import com.courier.model.Bill;
import com.courier.util.DatabaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BillingPanel extends JPanel {

    private BillingDAO dao = new BillingDAOImpl();

    private JTable            table;
    private DefaultTableModel tableModel;

    private JTextField    billIdField, shipmentIdField, customerIdField,
                          baseAmountField, taxAmountField, totalAmountField;
    private JComboBox<String> paymentStatusBox, paymentMethodBox;

    private JButton createBtn, payBtn, refreshBtn, clearBtn;

    // billing rate per kg
    private static final double RATE_PER_KG = 50.0;
    private static final double TAX_RATE    = 0.18;

    public BillingPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildFormPanel(),  BorderLayout.SOUTH);
        loadTable();
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"Bill ID", "Shipment ID", "Customer ID",
                         "Base Amt", "Tax", "Total", "Status", "Method", "Date"};
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
        wrapper.setBorder(BorderFactory.createTitledBorder("Billing Details"));

        JPanel fields = new JPanel(new GridLayout(4, 4, 8, 8));

        billIdField      = makeField(fields, "Bill ID (auto)");
        shipmentIdField  = makeField(fields, "Shipment ID");
        customerIdField  = makeField(fields, "Customer ID");
        baseAmountField  = makeField(fields, "Base Amount (₹)");
        taxAmountField   = makeField(fields, "Tax Amount (₹)");
        totalAmountField = makeField(fields, "Total Amount (₹)");

        fields.add(new JLabel("Payment Status"));
        paymentStatusBox = new JComboBox<>(new String[]{"PENDING", "PAID", "OVERDUE"});
        fields.add(paymentStatusBox);

        fields.add(new JLabel("Payment Method"));
        paymentMethodBox = new JComboBox<>(new String[]{"CASH", "CARD", "UPI", "NET_BANKING"});
        fields.add(paymentMethodBox);

        // auto-calculate on shipment ID entry
        shipmentIdField.addActionListener(e -> calculateAmount());

        billIdField.setEditable(false);
        billIdField.setBackground(new Color(240, 240, 240));
        taxAmountField.setEditable(false);
        taxAmountField.setBackground(new Color(240, 240, 240));
        totalAmountField.setEditable(false);
        totalAmountField.setBackground(new Color(240, 240, 240));

        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 6, 6));
        createBtn  = makeButton("Create Bill",   new Color(46, 139, 87));
        payBtn     = makeButton("Mark as Paid",  new Color(70, 130, 180));
        refreshBtn = makeButton("Refresh",       new Color(153, 102, 0));
        clearBtn   = makeButton("Clear",         new Color(105, 105, 105));

        btnPanel.add(createBtn);
        btnPanel.add(payBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(clearBtn);

        wrapper.add(fields,   BorderLayout.CENTER);
        wrapper.add(btnPanel, BorderLayout.EAST);

        createBtn.addActionListener(e  -> createBill());
        payBtn.addActionListener(e     -> markAsPaid());
        refreshBtn.addActionListener(e -> loadTable());
        clearBtn.addActionListener(e   -> clearForm());

        return wrapper;
    }

    // auto-calculate tax and total from base amount
    private void calculateAmount() {
        try {
            double base  = Double.parseDouble(baseAmountField.getText().trim());
            double tax   = Math.round(base * TAX_RATE * 100.0) / 100.0;
            double total = Math.round((base + tax) * 100.0) / 100.0;
            taxAmountField.setText(String.valueOf(tax));
            totalAmountField.setText(String.valueOf(total));
        } catch (NumberFormatException ignored) {}
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (Bill b : dao.getAllBills()) {
                tableModel.addRow(new Object[]{
                    b.getBillId(), b.getShipmentId(), b.getCustomerId(),
                    b.getBaseAmount(), b.getTaxAmount(), b.getTotalAmount(),
                    b.getPaymentStatus(), b.getPaymentMethod(), b.getBillDate()
                });
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        billIdField.setText(tableModel.getValueAt(row, 0).toString());
        shipmentIdField.setText(tableModel.getValueAt(row, 1).toString());
        customerIdField.setText(tableModel.getValueAt(row, 2).toString());
        baseAmountField.setText(tableModel.getValueAt(row, 3).toString());
        taxAmountField.setText(tableModel.getValueAt(row, 4).toString());
        totalAmountField.setText(tableModel.getValueAt(row, 5).toString());
        paymentStatusBox.setSelectedItem(tableModel.getValueAt(row, 6).toString());
        paymentMethodBox.setSelectedItem(tableModel.getValueAt(row, 7) != null
            ? tableModel.getValueAt(row, 7).toString() : "CASH");
    }

    private void createBill() {
        try {
            calculateAmount();
            Bill b = new Bill(
                Integer.parseInt(shipmentIdField.getText().trim()),
                Integer.parseInt(customerIdField.getText().trim()),
                Double.parseDouble(baseAmountField.getText().trim()),
                Double.parseDouble(taxAmountField.getText().trim()),
                Double.parseDouble(totalAmountField.getText().trim()),
                paymentStatusBox.getSelectedItem().toString(),
                paymentMethodBox.getSelectedItem().toString());
            dao.createBill(b);
            JOptionPane.showMessageDialog(this, "Bill created successfully!");
            clearForm(); loadTable();
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fill Shipment ID, Customer ID and Base Amount.",
                "Validation", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void markAsPaid() {
        if (billIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a bill to mark as paid.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.updatePaymentStatus(
                Integer.parseInt(billIdField.getText()),
                "PAID",
                paymentMethodBox.getSelectedItem().toString());
            JOptionPane.showMessageDialog(this, "Payment recorded successfully!");
            clearForm(); loadTable();
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        billIdField.setText(""); shipmentIdField.setText("");
        customerIdField.setText(""); baseAmountField.setText("");
        taxAmountField.setText(""); totalAmountField.setText("");
        paymentStatusBox.setSelectedIndex(0);
        paymentMethodBox.setSelectedIndex(0);
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
        btn.setBackground(bg); btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}