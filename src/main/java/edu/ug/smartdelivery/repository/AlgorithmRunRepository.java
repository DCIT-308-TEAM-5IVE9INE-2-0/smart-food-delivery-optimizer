package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;

public class AlgorithmRunRepository {
    private final DatabaseConnection databaseConnection;

    public AlgorithmRunRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
