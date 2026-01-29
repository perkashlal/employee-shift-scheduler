package it.university.advprog.repository.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;
import org.bson.Document;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class EmployeeMongoRepository implements EmployeeRepository {

    private final MongoCollection<Document> collection;

    public EmployeeMongoRepository(
            MongoClient client,
            String databaseName,
            String collectionName
    ) {
        MongoDatabase db = client.getDatabase(databaseName);
        this.collection = db.getCollection(collectionName);
    }

    @Override
    public void save(Employee employee) {
        collection.insertOne(new Document()
                .append("_id", employee.id())
                .append("name", employee.name()));
    }

    @Override
    public Optional<Employee> findById(String id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc == null
                ? Optional.empty()
                : Optional.of(new Employee(doc.getString("_id"), doc.getString("name")));
    }

    @Override
    public List<Employee> findAll() {
        return collection.find().map(
                d -> new Employee(d.getString("_id"), d.getString("name"))
        ).into(new java.util.ArrayList<>());
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(new Document("_id", id));
    }
}