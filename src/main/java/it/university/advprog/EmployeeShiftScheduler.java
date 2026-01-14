package it.university.advprog;

public class EmployeeShiftScheduler {

    private int scheduledShifts;

    public int getNumberOfScheduledShifts() {
        return scheduledShifts;
    }

    public void addShift() {
        scheduledShifts++;
    }

    public void removeShift() {
        scheduledShifts--;
    }
}
