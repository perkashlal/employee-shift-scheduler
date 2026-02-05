package it.university.advprog.ui;

public interface EmployeeController {

    void addEmployee(String id, String name);

    void removeEmployee(String id);
    void allEmployees();


    void setEmployeeView(EmployeeViewInterface view);
}
