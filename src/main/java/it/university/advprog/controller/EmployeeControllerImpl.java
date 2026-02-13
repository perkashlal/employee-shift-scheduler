package it.university.advprog.controller;
import it.university.advprog.model.Employee;
import it.university.advprog.repository.EmployeeRepository;
import it.university.advprog.ui.EmployeeViewInterface;

import java.util.List;

public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeRepository repository;
    private EmployeeViewInterface view;

    public EmployeeControllerImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setEmployeeView(EmployeeViewInterface view) {
        this.view = view;
    }

    @Override
    public void allEmployees() {
        try {
            List<Employee> employees = repository.findAll();
            for (Employee e : employees) {
                view.employeeAdded(e);
            }
            view.showError(""); 
        } catch (Exception ex) {
            view.showError("Cannot load employees: " + ex.getMessage());
        }
    }

    @Override
    public void addEmployee(String id, String name) {
        try {
            Employee employee = new Employee(id, name);
            repository.save(employee);
            view.employeeAdded(employee);
            view.showError("");
        } catch (Exception ex) {
            view.showError("Cannot add employee: " + ex.getMessage());
        }
    }

    @Override
    public void removeEmployee(String id) {
        try {
            repository.delete(id);
            view.employeeRemoved(id);
            view.showError("");
        } catch (Exception ex) {
            view.showError("Cannot remove employee: " + ex.getMessage());
        }
        
    }
}
