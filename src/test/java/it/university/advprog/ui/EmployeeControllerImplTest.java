package it.university.advprog.ui;

import static org.mockito.Mockito.verify;

import it.university.advprog.Employee;
import it.university.advprog.repository.EmployeeRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class EmployeeControllerImplTest {

    private EmployeeRepository repository;
    private EmployeeControllerImpl controller;

    @Before
    public void setUp() {
        repository = Mockito.mock(EmployeeRepository.class);
        controller = new EmployeeControllerImpl(repository);
    }

    @Test
    public void shouldDelegateAddEmployeeToRepository() {
       
        controller.addEmployee("001", "Alice");

        verify(repository).save(new Employee("001", "Alice"));
    }

    @Test
    public void shouldDelegateRemoveEmployeeToRepository() {
        
        controller.removeEmployee("001");

        verify(repository).delete("001");
    }
}