package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.datastructure.TraceStep;

public record ShortestPathResult(int sourceLocationId, int targetLocationId, double distance, int[] path, TraceStep[] trace) {
    public boolean reachable() {
        return !Double.isInfinite(distance);
    }
}
