package edu.ug.smartdelivery.algorithm.sort;

import java.util.Comparator;
import java.util.Objects;

public class QuickSort {
    public <T> void sort(T[] values, Comparator<T> comparator) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(comparator, "comparator");
        quickSort(values, 0, values.length - 1, comparator);
    }

    private <T> void quickSort(T[] values, int low, int high, Comparator<T> comparator) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(values, low, high, comparator);
        quickSort(values, low, pivotIndex - 1, comparator);
        quickSort(values, pivotIndex + 1, high, comparator);
    }

    private <T> int partition(T[] values, int low, int high, Comparator<T> comparator) {
        T pivot = values[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(values[j], pivot) <= 0) {
                i++;
                swap(values, i, j);
            }
        }
        swap(values, i + 1, high);
        return i + 1;
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
