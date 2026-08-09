package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.Location;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {
    private final DatabaseConnection databaseConnection;

    public LocationRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public DatabaseConnection databaseConnection() {
        return databaseConnection;
    }

    public void upsert(Location location) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO locations (location_id, name, area, type, latitude, longitude)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, location.locationId());
            statement.setString(2, location.name());
            statement.setString(3, location.area());
            statement.setString(4, location.type());
            statement.setDouble(5, location.latitude());
            statement.setDouble(6, location.longitude());
            statement.executeUpdate();
        }
    }

    public List<Location> findAll() throws SQLException {
        String sql = "SELECT location_id, name, area, type, latitude, longitude FROM locations ORDER BY location_id";
        List<Location> locations = new ArrayList<>();
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                locations.add(new Location(
                        resultSet.getInt("location_id"),
                        resultSet.getString("name"),
                        resultSet.getString("area"),
                        resultSet.getString("type"),
                        resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude")
                ));
            }
        }
        return locations;
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM locations");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
