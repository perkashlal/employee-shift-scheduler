package it.university.advprog.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeShiftScheduler {

    private final List<String> shifts = new ArrayList<>();

    public int getNumberOfScheduledShifts() {
        return shifts.size();
    }

    public void addShift() {
        shifts.add("UNSPECIFIED");
    }

   
    public void addShift(String shift) {
        shifts.add(shift);
    }

    public void updateShift(int index, String newShift) {
        shifts.set(index, newShift);
    }

    public void removeShift() {
        shifts.remove(shifts.size() - 1);
    }

    public List<String> getShifts() {
        return new ArrayList<>(shifts); // defensive copy
    }
}
