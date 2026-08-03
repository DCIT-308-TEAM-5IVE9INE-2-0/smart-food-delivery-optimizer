package edu.ug.smartdelivery.model;

import java.util.Objects;

public record AlgorithmRun(
        int runId,
        String algorithmName,
        int inputSize,
        long executionTimeNs,
        double memoryKb,
        int trialNumber,
        String dateRun
) {
    public AlgorithmRun {
        if (runId <= 0 || inputSize < 0 || executionTimeNs < 0 || memoryKb < 0 || trialNumber <= 0) {
            throw new IllegalArgumentException("algorithm run numeric values are invalid");
        }
        Objects.requireNonNull(algorithmName, "algorithmName");
        Objects.requireNonNull(dateRun, "dateRun");
    }
}
