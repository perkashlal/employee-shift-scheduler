package it.university.advprog;

import org.junit.jupiter.api.Test;

import it.university.advprog.model.Employee;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldExposeIdAndName() {
        Employee e = new Employee("1", "Alice");
        assertEquals("1", e.id());
        assertEquals("Alice", e.name());
    }

    @Test
    void equalsAndHashCodeShouldWorkForSameValues() {
        Employee a1 = new Employee("1", "Alice");
        Employee a2 = new Employee("1", "Alice");
        Employee b = new Employee("2", "Bob");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());

        assertNotEquals(a1, b);
        assertNotEquals(a1, null);
        assertNotEquals(a1, "not-an-employee");
    }

    @Test
    void equalsShouldBeFalseWhenNameOrIdDiffers() {
        Employee base = new Employee("1", "Alice");
        assertNotEquals(base, new Employee("1", "Bob")); 
        assertNotEquals(base, new Employee("2", "Alice"));
    }
    @Test
    void equalsShouldShortCircuitWhenIdDiffersEvenIfNameIsSame() {
        Employee base = new Employee("1", "Alice");
        Employee sameNameDifferentId = new Employee("2", "Alice");

        assertNotEquals(base, sameNameDifferentId);
    }
    @Test
    void equalsShouldBeTrueForSameReference() {
        Employee e = new Employee("1", "Alice");
        assertEquals(e, e);
    }
}
