package it.university.advprog.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeMongoRepositoryIT {

    private static final String DB_NAME = "employee-repository-it";
    private static final String COLLECTION = "employees";

    private MongoClient mongoClient;
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");

        // IMPORTANT: clean DB before each test
        mongoClient.getDatabase(DB_NAME).drop();

        repository = new EmployeeMongoRepository(
                mongoClient,
                DB_NAME,
                COLLECTION
        );
    }

    @AfterEach
    void tearDown() {
        mongoClient.getDatabase(DB_NAME).drop();
        mongoClient.close();
    }

    @Test
    void shouldSaveAndLoadEmployee() {
        Employee employee = new Employee("e1", "Alice");

        repository.save(employee);

        assertThat(repository.findAll())
                .hasSize(1)
                .contains(employee);
    }
}
