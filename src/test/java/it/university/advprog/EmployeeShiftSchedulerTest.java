package it.university.advprog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeShiftSchedulerTest {

    @Test
    void schedulerShouldStartWithNoScheduledShifts() {
        EmployeeShiftScheduler scheduler = new EmployeeShiftScheduler();
        assertEquals(0, scheduler.getNumberOfScheduledShifts());
        
        @Test
        void schedulerShouldContainOneShiftAfterAddingAShift() {
            EmployeeShiftScheduler scheduler = new EmployeeShiftScheduler();

            scheduler.addShift();

            assertEquals(1, scheduler.getNumberOfScheduledShifts());
        }

}
