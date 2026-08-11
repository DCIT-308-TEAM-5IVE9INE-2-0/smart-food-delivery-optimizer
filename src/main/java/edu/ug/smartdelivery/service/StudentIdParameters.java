package edu.ug.smartdelivery.service;

public record StudentIdParameters(
        int[] studentIds,
        int digitSum,
        int lastTwoDigitSum,
        long fullIdSum,
        int finalDigitSum,
        int priorityWeight,
        int routePenalty,
        int hashTableInitialSize,
        long randomDataSeed,
        int dynamicProgrammingCapacity
) {
    public StudentIdParameters {
        if (studentIds == null || studentIds.length == 0) {
            throw new IllegalArgumentException("studentIds are required");
        }
        studentIds = copyOf(studentIds);
    }

    @Override
    public int[] studentIds() {
        return copyOf(studentIds);
    }

    private static int[] copyOf(int[] values) {
        int[] copy = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            copy[i] = values[i];
        }
        return copy;
    }
}
