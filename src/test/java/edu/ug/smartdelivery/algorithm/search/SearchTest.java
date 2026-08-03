package edu.ug.smartdelivery.algorithm.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import org.junit.jupiter.api.Test;

class SearchTest {
    @Test
    void linearSearchFindsUnsortedValue() {
        String[] areas = {"Legon", "Madina", "Adenta"};

        assertEquals(1, new LinearSearch().find(areas, "Madina"));
    }

    @Test
    void binarySearchFindsSortedValue() {
        Integer[] orderIds = {10, 20, 30, 40};

        assertEquals(2, new BinarySearch().find(orderIds, 30, Comparator.naturalOrder()));
    }
}
