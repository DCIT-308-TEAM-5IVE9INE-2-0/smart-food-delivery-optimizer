package edu.ug.smartdelivery.model;

import java.util.Objects;

public record AlgorithmRunAverage(
        String algorithmName,
        int inputSize,
        double averageExecutionTimeNs,
        double averageMemoryKb,
        int trialCount
) {
    public AlgorithmRunAverage {
        Objects.requireNonNull(algorithmName, "algorithmName");
        if (inputSize < 0 || averageExecutionTimeNs < 0 || averageMemoryKb < 0 || trialCount <= 0) {
            throw new IllegalArgumentException("algorithm run average numeric values are invalid");
        }
    }
}
