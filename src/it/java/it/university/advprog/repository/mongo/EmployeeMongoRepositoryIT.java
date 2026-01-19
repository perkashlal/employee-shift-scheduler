package it.university.advprog.repository.mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMongoRepositoryIT {

    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;

    private MongoClient client;
    private EmployeeMongoRepository repository;

    @BeforeEach
    void setUp() {
        client = new MongoClient(new ServerAddress(MONGO_HOST, MONGO_PORT));
        repository = new EmployeeMongoRepository(client);
    }

    @AfterEach
    void tearDown() {
        client.close();
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployeesExist() {
        assertThat(repository.findAll()).isEmpty();
    }
}
