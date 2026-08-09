package edu.ug.smartdelivery.algorithm.sort;

import edu.ug.smartdelivery.datastructure.TraceStep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MergeSort {
    public <T> void sort(T[] values, Comparator<T> comparator) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(comparator, "comparator");
        if (values.length < 2) {
            return;
        }
        Object[] workspace = new Object[values.length];
        mergeSort(values, workspace, 0, values.length - 1, comparator, null);
    }

    public <T> List<TraceStep> trace(T[] values, Comparator<T> comparator) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(comparator, "comparator");
        List<TraceStep> trace = new ArrayList<>();
        trace.add(new TraceStep(0, "initial", Arrays.toString(values)));
        if (values.length >= 2) {
            Object[] workspace = new Object[values.length];
            mergeSort(values, workspace, 0, values.length - 1, comparator, trace);
        }
        return trace;
    }

    private <T> void mergeSort(T[] values, Object[] workspace, int left, int right, Comparator<T> comparator, List<TraceStep> trace) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(values, workspace, left, mid, comparator, trace);
        mergeSort(values, workspace, mid + 1, right, comparator, trace);
        merge(values, workspace, left, mid, right, comparator);
        if (trace != null) {
            trace.add(new TraceStep(trace.size(), "merge " + left + "-" + right, Arrays.toString(values)));
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void merge(T[] values, Object[] workspace, int left, int mid, int right, Comparator<T> comparator) {
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            if (comparator.compare(values[i], values[j]) <= 0) {
                workspace[k++] = values[i++];
            } else {
                workspace[k++] = values[j++];
            }
        }
        while (i <= mid) {
            workspace[k++] = values[i++];
        }
        while (j <= right) {
            workspace[k++] = values[j++];
        }
        for (int index = left; index <= right; index++) {
            values[index] = (T) workspace[index];
        }
    }
}
