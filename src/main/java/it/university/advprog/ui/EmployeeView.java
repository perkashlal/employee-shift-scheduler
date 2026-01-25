package it.university.advprog.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridLayout;

public class EmployeeView extends JFrame {

    private JLabel lblEmployeeId;
    private JLabel lblEmployeeName;
    private JTextField txtEmployeeId;
    private JTextField txtEmployeeName;
    private JButton btnAddEmployee;
    private JButton btnRemoveEmployee;

    private EmployeeController employeeController;

    public EmployeeView() {
        setTitle("Employee View");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        lblEmployeeId = new JLabel("Employee ID");
        lblEmployeeId.setName("lblEmployeeId");

        lblEmployeeName = new JLabel("Employee Name");
        lblEmployeeName.setName("lblEmployeeName");

        txtEmployeeId = new JTextField();
        txtEmployeeId.setName("txtEmployeeId");

        txtEmployeeName = new JTextField();
        txtEmployeeName.setName("txtEmployeeName");

        btnAddEmployee = new JButton("Add Employee");
        btnAddEmployee.setName("btnAddEmployee");
        btnAddEmployee.setEnabled(false);

        btnRemoveEmployee = new JButton("Remove Employee");
        btnRemoveEmployee.setName("btnRemoveEmployee");
        btnRemoveEmployee.setEnabled(false);

        // Enable Add button only when both fields are non-empty
        DocumentListener enableAddButtonListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateAddButtonState(); }
            @Override public void removeUpdate(DocumentEvent e) { updateAddButtonState(); }
            @Override public void changedUpdate(DocumentEvent e) { updateAddButtonState(); }
        };

        txtEmployeeId.getDocument().addDocumentListener(enableAddButtonListener);
        txtEmployeeName.getDocument().addDocumentListener(enableAddButtonListener);

        // Add delegation
        btnAddEmployee.addActionListener(e -> {
            String id = txtEmployeeId.getText();
            String name = txtEmployeeName.getText();

            if (employeeController != null) {
                employeeController.addEmployee(id, name);
            }

            txtEmployeeId.setText("");
            txtEmployeeName.setText("");
            updateAddButtonState();
        });

        // Delete delegation (UI Test #7)
        btnRemoveEmployee.addActionListener(e -> {
            if (employeeController != null) {
                String id = txtEmployeeId.getText().trim();
                employeeController.removeEmployee(id);
            }
        });

        panel.add(lblEmployeeId);
        panel.add(txtEmployeeId);
        panel.add(lblEmployeeName);
        panel.add(txtEmployeeName);
        panel.add(btnAddEmployee);
        panel.add(btnRemoveEmployee);

        add(panel);
    }

    private void updateAddButtonState() {
        boolean enabled =
                !txtEmployeeId.getText().trim().isEmpty()
             && !txtEmployeeName.getText().trim().isEmpty();

        btnAddEmployee.setEnabled(enabled);
    }

    // Controller injection (book-aligned)
    public void setEmployeeController(EmployeeController controller) {
        this.employeeController = controller;
    }

    // Package-private getter for UI tests (allowed by the book)
    JButton getBtnRemoveEmployee() {
        return btnRemoveEmployee;
    }
}
