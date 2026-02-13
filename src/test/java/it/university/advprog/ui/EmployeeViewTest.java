package it.university.advprog.ui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.university.advprog.controller.EmployeeController;

@RunWith(GUITestRunner.class)
public class EmployeeViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private EmployeeController controller;

    @BeforeClass
    public static void installRepaintManager() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Override
    protected void onSetUp() {
        controller = mock(EmployeeController.class);

        EmployeeView view = GuiActionRunner.execute(() -> {
            EmployeeView v = new EmployeeView(controller);
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return v;
        });

        window = new FrameFixture(robot(), view);
        window.show();
    }

    @After
    public void afterEachTest() {
        if (window != null) {
            window.cleanUp(); // FrameFixture cleanup
            window = null;
        }
        controller = null;
    }

    @Test
    public void shouldHaveInitialControlsDisabledOrEnabledCorrectly() {
        window.button("btnAddEmployee").requireDisabled();
        window.button("btnRemoveEmployee").requireDisabled();
    }

    @Test
    public void shouldEnableAddButtonWhenIdAndNameAreProvided() {
        window.textBox("idTextBox").setText("1");
        window.textBox("nameTextBox").setText("Alice");
        window.button("btnAddEmployee").requireEnabled();
    }

    @Test
    public void shouldKeepAddButtonDisabledIfEitherFieldIsBlank() {
        window.textBox("idTextBox").setText("1");
        window.button("btnAddEmployee").requireDisabled();

        window.textBox("idTextBox").setText("");
        window.textBox("nameTextBox").setText("Alice");
        window.button("btnAddEmployee").requireDisabled();
    }

    @Test
    public void shouldEnableDeleteButtonOnlyWhenEmployeeIdIsProvided() {
        window.textBox("idTextBox").setText("1");
        window.button("btnRemoveEmployee").requireEnabled();

        window.textBox("idTextBox").setText("");
        window.button("btnRemoveEmployee").requireDisabled();
    }

    @Test
    public void shouldClearFieldsAndDisableAddButtonAfterAddClick() {
        window.textBox("idTextBox").setText("1");
        window.textBox("nameTextBox").setText("Alice");

        window.button("btnAddEmployee").requireEnabled();
        window.button("btnAddEmployee").click();

        // ✅ Ensure the Swing EDT has processed the click and UI updates (important on CI/Xvfb)
        robot().waitForIdle();

        window.textBox("idTextBox").requireText("");
        window.textBox("nameTextBox").requireText("");
        window.button("btnAddEmployee").requireDisabled();
    }

    @Test
    public void shouldDelegateAddEmployeeToController() {
        window.textBox("idTextBox").setText("1");
        window.textBox("nameTextBox").setText("Alice");

        window.button("btnAddEmployee").requireEnabled();
        window.button("btnAddEmployee").click();

        // ✅ Avoid CI flakiness: wait for EDT before verifying
        robot().waitForIdle();

        verify(controller, timeout(1000)).addEmployee("1", "Alice");
    }

    @Test
    public void shouldDelegateRemoveEmployeeToControllerWhenDeleteClicked() {
        window.textBox("idTextBox").setText("1");

        window.button("btnRemoveEmployee").requireEnabled();
        window.button("btnRemoveEmployee").click();

        robot().waitForIdle();

        verify(controller, timeout(1000)).removeEmployee("1");
    }
}