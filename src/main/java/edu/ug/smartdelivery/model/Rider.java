package edu.ug.smartdelivery.model;

import java.util.Objects;

public record Rider(
        int riderId,
        String name,
        int homeLocationId,
        String vehicleType,
        int capacity,
        String availabilityStatus,
        int currentLocationId
) {
    public Rider {
        if (riderId <= 0 || homeLocationId <= 0 || currentLocationId <= 0) {
            throw new IllegalArgumentException("rider and location ids must be positive");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(vehicleType, "vehicleType");
        Objects.requireNonNull(availabilityStatus, "availabilityStatus");
    }
}
