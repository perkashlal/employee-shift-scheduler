package it.university.advprog.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridLayout;

public class EmployeeView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel lblEmployeeId;
    private JLabel lblEmployeeName;
    private JTextField txtEmployeeId;
    private JTextField txtEmployeeName;
    private JButton btnAddEmployee;
    private JButton btnRemoveEmployee;

    private EmployeeController employeeController;

    
    public EmployeeView() {
        setTitle("Employee View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        initListeners();

        pack();
        setLocationRelativeTo(null);
    }

    
    private void initUI() {
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

        panel.add(lblEmployeeId);
        panel.add(txtEmployeeId);
        panel.add(lblEmployeeName);
        panel.add(txtEmployeeName);
        panel.add(btnAddEmployee);
        panel.add(btnRemoveEmployee);

        add(panel);
    }

    
    private void initListeners() {
        DocumentListener fieldListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateButtonStates(); }
            @Override public void removeUpdate(DocumentEvent e) { updateButtonStates(); }
            @Override public void changedUpdate(DocumentEvent e) { updateButtonStates(); }
        };

        txtEmployeeId.getDocument().addDocumentListener(fieldListener);
        txtEmployeeName.getDocument().addDocumentListener(fieldListener);

        btnAddEmployee.addActionListener(e -> handleAddEmployee());
        btnRemoveEmployee.addActionListener(e -> handleRemoveEmployee());
    }

   
    private void updateButtonStates() {
        boolean hasId = !txtEmployeeId.getText().trim().isEmpty();
        boolean hasName = !txtEmployeeName.getText().trim().isEmpty();

        btnAddEmployee.setEnabled(hasId && hasName);
        btnRemoveEmployee.setEnabled(hasId);
    }

    private void handleAddEmployee() {
        if (employeeController != null) {
            employeeController.addEmployee(
                    txtEmployeeId.getText(),
                    txtEmployeeName.getText()
            );
        }

        txtEmployeeId.setText("");
        txtEmployeeName.setText("");
        updateButtonStates();
    }

    private void handleRemoveEmployee() {
        if (employeeController != null) {
            employeeController.removeEmployee(txtEmployeeId.getText());
        }
    }

  
    public void setEmployeeController(EmployeeController controller) {
        this.employeeController = controller;
    }
}
