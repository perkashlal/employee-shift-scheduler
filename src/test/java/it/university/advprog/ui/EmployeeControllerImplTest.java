package it.university.advprog.ui;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    }

    @Test
    void shouldDelegateRemoveEmployeeToRepository() {
        controller.removeEmployee("1");

        verify(repository).delete("1");
        verify(view).employeeRemoved("1");
    }
}
