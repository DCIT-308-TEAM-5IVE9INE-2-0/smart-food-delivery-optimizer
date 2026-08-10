package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.Rider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RiderRepository {
    private final DatabaseConnection databaseConnection;

    public RiderRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void upsert(Rider rider) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO riders
                (rider_id, name, home_location_id, vehicle_type, capacity, availability_status, current_location_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rider.riderId());
            statement.setString(2, rider.name());
            statement.setInt(3, rider.homeLocationId());
            statement.setString(4, rider.vehicleType());
            statement.setInt(5, rider.capacity());
            statement.setString(6, rider.availabilityStatus());
            statement.setInt(7, rider.currentLocationId());
            statement.executeUpdate();
        }
    }

    public List<Rider> findAll() throws SQLException {
        String sql = """
                SELECT rider_id, name, home_location_id, vehicle_type, capacity,
                       availability_status, current_location_id
                FROM riders
                ORDER BY rider_id
                """;
        List<Rider> riders = new ArrayList<>();
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                riders.add(new Rider(
                        resultSet.getInt("rider_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("home_location_id"),
                        resultSet.getString("vehicle_type"),
                        resultSet.getInt("capacity"),
                        resultSet.getString("availability_status"),
                        resultSet.getInt("current_location_id")
                ));
            }
        }
        return riders;
    }

    public void updateStatusAndCurrentLocation(int riderId, String availabilityStatus, int currentLocationId) throws SQLException {
        String sql = """
                UPDATE riders
                SET availability_status = ?, current_location_id = ?
                WHERE rider_id = ?
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, availabilityStatus);
            statement.setInt(2, currentLocationId);
            statement.setInt(3, riderId);
            statement.executeUpdate();
        }
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM riders");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
