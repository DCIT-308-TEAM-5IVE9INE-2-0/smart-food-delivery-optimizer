package edu.ug.smartdelivery.model;

import java.util.Objects;

public record Customer(int customerId, String name, String phone, int defaultLocationId) {
    public Customer {
        if (customerId <= 0 || defaultLocationId <= 0) {
            throw new IllegalArgumentException("customerId and defaultLocationId must be positive");
        }
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(phone, "phone");
    }
}
