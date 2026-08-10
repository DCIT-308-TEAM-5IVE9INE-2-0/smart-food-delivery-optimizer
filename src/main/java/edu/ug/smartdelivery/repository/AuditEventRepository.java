package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.AuditEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditEventRepository {
    private final DatabaseConnection databaseConnection;

    public AuditEventRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void upsert(AuditEvent auditEvent) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO audit_events
                (event_id, event_type, entity_type, entity_id, previous_value, new_value, event_time)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, auditEvent.eventId());
            statement.setString(2, auditEvent.eventType());
            statement.setString(3, auditEvent.entityType());
            statement.setInt(4, auditEvent.entityId());
            statement.setString(5, auditEvent.previousValue());
            statement.setString(6, auditEvent.newValue());
            statement.setString(7, auditEvent.eventTime());
            statement.executeUpdate();
        }
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM audit_events");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

    public int nextEventId() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COALESCE(MAX(event_id), 0) + 1 FROM audit_events");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 1;
        }
    }

    public List<AuditEvent> findAll() throws SQLException {
        String sql = """
                SELECT event_id, event_type, entity_type, entity_id, previous_value, new_value, event_time
                FROM audit_events
                ORDER BY event_id
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<AuditEvent> events = new ArrayList<>();
            while (resultSet.next()) {
                events.add(new AuditEvent(
                        resultSet.getInt("event_id"),
                        resultSet.getString("event_type"),
                        resultSet.getString("entity_type"),
                        resultSet.getInt("entity_id"),
                        resultSet.getString("previous_value"),
                        resultSet.getString("new_value"),
                        resultSet.getString("event_time")
                ));
            }
            return events;
        }
    }
}
