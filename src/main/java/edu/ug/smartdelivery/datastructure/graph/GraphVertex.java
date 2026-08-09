package edu.ug.smartdelivery.datastructure.graph;

public record GraphVertex(int locationId, String name) {
    public GraphVertex {
        if (locationId <= 0) {
            throw new IllegalArgumentException("locationId must be positive");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}
