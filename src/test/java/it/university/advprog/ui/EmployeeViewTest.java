package it.university.advprog.ui;

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JFrame;

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
        JFrame frame = GuiActionRunner.execute(() ->
            new EmployeeView()
        );
        window = new FrameFixture(robot(), frame);
        window.show();
    }

    @Test
    public void should_show_employee_view_window() {
        assertThat(window.target().isVisible()).isTrue();
    }
}
