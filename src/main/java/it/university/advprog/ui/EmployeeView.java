package it.university.advprog.ui;

import it.university.advprog.Employee;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridLayout;

public class EmployeeView extends JFrame implements EmployeeViewInterface {

	private static final long serialVersionUID = 1L;

	private JLabel lblEmployeeId;
	private JLabel lblEmployeeName;
	private JTextField txtEmployeeId;
	private JTextField txtEmployeeName;
	private JButton btnAddEmployee;
	private JButton btnRemoveEmployee;

	private EmployeeController employeeController;

	public EmployeeView() {
		this(null);
	}

	public EmployeeView(EmployeeController controller) {
		this.employeeController = controller;

		setName("employeeView");
		setTitle("Employee View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initUI();
		initListeners();
		updateButtonStates();

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

		btnRemoveEmployee = new JButton("Remove Employee");
		btnRemoveEmployee.setName("btnRemoveEmployee");

		panel.add(lblEmployeeId);
		panel.add(txtEmployeeId);
		panel.add(lblEmployeeName);
		panel.add(txtEmployeeName);
		panel.add(btnAddEmployee);
		panel.add(btnRemoveEmployee);

		setContentPane(panel);
	}

	private void initListeners() {
		DocumentListener fieldListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateButtonStates();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateButtonStates();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateButtonStates();
			}
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
	}

	@Override
	public void employeeRemoved(String id) {
	}

	@Override
	public void showError(String message) {
	}
}
