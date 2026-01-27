package it.university.advprog.e2e;

import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

@Disabled("E2E Test disabled on Windows – external MongoDB used instead of Testcontainers")
public class EmployeeSwingAppE2E extends AssertJSwingJUnitTestCase {

    private static final String DB_NAME = "employee_e2e_db";
    private static final String COLLECTION_NAME = "employees";
    private static final String MONGO_URI = "mongodb://localhost:27017";

    private MongoClient mongoClient;
    private FrameFixture window;

    @Override
    protected void onSetUp() {
        mongoClient = MongoClients.create(MONGO_URI);

        mongoClient.getDatabase(DB_NAME).drop();

        insertEmployee("1", "Alice");
        insertEmployee("2", "Bob");

        application("it.university.advprog.EmployeeSwingApp")
                .withArgs(
                        "--db-name=" + DB_NAME,
                        "--db-collection=" + COLLECTION_NAME
                )
                .start();

        window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return "Employee View".equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(BasicRobot.robotWithCurrentAwtHierarchy());
    }

    @Override
    protected void onTearDown() {
        if (window != null) {
            window.cleanUp();
        }
        mongoClient.close();
    }

    @Test
    void testEmployeesFromDatabaseAreShownAtStartup() {
        assertThat(window.list().contents())
                .anySatisfy(e -> assertThat(e).contains("1", "Alice"))
                .anySatisfy(e -> assertThat(e).contains("2", "Bob"));
    }

    private void insertEmployee(String id, String name) {
        mongoClient
                .getDatabase(DB_NAME)
                .getCollection(COLLECTION_NAME)
                .insertOne(
                        new Document()
                                .append("id", id)
                                .append("name", name)
                );
    }
}
