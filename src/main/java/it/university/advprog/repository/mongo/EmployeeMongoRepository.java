package it.university.advprog.repository.mongo;

import com.mongodb.client.MongoClient;

public class EmployeeMongoRepository {

    private final MongoClient client;

    public EmployeeMongoRepository(MongoClient client) {
        this.client = client;
    }
}