package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;

public class OrderRepository {
    private final DatabaseConnection databaseConnection;

    public OrderRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
