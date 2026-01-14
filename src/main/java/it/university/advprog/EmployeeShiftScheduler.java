package it.university.advprog;

public class EmployeeShiftScheduler {

    private int numberOfScheduledShifts = 0;

    public int getNumberOfScheduledShifts() {
        return numberOfScheduledShifts;
    }

    public void addShift() {
        numberOfScheduledShifts++;
    }
    
 public void removeShift() {
        numberOfScheduledShifts--;
    }
}
