package edu.ug.smartdelivery.database;

import java.nio.file.Files;
import java.nio.file.Path;
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
        ensureParentDirectoryExists();
        return DriverManager.getConnection(databaseUrl);
    }

    private void ensureParentDirectoryExists() throws SQLException {
        if (!databaseUrl.startsWith("jdbc:sqlite:")) {
            return;
        }
        String pathText = databaseUrl.substring("jdbc:sqlite:".length());
        if (pathText.isBlank() || ":memory:".equals(pathText)) {
            return;
        }
        Path parent = Path.of(pathText).toAbsolutePath().getParent();
        if (parent == null) {
            return;
        }
        try {
            Files.createDirectories(parent);
        } catch (Exception exception) {
            throw new SQLException("Could not create database directory: " + parent, exception);
        }
    }
}
