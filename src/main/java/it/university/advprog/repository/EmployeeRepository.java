package it.university.advprog.repository;

import it.university.advprog.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    void save(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(String id);

    void delete(String id);
}
