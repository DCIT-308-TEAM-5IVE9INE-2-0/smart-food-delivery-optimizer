package edu.ug.smartdelivery.datastructure.array;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class CustomDynamicArrayTest {
    @Test
    void addAndGetValues() {
        CustomDynamicArray<String> array = new CustomDynamicArray<>(2);

        array.add("Legon");
        array.add("Madina");
        array.add("Haatso");

        assertEquals(3, array.size());
        assertEquals("Haatso", array.get(2));
        assertEquals(4, array.capacity());
    }

    @Test
    void removeShiftsValues() {
        CustomDynamicArray<Integer> array = new CustomDynamicArray<>();
        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(20, array.remove(1));
        assertEquals(30, array.get(1));
    }

    @Test
    void invalidIndexThrowsException() {
        CustomDynamicArray<Integer> array = new CustomDynamicArray<>();

        assertThrows(NoSuchElementException.class, () -> array.get(0));
    }
}
