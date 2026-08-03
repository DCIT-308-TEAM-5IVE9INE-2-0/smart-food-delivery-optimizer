package edu.ug.smartdelivery.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String SCHEMA_RESOURCE = "/database/schema.sql";
    private final DatabaseConnection databaseConnection;

    public DatabaseInitializer(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void initialize() throws SQLException, IOException {
        String schema = readSchema();
        try (Connection connection = databaseConnection.open();
             Statement statement = connection.createStatement()) {
            for (String sql : schema.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        }
    }

    private String readSchema() throws IOException {
        try (InputStream stream = DatabaseInitializer.class.getResourceAsStream(SCHEMA_RESOURCE)) {
            if (stream == null) {
                throw new IOException("Missing schema resource: " + SCHEMA_RESOURCE);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
