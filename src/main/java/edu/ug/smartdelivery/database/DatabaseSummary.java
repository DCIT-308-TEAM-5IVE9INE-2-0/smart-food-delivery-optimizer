package edu.ug.smartdelivery.database;

public record DatabaseSummary(
        int locations,
        int roads,
        int restaurants,
        int customers,
        int riders,
        int orders,
        int algorithmRuns,
        int auditEvents
) {
}
