package it.university.advprog.ui;

import it.university.advprog.Employee;

public interface EmployeeViewInterface {

    void setEmployeeController(EmployeeController controller);

    void employeeAdded(Employee e);

    void employeeRemoved(String id);

    void showError(String message);
}
