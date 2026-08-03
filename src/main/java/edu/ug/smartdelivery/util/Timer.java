package edu.ug.smartdelivery.util;

public class Timer {
    private long startedAt;

    public void start() {
        startedAt = System.nanoTime();
    }

    public long elapsedNanos() {
        return System.nanoTime() - startedAt;
    }
}
