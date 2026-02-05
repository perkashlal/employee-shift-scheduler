package it.university.advprog.ui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeViewTest {

    private Robot robot;
    private FrameFixture window;

    private EmployeeView view;
    private EmployeeController controller;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        controller = mock(EmployeeController.class);

        view = GuiActionRunner.execute(() -> {
            EmployeeView v = new EmployeeView(controller);
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return v;
        });

        window = new FrameFixture(robot, view);
        window.show();
    }

    @After
    public void tearDown() {
        if (window != null) {
            window.cleanUp();
            window = null;
        }
        if (robot != null) {
            robot.cleanUp();
            robot = null;
        }
        view = null;
        controller = null;
    }

    @Test
    public void shouldHaveInitialControlsDisabledOrEnabledCorrectly() {
        window.button("btnAddEmployee").requireDisabled();
        window.button("btnRemoveEmployee").requireDisabled();
    }

    @Test
    public void shouldEnableAddButtonWhenIdAndNameAreProvided() {
        window.textBox("idTextBox").enterText("1");
        window.textBox("nameTextBox").enterText("Alice");
        window.button("btnAddEmployee").requireEnabled();
    }

    @Test
    public void shouldKeepAddButtonDisabledIfEitherFieldIsBlank() {
        window.textBox("idTextBox").enterText("1");
        window.button("btnAddEmployee").requireDisabled();

        window.textBox("idTextBox").setText("");
        window.textBox("nameTextBox").enterText("Alice");
        window.button("btnAddEmployee").requireDisabled();
    }

    @Test
    public void shouldEnableDeleteButtonOnlyWhenEmployeeIdIsProvided() {
        window.textBox("idTextBox").enterText("1");
        window.button("btnRemoveEmployee").requireEnabled();

        window.textBox("idTextBox").setText("");
        window.button("btnRemoveEmployee").requireDisabled();
    }

    @Test
    public void shouldClearFieldsAndDisableAddButtonAfterAddClick() {
        window.textBox("idTextBox").enterText("1");
        window.textBox("nameTextBox").enterText("Alice");

        window.button("btnAddEmployee").click();

        window.textBox("idTextBox").requireText("");
        window.textBox("nameTextBox").requireText("");
        window.button("btnAddEmployee").requireDisabled();
    }

    @Test
    public void shouldDelegateAddEmployeeToController() {
        window.textBox("idTextBox").enterText("1");
        window.textBox("nameTextBox").enterText("Alice");

        window.button("btnAddEmployee").click();

        verify(controller).addEmployee("1", "Alice");
    }

    @Test
    public void shouldDelegateRemoveEmployeeToControllerWhenDeleteClicked() {
        window.textBox("idTextBox").enterText("1");

        window.button("btnRemoveEmployee").click();

        verify(controller).removeEmployee("1");
    }
}