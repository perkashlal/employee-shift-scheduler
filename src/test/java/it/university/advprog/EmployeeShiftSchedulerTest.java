package it.university.advprog;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeShiftSchedulerTest {

    private EmployeeShiftScheduler scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new EmployeeShiftScheduler();
    }

    @Test
    void schedulerShouldStartWithNoScheduledShifts() {
        assertEquals(0, scheduler.getNumberOfScheduledShifts());
    }

    @Test
    void schedulerShouldContainOneShiftAfterAddingAShift() {
        scheduler.addShift();
        assertEquals(1, scheduler.getNumberOfScheduledShifts());
        
    }
    @Test
    void schedulerShouldContainOneShiftAfterRemovingAShift() {
        scheduler.addShift();
        scheduler.addShift();

        scheduler.removeShift();

        assertEquals(1, scheduler.getNumberOfScheduledShifts());
    }

}
