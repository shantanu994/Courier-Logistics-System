package com.courier.ui.panels;

import com.courier.dao.EmployeeDAO;
import com.courier.dao.EmployeeDAOImpl;
import com.courier.model.Employee;
import com.courier.util.DatabaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

public class EmployeePanel extends JPanel {

    private EmployeeDAO dao = new EmployeeDAOImpl();

    private JTable            table;
    private DefaultTableModel tableModel;

    private JTextField    idField, nameField, emailField,
                          phoneField, salaryField, hireDateField;
    private JComboBox<String> roleBox, statusBox;

    private JButton addBtn, updateBtn, deleteBtn, clearBtn;

    public EmployeePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildFormPanel(),  BorderLayout.SOUTH);
        loadTable();
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"ID", "Name", "Email", "Phone", "Role", "Salary", "Hire Date", "Status"};
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
        wrapper.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        JPanel fields = new JPanel(new GridLayout(4, 4, 8, 8));

        idField       = makeField(fields, "ID (auto)");
        nameField     = makeField(fields, "Name");
        emailField    = makeField(fields, "Email");
        phoneField    = makeField(fields, "Phone");
        salaryField   = makeField(fields, "Salary");
        hireDateField = makeField(fields, "Hire Date (YYYY-MM-DD)");

        fields.add(new JLabel("Role"));
        roleBox = new JComboBox<>(new String[]{"ADMIN", "MANAGER", "COURIER"});
        fields.add(roleBox);

        fields.add(new JLabel("Status"));
        statusBox = new JComboBox<>(new String[]{"ACTIVE", "INACTIVE"});
        fields.add(statusBox);

        idField.setEditable(false);
        idField.setBackground(new Color(240, 240, 240));

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

        addBtn.addActionListener(e    -> addEmployee());
        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());
        clearBtn.addActionListener(e  -> clearForm());

        return wrapper;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (Employee e : dao.getAllEmployees()) {
                tableModel.addRow(new Object[]{
                    e.getEmployeeId(), e.getName(), e.getEmail(),
                    e.getPhone(), e.getRole(), e.getSalary(),
                    e.getHireDate(), e.getStatus()
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
        roleBox.setSelectedItem(tableModel.getValueAt(row, 4).toString());
        salaryField.setText(tableModel.getValueAt(row, 5).toString());
        hireDateField.setText(tableModel.getValueAt(row, 6).toString());
        statusBox.setSelectedItem(tableModel.getValueAt(row, 7).toString());
    }

    private void addEmployee() {
        try {
            Employee e = new Employee(0,
                nameField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                roleBox.getSelectedItem().toString(),
                Double.parseDouble(salaryField.getText().trim()),
                Date.valueOf(hireDateField.getText().trim()),
                statusBox.getSelectedItem().toString());
            dao.addEmployee(e);
            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            clearForm(); loadTable();
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Check all fields. Date format: YYYY-MM-DD",
                "Validation", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateEmployee() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select an employee to update.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Employee e = new Employee(
                Integer.parseInt(idField.getText()),
                nameField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                roleBox.getSelectedItem().toString(),
                Double.parseDouble(salaryField.getText().trim()),
                Date.valueOf(hireDateField.getText().trim()),
                statusBox.getSelectedItem().toString());
            dao.updateEmployee(e);
            JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            clearForm(); loadTable();
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Check all fields. Date format: YYYY-MM-DD",
                "Validation", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteEmployee() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select an employee to delete.",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            dao.deleteEmployee(Integer.parseInt(idField.getText()));
            JOptionPane.showMessageDialog(this, "Employee deleted.");
            clearForm(); loadTable();
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText(""); nameField.setText(""); emailField.setText("");
        phoneField.setText(""); salaryField.setText(""); hireDateField.setText("");
        roleBox.setSelectedIndex(0); statusBox.setSelectedIndex(0);
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