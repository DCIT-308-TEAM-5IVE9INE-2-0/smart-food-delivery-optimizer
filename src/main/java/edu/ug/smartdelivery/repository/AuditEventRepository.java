package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditEventRepository {
    private final DatabaseConnection databaseConnection;

    public AuditEventRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM audit_events");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
