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
}
