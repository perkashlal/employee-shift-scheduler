package it.university.advprog.repository.mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.junit.jupiter.api.Test;

class EmployeeMongoRepositoryIT {

    @Test
    void shouldFailWhenMongoIsNotRunning() {
        // real MongoDB connection (not running yet)
        MongoClient client = new MongoClient(
                new ServerAddress("localhost", 27017)
        );

        // try to use the repository
        new EmployeeMongoRepository(client).findAll();
    }
}
