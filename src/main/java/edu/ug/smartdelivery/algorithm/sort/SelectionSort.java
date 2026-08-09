package edu.ug.smartdelivery.algorithm.sort;

import java.util.Comparator;
import java.util.Objects;

public class SelectionSort {
    public <T> void sort(T[] values, Comparator<T> comparator) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(comparator, "comparator");
        for (int i = 0; i < values.length - 1; i++) {
            int selectedIndex = i;
            for (int j = i + 1; j < values.length; j++) {
                if (comparator.compare(values[j], values[selectedIndex]) < 0) {
                    selectedIndex = j;
                }
            }
            swap(values, i, selectedIndex);
        }
    }

    private <T> void swap(T[] values, int first, int second) {
        if (first == second) {
            return;
        }
        T temp = values[first];
        values[first] = values[second];
        values[second] = temp;
    }
}
