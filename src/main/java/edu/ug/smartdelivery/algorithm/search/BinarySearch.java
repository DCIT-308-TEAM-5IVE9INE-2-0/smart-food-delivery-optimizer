package edu.ug.smartdelivery.algorithm.search;

import edu.ug.smartdelivery.datastructure.TraceStep;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public <T> List<TraceStep> trace(T[] sortedValues, T target, Comparator<T> comparator) {
        Objects.requireNonNull(sortedValues, "sortedValues");
        Objects.requireNonNull(comparator, "comparator");
        List<TraceStep> trace = new ArrayList<>();
        int low = 0;
        int high = sortedValues.length - 1;
        int step = 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = comparator.compare(sortedValues[mid], target);
            trace.add(new TraceStep(step++, "check mid=" + mid, "low=" + low + ", high=" + high + ", value=" + sortedValues[mid]));
            if (comparison == 0) {
                trace.add(new TraceStep(step, "target found", "index=" + mid));
                return trace;
            }
            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        trace.add(new TraceStep(step, "target not found", "low=" + low + ", high=" + high));
        return trace;
    }
}
