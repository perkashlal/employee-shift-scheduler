package it.university.advprog.repository.mongo;
import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;



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

        if (document == null) {
            return null;
        }

        return fromDocumentToEmployee(document);
    }

    @Override
    public void save(Employee employee) {
        employeeCollection.insertOne(
                new Document()
                        .append("id", employee.getId())
                        .append("name", employee.getName())
        );
    }

    @Override
    public void delete(String id) {
        employeeCollection.deleteOne(Filters.eq("id", id));
    }

    private Employee fromDocumentToEmployee(Document document) {
        return new Employee(
                String.valueOf(document.get("id")),
                String.valueOf(document.get("name"))
        );
    }
}
