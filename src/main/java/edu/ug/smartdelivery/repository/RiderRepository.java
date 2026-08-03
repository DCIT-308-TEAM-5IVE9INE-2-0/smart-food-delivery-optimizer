package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;

public class RiderRepository {
    private final DatabaseConnection databaseConnection;

    public RiderRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
