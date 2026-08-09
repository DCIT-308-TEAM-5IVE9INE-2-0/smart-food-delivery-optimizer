package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final DatabaseConnection databaseConnection;

    public CustomerRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void upsert(Customer customer) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO customers (customer_id, name, phone, default_location_id)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customer.customerId());
            statement.setString(2, customer.name());
            statement.setString(3, customer.phone());
            statement.setInt(4, customer.defaultLocationId());
            statement.executeUpdate();
        }
    }

    public List<Customer> findAll() throws SQLException {
        String sql = "SELECT customer_id, name, phone, default_location_id FROM customers ORDER BY customer_id";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getInt("default_location_id")
                ));
            }
        }
        return customers;
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM customers");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }
}
