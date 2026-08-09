package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.Restaurant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    private final DatabaseConnection databaseConnection;

    public RestaurantRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void upsert(Restaurant restaurant) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO restaurants
                (restaurant_id, name, location_id, opening_time, closing_time, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, restaurant.restaurantId());
            statement.setString(2, restaurant.name());
            statement.setInt(3, restaurant.locationId());
            statement.setString(4, restaurant.openingTime());
            statement.setString(5, restaurant.closingTime());
            statement.setString(6, restaurant.status());
            statement.executeUpdate();
        }
    }

    public List<Restaurant> findAll() throws SQLException {
        String sql = "SELECT restaurant_id, name, location_id, opening_time, closing_time, status FROM restaurants ORDER BY restaurant_id";
        List<Restaurant> restaurants = new ArrayList<>();
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                restaurants.add(new Restaurant(
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("location_id"),
                        resultSet.getString("opening_time"),
                        resultSet.getString("closing_time"),
                        resultSet.getString("status")
                ));
            }
        }
        return restaurants;
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM restaurants");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
