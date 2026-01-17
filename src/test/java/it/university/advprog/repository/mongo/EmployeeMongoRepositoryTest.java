package it.university.advprog.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import it.university.advprog.repository.EmployeeRepository;

class EmployeeMongoRepositoryTest {

    private static MongoServer server;
    private static InetSocketAddress serverAddress;

    private MongoClient client;
    private EmployeeRepository repository;

    @BeforeAll
    static void startMongo() {
        server = new MongoServer(new MemoryBackend());
        serverAddress = server.bind();
    }

    @BeforeEach
    void setup() {
        client = new MongoClient(new ServerAddress(serverAddress));
        repository = new EmployeeMongoRepository(client);
    }

    @AfterEach
    void tearDown() {
        client.close();
    }

    @Test
    void findAll_whenDatabaseIsEmpty_returnsEmptyList() {
        assertThat(repository.findAll()).isEmpty();
    }
}
