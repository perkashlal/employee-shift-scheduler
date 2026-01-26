package it.university.advprog.ui;

import static org.mockito.Mockito.verify;

import it.university.advprog.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EmployeeControllerImplTest {

    private EmployeeRepository repository;
    private EmployeeControllerImpl controller;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(EmployeeRepository.class);
        controller = new EmployeeControllerImpl(repository);
    }

    @Test
    void shouldDelegateAddEmployeeToRepository() {
        controller.addEmployee("001", "perkash");

        verify(repository).save("002", "pika's");
    }

    @Test
    void shouldDelegateRemoveEmployeeToRepository() {
        controller.removeEmployee("001");

        verify(repository).delete("001");
    }
}
