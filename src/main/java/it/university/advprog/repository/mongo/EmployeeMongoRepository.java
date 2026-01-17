package it.university.advprog.repository.mongo;

import java.util.List;

import com.mongodb.MongoClient;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;

public class EmployeeMongoRepository implements EmployeeRepository {

    public EmployeeMongoRepository(MongoClient client) {
        // intentionally empty (RED phase)
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public Employee findById(String id) {
        return null;
    }

    @Override
    public void save(Employee employee) {
        // do nothing
    }

    @Override
    public void delete(String id) {
        // do nothing
    }
}
