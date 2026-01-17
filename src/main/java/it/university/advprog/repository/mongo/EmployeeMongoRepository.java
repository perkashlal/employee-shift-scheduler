package it.university.advprog.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;

public class EmployeeMongoRepository implements EmployeeRepository {

    public static final String EMPLOYEE_DB_NAME = "employee";
    public static final String EMPLOYEE_COLLECTION_NAME = "employees";

    private final MongoCollection<Document> employeeCollection;

    public EmployeeMongoRepository(MongoClient client) {
        MongoDatabase database = client.getDatabase(EMPLOYEE_DB_NAME);
        this.employeeCollection = database.getCollection(EMPLOYEE_COLLECTION_NAME);
    }

    @Override
    public List<Employee> findAll() {
        return StreamSupport
                .stream(employeeCollection.find().spliterator(), false)
                .map(this::fromDocumentToEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public Employee findById(String id) {
        Document document = employeeCollection
                .find(Filters.eq("id", id))
                .first();

        return document != null ? fromDocumentToEmployee(document) : null;
    }

    @Override
    public void save(Employee employee) {
        employeeCollection.insertOne(toDocument(employee));
    }

    @Override
    public void delete(String id) {
        employeeCollection.deleteOne(Filters.eq("id", id));
    }

    

    private Employee fromDocumentToEmployee(Document document) {
        return new Employee(
                document.getString("id"),
                document.getString("name")
        );
    }

    private Document toDocument(Employee employee) {
        return new Document()
                .append("id", employee.getId())
                .append("name", employee.getName());
    }
}
