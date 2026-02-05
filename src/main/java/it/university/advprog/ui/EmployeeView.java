package it.university.advprog.ui;

import it.university.advprog.Employee;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class EmployeeView extends JFrame implements EmployeeViewInterface {

    private static final long serialVersionUID = 1L;

    private JPanel formPanel;

    private JList<String> employeeList;
    private DefaultListModel<String> employeeListModel;

    private JLabel lblEmployeeId;
    private JLabel lblEmployeeName;
    private JTextField txtEmployeeId;
    private JTextField txtEmployeeName;
    private JButton btnAddEmployee;
    private JButton btnRemoveEmployee;

    private JLabel errorMessageLabel;

    private EmployeeController employeeController;

    public EmployeeView() {
        this(null);
    }

    public EmployeeView(EmployeeController controller) {
        this.employeeController = controller;

        setName("employeeView");
        setTitle("Employee View");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        initListeners();
        updateButtonStates();

        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        setContentPane(root);

        formPanel = new JPanel(new GridLayout(3, 2));

        lblEmployeeId = new JLabel("Employee ID");
        lblEmployeeId.setName("lblEmployeeId");

        lblEmployeeName = new JLabel("Employee Name");
        lblEmployeeName.setName("lblEmployeeName");

        txtEmployeeId = new JTextField();
        txtEmployeeId.setName("idTextBox");

        txtEmployeeName = new JTextField();
        txtEmployeeName.setName("nameTextBox");

        btnAddEmployee = new JButton("Add Employee");
        btnAddEmployee.setName("btnAddEmployee");

        btnRemoveEmployee = new JButton("Remove Employee");
        btnRemoveEmployee.setName("btnRemoveEmployee");

        formPanel.add(lblEmployeeId);
        formPanel.add(txtEmployeeId);
        formPanel.add(lblEmployeeName);
        formPanel.add(txtEmployeeName);
        formPanel.add(btnAddEmployee);
        formPanel.add(btnRemoveEmployee);

        root.add(formPanel, BorderLayout.NORTH);

        employeeListModel = new DefaultListModel<>();
        employeeList = new JList<>(employeeListModel);
        employeeList.setName("employeeList");
        root.add(new JScrollPane(employeeList), BorderLayout.CENTER);

        errorMessageLabel = new JLabel("");
        errorMessageLabel.setName("errorMessageLabel");
        root.add(errorMessageLabel, BorderLayout.SOUTH);
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
        String id = txtEmployeeId.getText().trim();
        String name = txtEmployeeName.getText().trim();

        if (employeeController != null) {
            employeeController.addEmployee(id, name);
        }

        txtEmployeeId.setText("");
        txtEmployeeName.setText("");
        updateButtonStates();
    }

    private void handleRemoveEmployee() {
        String id = txtEmployeeId.getText().trim();

        if (employeeController != null) {
            employeeController.removeEmployee(id);
        }

        updateButtonStates();
    }

    @Override
    public void setEmployeeController(EmployeeController controller) {
        this.employeeController = controller;
    }

    @Override
    public void employeeAdded(Employee employee) {
        SwingUtilities.invokeLater(() -> {
            employeeListModel.addElement(display(employee));
            errorMessageLabel.setText("");
        });
    }

    @Override
    public void employeeRemoved(String id) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < employeeListModel.size(); i++) {
                String row = employeeListModel.get(i);
                if (row.startsWith(id + " - ")) {
                    employeeListModel.remove(i);
                    break;
                }
            }
            errorMessageLabel.setText("");
        });
    }

    @Override
    public void showError(String message) {
        SwingUtilities.invokeLater(() -> errorMessageLabel.setText(message));
    }

    private String display(Employee employee) {
        return employee.id() + " - " + employee.name();
    }
}