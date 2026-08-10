package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private final DatabaseConnection databaseConnection;

    public OrderRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void upsert(Order order) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO orders
                (order_id, restaurant_id, customer_id, source_location_id, destination_location_id,
                 category, urgency, time_submitted, deadline, status, estimated_distance, assigned_rider_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.orderId());
            statement.setInt(2, order.restaurantId());
            statement.setInt(3, order.customerId());
            statement.setInt(4, order.sourceLocationId());
            statement.setInt(5, order.destinationLocationId());
            statement.setString(6, order.category());
            statement.setInt(7, order.urgency());
            statement.setString(8, order.timeSubmitted());
            statement.setString(9, order.deadline());
            statement.setString(10, order.status());
            statement.setDouble(11, order.estimatedDistance());
            if (order.assignedRiderId() == null) {
                statement.setNull(12, Types.INTEGER);
            } else {
                statement.setInt(12, order.assignedRiderId());
            }
            statement.executeUpdate();
        }
    }

    public List<Order> findAll() throws SQLException {
        String sql = """
                SELECT order_id, restaurant_id, customer_id, source_location_id, destination_location_id,
                       category, urgency, time_submitted, deadline, status, estimated_distance, assigned_rider_id
                FROM orders
                ORDER BY order_id
                """;
        List<Order> orders = new ArrayList<>();
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int assignedRiderId = resultSet.getInt("assigned_rider_id");
                boolean assignedRiderWasNull = resultSet.wasNull();
                orders.add(new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("source_location_id"),
                        resultSet.getInt("destination_location_id"),
                        resultSet.getString("category"),
                        resultSet.getInt("urgency"),
                        resultSet.getString("time_submitted"),
                        resultSet.getString("deadline"),
                        resultSet.getString("status"),
                        resultSet.getDouble("estimated_distance"),
                        assignedRiderWasNull ? null : assignedRiderId
                ));
            }
        }
        return orders;
    }

    public void updateStatusAndAssignedRider(int orderId, String status, Integer assignedRiderId) throws SQLException {
        String sql = """
                UPDATE orders
                SET status = ?, assigned_rider_id = ?
                WHERE order_id = ?
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            if (assignedRiderId == null) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, assignedRiderId);
            }
            statement.setInt(3, orderId);
            statement.executeUpdate();
        }
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM orders");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
