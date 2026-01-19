package it.university.advprog.repository;
import it.university.advprog.Employee;
import java.util.List;



public interface EmployeeRepository {

    List<Employee> findAll();

    Employee findById(String id);

    void save(Employee employee);

    void delete(String id);
}
