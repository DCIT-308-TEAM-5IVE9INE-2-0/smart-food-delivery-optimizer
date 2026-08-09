package edu.ug.smartdelivery.algorithm.sort;

import edu.ug.smartdelivery.datastructure.TraceStep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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

    public <T> List<TraceStep> trace(T[] values, Comparator<T> comparator) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(comparator, "comparator");
        List<TraceStep> trace = new ArrayList<>();
        trace.add(new TraceStep(0, "initial", Arrays.toString(values)));
        int step = 1;
        for (int i = 1; i < values.length; i++) {
            T key = values[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(values[j], key) > 0) {
                values[j + 1] = values[j];
                j--;
            }
            values[j + 1] = key;
            trace.add(new TraceStep(step++, "insert index " + i, Arrays.toString(values)));
        }
        return trace;
    }
}
