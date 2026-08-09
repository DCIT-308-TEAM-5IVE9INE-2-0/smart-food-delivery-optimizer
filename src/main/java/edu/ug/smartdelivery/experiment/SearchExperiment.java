package edu.ug.smartdelivery.experiment;

import edu.ug.smartdelivery.algorithm.search.BinarySearch;
import edu.ug.smartdelivery.algorithm.search.LinearSearch;
import edu.ug.smartdelivery.util.MemoryTracker;
import edu.ug.smartdelivery.util.Timer;
import java.util.Comparator;

public class SearchExperiment {
    private final LinearSearch linearSearch = new LinearSearch();
    private final BinarySearch binarySearch = new BinarySearch();
    private final Timer timer = new Timer();
    private final MemoryTracker memoryTracker = new MemoryTracker();

    public ExperimentMeasurement[] run(int[] inputSizes, int trials) {
        ExperimentMeasurement[] results = new ExperimentMeasurement[inputSizes.length * trials * 2];
        int position = 0;
        for (int inputSize : inputSizes) {
            Integer[] values = ascendingValues(inputSize);
            Integer target = inputSize - 1;
            for (int trial = 1; trial <= trials; trial++) {
                results[position++] = measure("Linear Search", inputSize, trial, () -> linearSearch.find(values, target));
                results[position++] = measure("Binary Search", inputSize, trial, () -> binarySearch.find(values, target, Comparator.naturalOrder()));
            }
        }
        return results;
    }

    private Integer[] ascendingValues(int size) {
        Integer[] values = new Integer[size];
        for (int i = 0; i < size; i++) {
            values[i] = i;
        }
        return values;
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
