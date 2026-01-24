package it.university.advprog.repository.mongo;

public final class MongoConfig {

    public static final String HOST = "localhost";
    public static final int PORT = 27017;
    public static final String DATABASE = "employee-db";
    public static final String COLLECTION = "employees";

    private MongoConfig() {
        
    }
}
