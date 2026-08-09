package edu.ug.smartdelivery.experiment;

import edu.ug.smartdelivery.algorithm.sort.InsertionSort;
import edu.ug.smartdelivery.algorithm.sort.MergeSort;
import edu.ug.smartdelivery.algorithm.sort.QuickSort;
import edu.ug.smartdelivery.algorithm.sort.SelectionSort;
import edu.ug.smartdelivery.util.MemoryTracker;
import edu.ug.smartdelivery.util.Timer;
import java.util.Comparator;

public class SortingExperiment {
    private final SelectionSort selectionSort = new SelectionSort();
    private final InsertionSort insertionSort = new InsertionSort();
    private final MergeSort mergeSort = new MergeSort();
    private final QuickSort quickSort = new QuickSort();
    private final Timer timer = new Timer();
    private final MemoryTracker memoryTracker = new MemoryTracker();

    public ExperimentMeasurement[] run(int[] inputSizes, int trials) {
        ExperimentMeasurement[] results = new ExperimentMeasurement[inputSizes.length * trials * 4];
        int position = 0;
        for (int inputSize : inputSizes) {
            Integer[] base = generatedValues(inputSize);
            for (int trial = 1; trial <= trials; trial++) {
                results[position++] = measure("Selection Sort", inputSize, trial,
                        () -> selectionSort.sort(copyOf(base), Comparator.naturalOrder()));
                results[position++] = measure("Insertion Sort", inputSize, trial,
                        () -> insertionSort.sort(copyOf(base), Comparator.naturalOrder()));
                results[position++] = measure("Merge Sort", inputSize, trial,
                        () -> mergeSort.sort(copyOf(base), Comparator.naturalOrder()));
                results[position++] = measure("Quick Sort", inputSize, trial,
                        () -> quickSort.sort(copyOf(base), Comparator.naturalOrder()));
            }
        }
        return results;
    }

    private Integer[] generatedValues(int size) {
        Integer[] values = new Integer[size];
        for (int i = 0; i < size; i++) {
            values[i] = Math.floorMod(size * 37 - i * 19, Math.max(1, size * 3));
        }
        return values;
    }

    private Integer[] copyOf(Integer[] values) {
        Integer[] copy = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            copy[i] = values[i];
        }
        return copy;
    }

    private ExperimentMeasurement measure(String algorithmName, int inputSize, int trial, Runnable operation) {
        double beforeMemory = memoryTracker.usedMemoryKb();
        timer.start();
        operation.run();
        long elapsed = timer.elapsedNanos();
        double memoryKb = Math.max(0, memoryTracker.usedMemoryKb() - beforeMemory);
        return new ExperimentMeasurement(algorithmName, inputSize, elapsed, memoryKb, trial);
    }
}
