package it.university.advprog.repository.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class EmployeeMongoRepository implements EmployeeRepository {

    private final MongoCollection<Document> collection;

    public EmployeeMongoRepository(MongoClient client) {
        MongoDatabase database = client.getDatabase("employee-db");
        this.collection = database.getCollection("employees");
    }

    @Override
    public void save(Employee employee) {
        Document doc = new Document()
                .append("_id", employee.id())
                .append("name", employee.name());

        collection.insertOne(doc);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();

        for (Document doc : collection.find()) {
            result.add(new Employee(
                    doc.getString("_id"),
                    doc.getString("name")
            ));
        }
        return result;
    }

    @Override
    public Optional<Employee> findById(String id) {
        Document doc = collection.find(eq("_id", id)).first();

        if (doc == null) {
            return Optional.empty();
        }

        return Optional.of(new Employee(
                doc.getString("_id"),
                doc.getString("name")
        ));
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(eq("_id", id));
    }
}
