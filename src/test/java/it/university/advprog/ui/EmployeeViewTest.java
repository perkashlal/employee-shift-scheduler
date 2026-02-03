package it.university.advprog.ui;

import static org.mockito.Mockito.verify;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(GUITestRunner.class)
public class EmployeeViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        EmployeeView view = GuiActionRunner.execute(() -> new EmployeeView());
        show(view);
    }

    private void show(EmployeeView view) {
        if (window != null) {
            window.cleanUp();
        }
        window = new FrameFixture(robot(), view);
        window.show();
    }

    @Override
    protected void onTearDown() {
        if (window != null) {
            window.cleanUp();
            window = null;
        }
    }

    @Test
    public void shouldShowEmployeeViewWindow() {
        window.requireVisible();
    }

    @Test
    public void shouldHaveInitialControlsDisabledOrEnabledCorrectly() {
        window.label("lblEmployeeId").requireText("Employee ID");
        window.label("lblEmployeeName").requireText("Employee Name");

        window.textBox("txtEmployeeId").requireEnabled();
        window.textBox("txtEmployeeName").requireEnabled();

        window.button("btnAddEmployee").requireDisabled();
        window.button("btnRemoveEmployee").requireDisabled();
    }

    @Test
    public void shouldEnableAddButtonWhenIdAndNameAreProvided() {
        window.textBox("txtEmployeeId").enterText("002");
        window.textBox("txtEmployeeName").enterText("perkash");

        window.button("btnAddEmployee").requireEnabled();
    }

    @Test
    public void shouldKeepAddButtonDisabledIfEitherFieldIsBlank() {
        window.textBox("txtEmployeeId").setText("003");
        window.textBox("txtEmployeeName").setText("");
        window.button("btnAddEmployee").requireDisabled();

        window.textBox("txtEmployeeId").setText("");
        window.textBox("txtEmployeeName").setText("perkash");
        window.button("btnAddEmployee").requireDisabled();

        window.textBox("txtEmployeeId").setText(" ");
        window.textBox("txtEmployeeName").setText("sunil");
        window.button("btnAddEmployee").requireDisabled();
    }

    @Test
    public void shouldClearFieldsAndDisableAddButtonAfterAddClick() {
        window.textBox("txtEmployeeId").enterText("001");
        window.textBox("txtEmployeeName").enterText("perkash");

        window.button("btnAddEmployee").click();

        window.textBox("txtEmployeeId").requireText("");
        window.textBox("txtEmployeeName").requireText("");
        window.button("btnAddEmployee").requireDisabled();
    }

    @Test
    public void shouldDelegateAddEmployeeToController() {
        EmployeeController controller = Mockito.mock(EmployeeController.class);

        EmployeeView view = GuiActionRunner.execute(() -> {
            EmployeeView v = new EmployeeView();
            v.setEmployeeController(controller);
            return v;
        });

        FrameFixture localWindow = new FrameFixture(robot(), view);
        localWindow.show();

        localWindow.textBox("txtEmployeeId").setText("001");
        localWindow.textBox("txtEmployeeName").setText("Pika's");

        localWindow.button("btnAddEmployee").click();

        verify(controller).addEmployee("001", "Pika's");

        localWindow.cleanUp();
    }


    @Test
    public void shouldEnableDeleteButtonOnlyWhenEmployeeIdIsProvided() {
        window.button("btnRemoveEmployee").requireDisabled();

        window.textBox("txtEmployeeId").setText("123");
        window.button("btnRemoveEmployee").requireEnabled();

        window.textBox("txtEmployeeId").setText("");
        window.button("btnRemoveEmployee").requireDisabled();
    }

    @Test
    public void shouldDelegateRemoveEmployeeToControllerWhenDeleteClicked() {
        EmployeeController controller = Mockito.mock(EmployeeController.class);

        EmployeeView view = GuiActionRunner.execute(() -> {
            EmployeeView v = new EmployeeView();
            v.setEmployeeController(controller);
            return v;
        });

        FrameFixture localWindow = new FrameFixture(robot(), view);
        localWindow.show();

        localWindow.textBox("txtEmployeeId").enterText("007");

        localWindow.button("btnRemoveEmployee").click();

        verify(controller).removeEmployee("007");

        localWindow.cleanUp();
    }

}
