package it.university.advprog.controller;

import it.university.advprog.ui.EmployeeViewInterface;

public interface EmployeeController {

    void addEmployee(String id, String name);

    void removeEmployee(String id);
    void allEmployees();


    void setEmployeeView(EmployeeViewInterface view);
}
