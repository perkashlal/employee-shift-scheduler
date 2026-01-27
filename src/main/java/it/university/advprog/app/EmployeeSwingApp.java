package it.university.advprog.app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import it.university.advprog.repository.EmployeeRepository;
import it.university.advprog.repository.mongo.EmployeeMongoRepository;
import it.university.advprog.ui.EmployeeController;
import it.university.advprog.ui.EmployeeControllerImpl;
import it.university.advprog.ui.EmployeeView;

import javax.swing.SwingUtilities;

public class EmployeeSwingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MongoClient mongoClient =
                        MongoClients.create("mongodb://localhost:27017");

                EmployeeRepository employeeRepository =
                        new EmployeeMongoRepository(mongoClient);

                EmployeeView employeeView = new EmployeeView();

                EmployeeController employeeController =
                        new EmployeeControllerImpl(employeeRepository);

                employeeController.setEmployeeView(employeeView);
                employeeView.setEmployeeController(employeeController);

                employeeView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
