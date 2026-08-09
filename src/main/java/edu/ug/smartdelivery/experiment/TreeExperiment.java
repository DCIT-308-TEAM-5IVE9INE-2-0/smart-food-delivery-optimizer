package edu.ug.smartdelivery.experiment;

import edu.ug.smartdelivery.datastructure.tree.CustomBinarySearchTree;
import edu.ug.smartdelivery.datastructure.tree.CustomRedBlackTree;
import edu.ug.smartdelivery.util.MemoryTracker;
import edu.ug.smartdelivery.util.Timer;

public class TreeExperiment {
    private final Timer timer = new Timer();
    private final MemoryTracker memoryTracker = new MemoryTracker();

    public ExperimentMeasurement[] run(int[] inputSizes, int trials) {
        ExperimentMeasurement[] results = new ExperimentMeasurement[inputSizes.length * trials * 4];
        int position = 0;
        for (int inputSize : inputSizes) {
            for (int trial = 1; trial <= trials; trial++) {
                results[position++] = measure("BST Insert", inputSize, trial, () -> {
                    CustomBinarySearchTree<Integer, Integer> tree = new CustomBinarySearchTree<>();
                    for (int i = 0; i < inputSize; i++) {
                        tree.insert(i, i);
                    }
                });
                results[position++] = measure("Red Black Tree Insert", inputSize, trial, () -> {
                    CustomRedBlackTree<Integer, Integer> tree = new CustomRedBlackTree<>();
                    for (int i = 0; i < inputSize; i++) {
                        tree.insert(i, i);
                    }
                });
                results[position++] = measure("BST Search", inputSize, trial, () -> {
                    CustomBinarySearchTree<Integer, Integer> tree = filledBst(inputSize);
                    for (int i = 0; i < inputSize; i++) {
                        tree.search(i);
                    }
                });
                results[position++] = measure("Red Black Tree Search", inputSize, trial, () -> {
                    CustomRedBlackTree<Integer, Integer> tree = filledRedBlackTree(inputSize);
                    for (int i = 0; i < inputSize; i++) {
                        tree.search(i);
                    }
                });
            }
        }
        return results;
    }

    private CustomBinarySearchTree<Integer, Integer> filledBst(int inputSize) {
        CustomBinarySearchTree<Integer, Integer> tree = new CustomBinarySearchTree<>();
        for (int i = 0; i < inputSize; i++) {
            tree.insert(i, i);
        }
        return tree;
    }

    private CustomRedBlackTree<Integer, Integer> filledRedBlackTree(int inputSize) {
        CustomRedBlackTree<Integer, Integer> tree = new CustomRedBlackTree<>();
        for (int i = 0; i < inputSize; i++) {
            tree.insert(i, i);
        }
        return tree;
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
