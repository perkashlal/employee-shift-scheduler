package it.university.advprog.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.timing.Condition;
import org.assertj.swing.timing.Pause;
import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@RunWith(GUITestRunner.class)
public class EmployeeSwingAppE2E extends AssertJSwingJUnitTestCase {

    @BeforeClass
    public static void installEdtCheck() {
        FailOnThreadViolationRepaintManager.install();
    }

    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

    private static final String DB_NAME = "employee_e2e_db";
    private static final String COLLECTION_NAME = "employees";

    private MongoClient mongoClient;
    private FrameFixture window;

    @Override
    protected void onSetUp() {
        mongoClient = MongoClients.create(mongo.getReplicaSetUrl());
        mongoClient.getDatabase(DB_NAME).drop();

        insertEmployee("1", "Alice");
        insertEmployee("2", "Bob");

        application("it.university.advprog.app.EmployeeSwingApp")
                .withArgs(
                        "--mongo-host=" + mongo.getHost(),
                        "--mongo-port=" + mongo.getFirstMappedPort(),
                        "--db-name=" + DB_NAME,
                        "--db-collection=" + COLLECTION_NAME
                )
                .start();

        window = WindowFinder.findFrame(JFrame.class)
                .withTimeout(10_000)
                .using(robot());

        // wait until list has 2 items (prevents timing flakiness)
        Pause.pause(new Condition("employee list contains at least 2 items") {
            @Override
            public boolean test() {
                try {
                    return window.list("employeeList").contents().length >= 2;
                } catch (Exception e) {
                    return false;
                }
            }
        }, 10_000);
    }

    @Override
    protected void onTearDown() {
        if (window != null) {
            try {
                GuiActionRunner.execute(() -> window.target().dispose());
                window.cleanUp();
            } catch (Exception ignored) {
            }
            window = null;
        }

        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }

        Pause.pause(200);
    }

    @Test
    public void testEmployeesFromDatabaseAreShownAtStartup() {
        String[] rows = window.list("employeeList").contents();

        assertThat(rows).anySatisfy(r -> assertThat(r).contains("1").contains("Alice"));
        assertThat(rows).anySatisfy(r -> assertThat(r).contains("2").contains("Bob"));
    }

    private void insertEmployee(String id, String name) {
        mongoClient.getDatabase(DB_NAME)
                .getCollection(COLLECTION_NAME)
                .insertOne(new Document().append("_id", id).append("name", name));
    }
}