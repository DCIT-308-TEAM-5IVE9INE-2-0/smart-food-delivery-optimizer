package edu.ug.smartdelivery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DEFAULT_URL = "jdbc:sqlite:database/smart_delivery.db";
    private final String databaseUrl;

    public DatabaseConnection() {
        this(DEFAULT_URL);
    }

    public DatabaseConnection(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public Connection open() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }
}
