package edu.ug.smartdelivery.experiment;

import edu.ug.smartdelivery.datastructure.heap.CustomMinHeap;
import edu.ug.smartdelivery.util.MemoryTracker;
import edu.ug.smartdelivery.util.Timer;

public class HeapExperiment {
    private final Timer timer = new Timer();
    private final MemoryTracker memoryTracker = new MemoryTracker();

    public ExperimentMeasurement[] run(int[] inputSizes, int trials) {
        ExperimentMeasurement[] results = new ExperimentMeasurement[inputSizes.length * trials * 2];
        int position = 0;
        for (int inputSize : inputSizes) {
            for (int trial = 1; trial <= trials; trial++) {
                results[position++] = measure("Min Heap Insert", inputSize, trial, () -> {
                    CustomMinHeap<Integer> heap = new CustomMinHeap<>();
                    for (int i = inputSize; i > 0; i--) {
                        heap.insert(i);
                    }
                });
                results[position++] = measure("Min Heap Extract", inputSize, trial, () -> {
                    CustomMinHeap<Integer> heap = filledHeap(inputSize);
                    while (!heap.isEmpty()) {
                        heap.extractMin();
                    }
                });
            }
        }
        return results;
    }

    private CustomMinHeap<Integer> filledHeap(int inputSize) {
        CustomMinHeap<Integer> heap = new CustomMinHeap<>();
        for (int i = inputSize; i > 0; i--) {
            heap.insert(i);
        }
        return heap;
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
