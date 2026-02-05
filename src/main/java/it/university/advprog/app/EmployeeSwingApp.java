package it.university.advprog.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Callable;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import it.university.advprog.repository.EmployeeRepository;
import it.university.advprog.repository.mongo.EmployeeMongoRepository;
import it.university.advprog.ui.EmployeeController;
import it.university.advprog.ui.EmployeeControllerImpl;
import it.university.advprog.ui.EmployeeView;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class EmployeeSwingApp implements Callable<Integer> {

    @Option(names = "--mongo-host")
    private String mongoHost = "localhost";

    @Option(names = "--mongo-port")
    private int mongoPort = 27017;

    @Option(names = "--db-name")
    private String databaseName = "employee-db";

    @Option(names = "--db-collection")
    private String collectionName = "employees";

    public static void main(String[] args) {
        new CommandLine(new EmployeeSwingApp()).execute(args);
    }

    @Override
    public Integer call() {
        MongoClient mongoClient = MongoClients.create("mongodb://" + mongoHost + ":" + mongoPort);

        SwingUtilities.invokeLater(() -> {
            try {
                EmployeeRepository repo =
                        new EmployeeMongoRepository(mongoClient, databaseName, collectionName);

                EmployeeController controller = new EmployeeControllerImpl(repo);
                EmployeeView view = new EmployeeView();

                controller.setEmployeeView(view);
                view.setEmployeeController(controller);

                // IMPORTANT for tests: NEVER EXIT_ON_CLOSE
                view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                view.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mongoClient.close();
                    }
                });

                view.setVisible(true);
                controller.allEmployees();

            } catch (Exception e) {
                e.printStackTrace();
                mongoClient.close();
                // DO NOT call System.exit(...)
            }
        });

        return 0;
    }
}