package it.university.advprog.repository.mongo;

public final class MongoConfig {

    private MongoConfig() {}

    public static String host() {
        return get("MONGO_HOST", "localhost");
    }

    public static int port() {
        return Integer.parseInt(get("MONGO_PORT", "27017"));
    }

    public static String database() {
        return get("MONGO_DB", "employee-db");
    }

    public static String collection() {
        return get("MONGO_COLLECTION", "employees");
    }

    private static String get(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}