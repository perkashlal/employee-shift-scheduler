package it.university.advprog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeShiftSchedulerTest {

    @Test
    void schedulerShouldStartWithNoScheduledShifts() {
        EmployeeShiftScheduler scheduler = new EmployeeShiftScheduler();
        assertEquals(0, scheduler.getNumberOfScheduledShifts());
    }
}
