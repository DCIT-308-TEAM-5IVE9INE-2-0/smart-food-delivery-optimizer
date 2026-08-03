package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;

public class RoadRepository {
    private final DatabaseConnection databaseConnection;

    public RoadRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
