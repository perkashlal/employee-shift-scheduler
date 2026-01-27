package it.university.advprog.ui;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;
import it.university.advprog.repository.mongo.EmployeeMongoRepository;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetSocketAddress;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeViewIT extends AssertJSwingJUnitTestCase {

    private static MongoServer server;
    private static InetSocketAddress serverAddress;

    private MongoClient mongoClient;
    private EmployeeRepository employeeRepository;

    private FrameFixture window;
    private EmployeeView employeeView;
    private EmployeeController employeeController;

    @BeforeClass
    public static void setupServer() {
        server = new MongoServer(new MemoryBackend());
        serverAddress = server.bind();
    }

    @AfterClass
    public static void shutdownServer() {
        server.shutdown();
    }

    @Override
    protected void onSetUp() {
        mongoClient = MongoClients.create(
                "mongodb://" + serverAddress.getHostName() + ":" + serverAddress.getPort()
        );

        employeeRepository = new EmployeeMongoRepository(mongoClient);

        for (Employee employee : employeeRepository.findAll()) {
            employeeRepository.delete(employee.id());
        }

        employeeView = GuiActionRunner.execute(() -> {
            EmployeeView view = new EmployeeView();
            employeeController = new EmployeeControllerImpl(employeeRepository);
            employeeController.setEmployeeView(view);
            view.setEmployeeController(employeeController);
            return view;
        });

        window = new FrameFixture(robot(), employeeView);
        window.show();
    }

    @Test
    public void testAddEmployeeThroughUI() {
        window.textBox("txtEmployeeId").enterText("1");
        window.textBox("txtEmployeeName").enterText("Alice");

        window.button("btnAddEmployee").click();

        assertThat(employeeRepository.findById("1"))
                .isPresent()
                .contains(new Employee("1", "Alice"));
    }

    @Test
    public void testRemoveEmployeeThroughUI() {
        employeeRepository.save(new Employee("2", "Bob"));

        window.textBox("txtEmployeeId").enterText("2");

        window.button("btnRemoveEmployee").click();

        assertThat(employeeRepository.findById("2")).isEmpty();
    }
}
