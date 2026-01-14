package it.university.advprog;

public class EmployeeShiftScheduler {

    private int scheduledShifts = 0;

    public int getNumberOfScheduledShifts() {
        return scheduledShifts;
    }

    public void addShift() {
        scheduledShifts++;
    }
}
