package it.university.advprog.repository.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.Test;

class EmployeeMongoRepositoryIT {

    @Test
    void shouldFailBecauseRepositoryIsNotImplementedYet() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");

        // RED phase: class does not exist yet
        new EmployeeMongoRepository(client);
    }
}
