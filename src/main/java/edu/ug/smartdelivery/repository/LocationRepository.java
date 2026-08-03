package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;

public class LocationRepository {
    private final DatabaseConnection databaseConnection;

    public LocationRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public DatabaseConnection databaseConnection() {
        return databaseConnection;
    }
}
