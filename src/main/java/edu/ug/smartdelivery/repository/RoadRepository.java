package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.Road;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoadRepository {
    private final DatabaseConnection databaseConnection;

    public RoadRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void upsert(Road road) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO roads
                (road_id, from_location_id, to_location_id, distance_km, travel_time_minutes, road_condition_weight, is_bidirectional)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, road.roadId());
            statement.setInt(2, road.fromLocationId());
            statement.setInt(3, road.toLocationId());
            statement.setDouble(4, road.distanceKm());
            statement.setInt(5, road.travelTimeMinutes());
            statement.setDouble(6, road.roadConditionWeight());
            statement.setInt(7, road.bidirectional() ? 1 : 0);
            statement.executeUpdate();
        }
    }

    public List<Road> findAll() throws SQLException {
        String sql = """
                SELECT road_id, from_location_id, to_location_id, distance_km,
                       travel_time_minutes, road_condition_weight, is_bidirectional
                FROM roads
                ORDER BY road_id
                """;
        List<Road> roads = new ArrayList<>();
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                roads.add(new Road(
                        resultSet.getInt("road_id"),
                        resultSet.getInt("from_location_id"),
                        resultSet.getInt("to_location_id"),
                        resultSet.getDouble("distance_km"),
                        resultSet.getInt("travel_time_minutes"),
                        resultSet.getDouble("road_condition_weight"),
                        resultSet.getInt("is_bidirectional") == 1
                ));
            }
        }
        return roads;
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM roads");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
