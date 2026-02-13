package it.university.advprog.repository.mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import it.university.advprog.model.Employee;
import it.university.advprog.repository.EmployeeRepository;

import org.bson.Document;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class EmployeeMongoRepository implements EmployeeRepository {

    private final MongoCollection<Document> collection;

    public EmployeeMongoRepository(
            MongoClient client,
            String databaseName,
            String collectionName
    ) {
        this.collection =
                client.getDatabase(databaseName)
                      .getCollection(collectionName);
    }

    @Override
    public void save(Employee employee) {
        collection.insertOne(
                new Document("_id", employee.id())
                        .append("name", employee.name())
        );
    }

    @Override
    public Optional<Employee> findById(String id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc == null
                ? Optional.empty()
                : Optional.of(
                        new Employee(
                                doc.getString("_id"),
                                doc.getString("name")
                        )
                );
    }

    @Override
    public List<Employee> findAll() {
        return collection.find()
                .map(d -> new Employee(
                        d.getString("_id"),
                        d.getString("name")
                ))
                .into(new ArrayList<>());
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(new Document("_id", id));
    }
}
