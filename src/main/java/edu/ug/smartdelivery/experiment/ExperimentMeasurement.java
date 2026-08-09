package edu.ug.smartdelivery.experiment;

public record ExperimentMeasurement(
        String algorithmName,
        int inputSize,
        long executionTimeNs,
        double memoryKb,
        int trialNumber
) {
    public ExperimentMeasurement {
        if (algorithmName == null || algorithmName.isBlank()) {
            throw new IllegalArgumentException("algorithmName is required");
        }
        if (inputSize < 0 || executionTimeNs < 0 || memoryKb < 0 || trialNumber <= 0) {
            throw new IllegalArgumentException("experiment measurement numeric values are invalid");
        }
    }
}
