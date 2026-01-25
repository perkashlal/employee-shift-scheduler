package it.university.advprog.ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GUITestRunner.class)
public class EmployeeViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        EmployeeView view = GuiActionRunner.execute(EmployeeView::new);
        window = new FrameFixture(robot(), view);
        window.show();
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
		
		    
		
		}
