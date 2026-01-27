package it.university.advprog.ui;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;

public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeRepository repository;
    private EmployeeViewInterface view;

    public EmployeeControllerImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    public void setEmployeeView(EmployeeViewInterface view) {
        this.view = view;
    }

    @Override
    public void addEmployee(String id, String name) {
        Employee employee = new Employee(id, name);
        repository.save(employee);
        view.employeeAdded(employee);
    }

    @Override
    public void removeEmployee(String id) {
        repository.delete(id);
        view.employeeRemoved(id);
    }
}
