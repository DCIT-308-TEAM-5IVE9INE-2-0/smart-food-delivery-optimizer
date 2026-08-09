package edu.ug.smartdelivery.experiment;

import edu.ug.smartdelivery.datastructure.hash.CustomHashTable;
import edu.ug.smartdelivery.util.MemoryTracker;
import edu.ug.smartdelivery.util.Timer;

public class HashExperiment {
    private final Timer timer = new Timer();
    private final MemoryTracker memoryTracker = new MemoryTracker();

    public ExperimentMeasurement[] run(int[] inputSizes, int trials) {
        ExperimentMeasurement[] results = new ExperimentMeasurement[inputSizes.length * trials * 2];
        int position = 0;
        for (int inputSize : inputSizes) {
            for (int trial = 1; trial <= trials; trial++) {
                results[position++] = measure("Hash Table Insert", inputSize, trial, () -> {
                    CustomHashTable<Integer, Integer> table = new CustomHashTable<>();
                    for (int i = 0; i < inputSize; i++) {
                        table.put(i, i * 10);
                    }
                });
                results[position++] = measure("Hash Table Lookup", inputSize, trial, () -> {
                    CustomHashTable<Integer, Integer> table = filledTable(inputSize);
                    for (int i = 0; i < inputSize; i++) {
                        table.get(i);
                    }
                });
            }
        }
        return results;
    }

    private CustomHashTable<Integer, Integer> filledTable(int inputSize) {
        CustomHashTable<Integer, Integer> table = new CustomHashTable<>();
        for (int i = 0; i < inputSize; i++) {
            table.put(i, i * 10);
        }
        return table;
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
