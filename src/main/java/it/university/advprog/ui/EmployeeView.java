package it.university.advprog.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
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

        DocumentListener enableAddButtonListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateAddButtonState(); }
            @Override public void removeUpdate(DocumentEvent e) { updateAddButtonState(); }
            @Override public void changedUpdate(DocumentEvent e) { updateAddButtonState(); }
        };

        txtEmployeeId.getDocument().addDocumentListener(enableAddButtonListener);
        txtEmployeeName.getDocument().addDocumentListener(enableAddButtonListener);

        // 🔹 NEW: minimal ActionListener for GREEN
        btnAddEmployee.addActionListener(e -> {
            txtEmployeeId.setText("");
            txtEmployeeName.setText("");
            updateAddButtonState(); // disables the button again
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
}
