package it.university.advprog.ui;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.mockito.Mockito.*;

class EmployeeControllerImplTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private EmployeeViewInterface view;

    private EmployeeControllerImpl controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new EmployeeControllerImpl(repository);
        controller.setEmployeeView(view);
    }

    @Test
    void shouldDelegateAddEmployeeToRepository() {
        controller.addEmployee("1", "Alice");

        verify(repository).save(new Employee("1", "Alice"));
        verify(view).employeeAdded(new Employee("1", "Alice"));
        verify(view).showError("");
    }

    @Test
    void shouldDelegateRemoveEmployeeToRepository() {
        controller.removeEmployee("1");

        verify(repository).delete("1");
        verify(view).employeeRemoved("1");
        verify(view).showError("");
    }

    @Test
    void allEmployeesShouldShowAllEmployeesAndClearError() {
        List<Employee> employees = List.of(
                new Employee("1", "Alice"),
                new Employee("2", "Bob")
        );
        when(repository.findAll()).thenReturn(employees);

        controller.allEmployees();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).employeeAdded(new Employee("1", "Alice"));
        inOrder.verify(view).employeeAdded(new Employee("2", "Bob"));
        inOrder.verify(view).showError("");

        verify(view, never()).employeeRemoved(anyString());
    }

    @Test
    void allEmployeesShouldShowErrorWhenRepositoryFails() {
        when(repository.findAll()).thenThrow(new RuntimeException("db down"));

        controller.allEmployees();

        verify(view).showError("Cannot load employees: db down");
        verify(view, never()).employeeAdded(any());
    }

    @Test
    void addEmployeeShouldShowErrorWhenRepositoryFails() {
        doThrow(new RuntimeException("duplicate"))
                .when(repository).save(new Employee("1", "Alice"));

        controller.addEmployee("1", "Alice");

        verify(view).showError("Cannot add employee: duplicate");
        verify(view, never()).employeeAdded(any());
    }

    @Test
    void removeEmployeeShouldShowErrorWhenRepositoryFails() {
        doThrow(new RuntimeException("not found"))
                .when(repository).delete("1");

        controller.removeEmployee("1");

        verify(view).showError("Cannot remove employee: not found");
        verify(view, never()).employeeRemoved(anyString());
    }
}