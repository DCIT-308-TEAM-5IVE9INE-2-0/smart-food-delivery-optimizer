package edu.ug.smartdelivery.model;

public record Road(
        int roadId,
        int fromLocationId,
        int toLocationId,
        double distanceKm,
        int travelTimeMinutes,
        double roadConditionWeight,
        boolean bidirectional
) {
    public Road {
        if (roadId <= 0 || fromLocationId <= 0 || toLocationId <= 0) {
            throw new IllegalArgumentException("road and location ids must be positive");
        }
        if (distanceKm < 0 || travelTimeMinutes < 0 || roadConditionWeight < 0) {
            throw new IllegalArgumentException("road weights must be non-negative");
        }
    }
}
