package edu.ug.smartdelivery.repository;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.AlgorithmRun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmRunRepository {
    private final DatabaseConnection databaseConnection;

    public AlgorithmRunRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void upsert(AlgorithmRun algorithmRun) throws SQLException {
        String sql = """
                INSERT OR REPLACE INTO algorithm_runs
                (run_id, algorithm_name, input_size, execution_time_ns, memory_kb, trial_number, date_run)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, algorithmRun.runId());
            statement.setString(2, algorithmRun.algorithmName());
            statement.setInt(3, algorithmRun.inputSize());
            statement.setLong(4, algorithmRun.executionTimeNs());
            statement.setDouble(5, algorithmRun.memoryKb());
            statement.setInt(6, algorithmRun.trialNumber());
            statement.setString(7, algorithmRun.dateRun());
            statement.executeUpdate();
        }
    }

    public int count() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM algorithm_runs");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

    public int nextRunId() throws SQLException {
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement("SELECT COALESCE(MAX(run_id), 0) + 1 FROM algorithm_runs");
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 1;
        }
    }

    public List<AlgorithmRun> findAll() throws SQLException {
        String sql = """
                SELECT run_id, algorithm_name, input_size, execution_time_ns, memory_kb, trial_number, date_run
                FROM algorithm_runs
                ORDER BY run_id
                """;
        try (Connection connection = databaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<AlgorithmRun> runs = new ArrayList<>();
            while (resultSet.next()) {
                runs.add(new AlgorithmRun(
                        resultSet.getInt("run_id"),
                        resultSet.getString("algorithm_name"),
                        resultSet.getInt("input_size"),
                        resultSet.getLong("execution_time_ns"),
                        resultSet.getDouble("memory_kb"),
                        resultSet.getInt("trial_number"),
                        resultSet.getString("date_run")
                ));
            }
            return runs;
        }
    }
}
