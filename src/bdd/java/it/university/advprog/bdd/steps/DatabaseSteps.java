package it.university.advprog.bdd.steps;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class DatabaseSteps {

    static final String MONGO_HOST = "localhost";
    static final int MONGO_PORT = 27017;

    static final String DB_NAME = "employee-db";
    static final String COLLECTION_NAME = "employees";

    private MongoClient mongoClient;

    @Before
    public void setUp() {
        mongoClient = MongoClients.create("mongodb://" + MONGO_HOST + ":" + MONGO_PORT);
        mongoClient.getDatabase(DB_NAME).drop();
    }

    @After
    public void tearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Given("the database contains the following employees")
    public void the_database_contains_the_following_employees(List<Map<String, String>> rows) {
        rows.forEach(r -> mongoClient
                .getDatabase(DB_NAME)
                .getCollection(COLLECTION_NAME)
                .insertOne(new Document("_id", r.get("id").trim())
                        .append("name", r.get("name").trim())));
    }
}