package edu.ug.smartdelivery.database;

public enum DatabaseTable {
    LOCATIONS("locations"),
    ROADS("roads"),
    RESTAURANTS("restaurants"),
    CUSTOMERS("customers"),
    RIDERS("riders"),
    ORDERS("orders"),
    ALGORITHM_RUNS("algorithm_runs"),
    AUDIT_EVENTS("audit_events");

    private final String tableName;

    DatabaseTable(String tableName) {
        this.tableName = tableName;
    }

    public String tableName() {
        return tableName;
    }
}
