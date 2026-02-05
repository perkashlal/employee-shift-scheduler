package it.university.advprog.ui;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;
import it.university.advprog.repository.mongo.EmployeeMongoRepository;

public class EmployeeViewIT extends AssertJSwingJUnitTestCase {

    private static final String DB_NAME = "employee-view-it";
    private static final String COLLECTION = "employees";

    private MongoServer mongoServer;
    private MongoClient mongoClient;
    private EmployeeRepository repository;

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        mongoServer = new MongoServer(new MemoryBackend());
        InetSocketAddress address = mongoServer.bind();

        mongoClient = MongoClients.create("mongodb://localhost:" + address.getPort());

        mongoClient.getDatabase(DB_NAME).drop();

        repository = new EmployeeMongoRepository(mongoClient, DB_NAME, COLLECTION);

        EmployeeView view = GuiActionRunner.execute(() -> {
            EmployeeView v = new EmployeeView();
            EmployeeController controller = new EmployeeControllerImpl(repository);
            controller.setEmployeeView(v);
            v.setEmployeeController(controller);
            return v;
        });

        window = new FrameFixture(robot(), view);
        window.show();
    }

    @Override
    protected void onTearDown() {
        if (window != null) {
            window.cleanUp();
            window = null;
        }
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
        if (mongoServer != null) {
            mongoServer.shutdown();
            mongoServer = null;
        }
    }

    @Test
    public void shouldAddEmployeeThroughUI() {
        window.textBox("idTextBox").setText("1");
        window.textBox("nameTextBox").setText("Alice");
        window.button("btnAddEmployee").click();

        assertThat(repository.findById("1"))
                .isPresent()
                .contains(new Employee("1", "Alice"));
    }

    @Test
    public void shouldRemoveEmployeeThroughUI() {
        repository.save(new Employee("2", "Bob"));

        window.textBox("idTextBox").setText("2");
        window.button("btnRemoveEmployee").click();

        assertThat(repository.findById("2")).isEmpty();
    }
}