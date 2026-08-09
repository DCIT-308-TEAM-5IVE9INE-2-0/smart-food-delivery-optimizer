package edu.ug.smartdelivery.datastructure.graph;

public record GraphEdge(int fromLocationId, int toLocationId, double weight) {
    public GraphEdge {
        if (fromLocationId <= 0 || toLocationId <= 0) {
            throw new IllegalArgumentException("location ids must be positive");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("weight must be non-negative");
        }
    }
}
