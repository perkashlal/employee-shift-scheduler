package it.university.advprog.repository;

import java.util.List;
import java.util.Optional;

import it.university.advprog.model.Employee;

public interface EmployeeRepository {

    void save(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(String id);

    void delete(String id);
}
