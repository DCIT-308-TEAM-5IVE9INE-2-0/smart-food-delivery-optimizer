package edu.ug.smartdelivery.algorithm.search;

import java.util.Comparator;
import java.util.Objects;

public class BinarySearch {
    public <T> int find(T[] sortedValues, T target, Comparator<T> comparator) {
        Objects.requireNonNull(sortedValues, "sortedValues");
        Objects.requireNonNull(comparator, "comparator");
        int low = 0;
        int high = sortedValues.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = comparator.compare(sortedValues[mid], target);
            if (comparison == 0) {
                return mid;
            }
            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
