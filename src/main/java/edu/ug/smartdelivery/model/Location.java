package edu.ug.smartdelivery.model;

import java.util.Objects;

public record Location(int locationId, String name, String area, String type, double latitude, double longitude) {
    public Location {
        if (locationId <= 0) {
            throw new IllegalArgumentException("locationId must be positive");
        }
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(area, "area");
        Objects.requireNonNull(type, "type");
    }
}
