package edu.ug.smartdelivery.algorithm.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import org.junit.jupiter.api.Test;

class SortAlgorithmsTest {
    @Test
    void selectionSortOrdersValues() {
        Integer[] values = {5, 1, 4, 2};

        new SelectionSort().sort(values, Comparator.naturalOrder());

        assertArrayEquals(new Integer[] {1, 2, 4, 5}, values);
    }

    @Test
    void mergeSortOrdersValuesAndProducesTrace() {
        Integer[] values = {8, 3, 6, 1};
        MergeSort mergeSort = new MergeSort();

        var trace = mergeSort.trace(values, Comparator.naturalOrder());

        assertArrayEquals(new Integer[] {1, 3, 6, 8}, values);
        assertEquals("initial", trace.get(0).action());
    }

    @Test
    void quickSortOrdersValues() {
        Integer[] values = {9, 2, 7, 2};

        new QuickSort().sort(values, Comparator.naturalOrder());

        assertArrayEquals(new Integer[] {2, 2, 7, 9}, values);
    }
}
