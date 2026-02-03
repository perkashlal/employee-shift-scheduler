package it.university.advprog.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class EmployeeMongoRepositoryIT {

    private static final String DB_NAME = "employee-repository-it";
    private static final String COLLECTION = "employees";

    @Container
    static MongoDBContainer mongo =
            new MongoDBContainer("mongo:4.4.3");

    private static MongoClient mongoClient;
    private static EmployeeRepository repository;

    @BeforeAll
    static void setUp() {
        mongoClient = MongoClients.create(mongo.getConnectionString());

        repository = new EmployeeMongoRepository(
                mongoClient,
                DB_NAME,
                COLLECTION
        );
    }

    @AfterAll
    static void tearDown() {
        mongoClient.close();
    }

    @Test
    void shouldSaveAndLoadEmployee() {
        Employee employee = new Employee("e1", "Alice");

        repository.save(employee);

        assertThat(repository.findAll())
                .hasSize(1)
                .containsExactly(employee);
    }
}
