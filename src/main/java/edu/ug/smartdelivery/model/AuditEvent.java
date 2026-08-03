package edu.ug.smartdelivery.model;

import java.util.Objects;

public record AuditEvent(
        int eventId,
        String eventType,
        String entityType,
        int entityId,
        String previousValue,
        String newValue,
        String eventTime
) {
    public AuditEvent {
        if (eventId <= 0 || entityId <= 0) {
            throw new IllegalArgumentException("eventId and entityId must be positive");
        }
        Objects.requireNonNull(eventType, "eventType");
        Objects.requireNonNull(entityType, "entityType");
        Objects.requireNonNull(eventTime, "eventTime");
    }
}
