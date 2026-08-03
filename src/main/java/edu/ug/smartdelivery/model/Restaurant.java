package edu.ug.smartdelivery.model;

import java.util.Objects;

public record Restaurant(
        int restaurantId,
        String name,
        int locationId,
        String openingTime,
        String closingTime,
        String status
) {
    public Restaurant {
        if (restaurantId <= 0 || locationId <= 0) {
            throw new IllegalArgumentException("restaurantId and locationId must be positive");
        }
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(openingTime, "openingTime");
        Objects.requireNonNull(closingTime, "closingTime");
        Objects.requireNonNull(status, "status");
    }
}
