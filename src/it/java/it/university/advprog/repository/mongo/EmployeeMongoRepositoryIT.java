package it.university.advprog.repository.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import it.university.advprog.Employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMongoRepositoryIT {

    private final MongoClient client =
            MongoClients.create("mongodb://localhost:27017");

    private final EmployeeMongoRepository repository =
            new EmployeeMongoRepository(client);

    @AfterEach
    void cleanUp() {
        client.getDatabase("employee-test").drop();
        client.close();
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
