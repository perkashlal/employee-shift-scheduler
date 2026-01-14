package it.university.advprog;

import java.util.ArrayList;
import java.util.List;

public class EmployeeShiftScheduler {

    private int scheduledShifts;
    private final List<String> shifts = new ArrayList<>();

    public int getNumberOfScheduledShifts() {
        return scheduledShifts;
    }

   
    public void addShift() {
        scheduledShifts++;
    }

    public void addShift(String shift) {
        shifts.add(shift);
        scheduledShifts++;
    }

    public List<String> getShifts() {
        return shifts;
    }

    public void removeShift() {
        scheduledShifts--;
    }
}
