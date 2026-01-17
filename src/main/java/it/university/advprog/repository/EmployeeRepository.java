package it.university.advprog.repository;

import java.util.List;

import it.university.advprog.Employee;

public interface EmployeeRepository {

    List<Employee> findAll();

    Employee findById(String id);

    void save(Employee employee);

    void delete(String id);
}
