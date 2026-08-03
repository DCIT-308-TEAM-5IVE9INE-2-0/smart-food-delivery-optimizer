package edu.ug.smartdelivery.model;

import java.util.Objects;

public record Order(
        int orderId,
        int restaurantId,
        int customerId,
        int sourceLocationId,
        int destinationLocationId,
        String category,
        int urgency,
        String timeSubmitted,
        String deadline,
        String status,
        double estimatedDistance,
        Integer assignedRiderId
) {
    public Order {
        if (orderId <= 0 || restaurantId <= 0 || customerId <= 0 || sourceLocationId <= 0 || destinationLocationId <= 0) {
            throw new IllegalArgumentException("ids must be positive");
        }
        if (urgency < 0 || estimatedDistance < 0) {
            throw new IllegalArgumentException("urgency and estimatedDistance must be non-negative");
        }
        Objects.requireNonNull(category, "category");
        Objects.requireNonNull(timeSubmitted, "timeSubmitted");
        Objects.requireNonNull(deadline, "deadline");
        Objects.requireNonNull(status, "status");
    }
}
