package com.courier.ui.panels;

import com.courier.dao.CustomerDAO;
import com.courier.dao.CustomerDAOImpl;
import com.courier.model.Customer;
import com.courier.util.DatabaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerPanel extends JPanel {

    private CustomerDAO dao = new CustomerDAOImpl();

    // table
    private JTable            table;
    private DefaultTableModel tableModel;

    // form fields
    private JTextField idField, nameField, emailField,
                       phoneField, addressField, cityField,
                       stateField, pincodeField;

    // buttons
    private JButton addBtn, updateBtn, deleteBtn, clearBtn;

    public CustomerPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildFormPanel(),  BorderLayout.SOUTH);
        loadTable();
    }

    // ── table ────────────────────────────────────────────
    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"ID", "Name", "Email", "Phone", "City", "State", "Pincode"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // click row → fill form
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromTable();
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ── form ─────────────────────────────────────────────
    private JPanel buildFormPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(10, 0));
        wrapper.setBorder(BorderFactory.createTitledBorder("Customer Details"));

        // fields
        JPanel fields = new JPanel(new GridLayout(4, 4, 8, 8));

        idField      = makeField(fields, "ID (auto)");
        nameField    = makeField(fields, "Name");
        emailField   = makeField(fields, "Email");
        phoneField   = makeField(fields, "Phone");
        addressField = makeField(fields, "Address");
        cityField    = makeField(fields, "City");
        stateField   = makeField(fields, "State");
        pincodeField = makeField(fields, "Pincode");

        idField.setEditable(false);
        idField.setBackground(new Color(240, 240, 240));

        // buttons
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 6, 6));
        addBtn    = makeButton("Add",    new Color(46, 139, 87));
        updateBtn = makeButton("Update", new Color(70, 130, 180));
        deleteBtn = makeButton("Delete", new Color(178, 34, 34));
        clearBtn  = makeButton("Clear",  new Color(105, 105, 105));

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        wrapper.add(fields,   BorderLayout.CENTER);
        wrapper.add(btnPanel, BorderLayout.EAST);

        // wire buttons
        addBtn.addActionListener(e    -> addCustomer());
        updateBtn.addActionListener(e -> updateCustomer());
        deleteBtn.addActionListener(e -> deleteCustomer());
        clearBtn.addActionListener(e  -> clearForm());

        return wrapper;
    }

    // ── actions ───────────────────────────────────────────
    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            ArrayList<Customer> list = dao.getAllCustomers();
            for (Customer c : list) {
                tableModel.addRow(new Object[]{
                    c.getCustomerId(), c.getName(), c.getEmail(),
                    c.getPhone(), c.getCity(), c.getState(), c.getPincode()
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
        nameField.setText(tableModel.getValueAt(row, 1).toString());
        emailField.setText(tableModel.getValueAt(row, 2).toString());
        phoneField.setText(tableModel.getValueAt(row, 3).toString());
        cityField.setText(tableModel.getValueAt(row, 4).toString());
        stateField.setText(tableModel.getValueAt(row, 5).toString());
        pincodeField.setText(tableModel.getValueAt(row, 6).toString());
    }

    private void addCustomer() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Customer c = new Customer(0,
                nameField.getText().trim(), emailField.getText().trim(),
                phoneField.getText().trim(), addressField.getText().trim(),
                cityField.getText().trim(),  stateField.getText().trim(),
                pincodeField.getText().trim());
            dao.addCustomer(c);
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            clearForm();
            loadTable();
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a customer to update.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Customer c = new Customer(
                Integer.parseInt(idField.getText()),
                nameField.getText().trim(), emailField.getText().trim(),
                phoneField.getText().trim(), addressField.getText().trim(),
                cityField.getText().trim(),  stateField.getText().trim(),
                pincodeField.getText().trim());
            dao.updateCustomer(c);
            JOptionPane.showMessageDialog(this, "Customer updated successfully!");
            clearForm();
            loadTable();
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a customer to delete.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            dao.deleteCustomer(Integer.parseInt(idField.getText()));
            JOptionPane.showMessageDialog(this, "Customer deleted.");
            clearForm();
            loadTable();
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText(""); nameField.setText("");  emailField.setText("");
        phoneField.setText(""); addressField.setText("");
        cityField.setText(""); stateField.setText(""); pincodeField.setText("");
        table.clearSelection();
    }

    // ── helpers ───────────────────────────────────────────
    private JTextField makeField(JPanel parent, String label) {
        parent.add(new JLabel(label));
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        parent.add(tf);
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