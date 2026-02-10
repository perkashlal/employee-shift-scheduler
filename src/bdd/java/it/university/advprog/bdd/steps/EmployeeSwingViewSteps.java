package it.university.advprog.bdd.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.util.List;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeSwingViewSteps {

    private static final String MAIN_CLASS_FQN = "it.university.advprog.app.EmployeeSwingApp";

    private FrameFixture window;

    @After
    public void cleanUp() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @When("the Employee View is shown")
    public void the_Employee_View_is_shown() {
        application(MAIN_CLASS_FQN)
                .withArgs(
                        "--mongo-host=" + DatabaseSteps.MONGO_HOST,
                        "--mongo-port=" + DatabaseSteps.MONGO_PORT,
                        "--db-name=" + DatabaseSteps.DB_NAME,
                        "--db-collection=" + DatabaseSteps.COLLECTION_NAME
                )
                .start();

        window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return frame.isShowing();
            }
        }).using(BasicRobot.robotWithCurrentAwtHierarchy());
    }

    @Then("the list contains employees")
    public void the_list_contains_employees(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);

        for (List<String> row : rows) {
            String id = row.get(0).trim();
            String name = row.get(1).trim();

            assertThat(window.list().contents())
                    .anyMatch(e -> e.contains(id) && e.contains(name));
        }
    }
}