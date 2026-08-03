package edu.ug.smartdelivery.algorithm.sort;

import java.util.Comparator;
import java.util.Objects;

public class InsertionSort {
    public <T> void sort(T[] values, Comparator<T> comparator) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(comparator, "comparator");
        for (int i = 1; i < values.length; i++) {
            T key = values[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(values[j], key) > 0) {
                values[j + 1] = values[j];
                j--;
            }
            values[j + 1] = key;
        }
    }
}
