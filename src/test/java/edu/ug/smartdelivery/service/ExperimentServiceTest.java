package edu.ug.smartdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.AlgorithmRun;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ExperimentServiceTest {
    @Test
    void defaultExperimentsPersistAndExportRuns() throws Exception {
        Path databaseFile = Files.createTempFile("smart-delivery-experiments", ".db");
        DatabaseConnection connection = new DatabaseConnection("jdbc:sqlite:" + databaseFile);
        ExperimentService service = new ExperimentService(connection);

        AlgorithmRun[] generated = service.runDefaultExperiments();
        AlgorithmRun[] stored = service.getStoredRuns();

        assertEquals(171, generated.length);
        assertEquals(generated.length, stored.length);
        assertTrue(Files.exists(Path.of("results", "csv", "algorithm_runs.csv")));
    }
}
