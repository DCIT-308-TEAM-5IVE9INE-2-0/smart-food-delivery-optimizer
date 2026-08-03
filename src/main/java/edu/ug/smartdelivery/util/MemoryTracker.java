package edu.ug.smartdelivery.util;

public class MemoryTracker {
    public double usedMemoryKb() {
        Runtime runtime = Runtime.getRuntime();
        long usedBytes = runtime.totalMemory() - runtime.freeMemory();
        return usedBytes / 1024.0;
    }
}
