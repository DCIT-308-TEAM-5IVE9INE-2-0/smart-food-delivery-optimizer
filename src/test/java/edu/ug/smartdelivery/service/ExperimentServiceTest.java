package edu.ug.smartdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.model.AlgorithmRun;
import edu.ug.smartdelivery.model.AlgorithmRunAverage;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ExperimentServiceTest {
    @Test
    void defaultExperimentsPersistAndExportRuns() throws Exception {
        Path databaseFile = Files.createTempFile("smart-delivery-experiments", ".db");
        DatabaseConnection connection = new DatabaseConnection("jdbc:sqlite:" + databaseFile);
        ExperimentService service = new ExperimentService(connection);

        assertEquals(71, service.hashExperimentInitialCapacity());
        assertEquals(171, service.expectedDefaultRunCount());
        assertEquals(282, service.expectedReportRunCount());

        AlgorithmRun[] generated = service.runDefaultExperiments();
        AlgorithmRun[] stored = service.getStoredRuns();

        assertEquals(service.expectedDefaultRunCount(), generated.length);
        assertEquals(generated.length, stored.length);
        assertTrue(Files.exists(Path.of("results", "csv", "algorithm_runs.csv")));
        assertTrue(Files.exists(Path.of("results", "csv", "algorithm_run_averages.csv")));
    }

    @Test
    void calculatesAveragesForRepeatedAlgorithmRuns() {
        ExperimentService service = new ExperimentService(new DatabaseConnection("jdbc:sqlite::memory:"));
        AlgorithmRun[] runs = {
                new AlgorithmRun(1, "Linear Search", 100, 10, 2, 1, "2026-08-11"),
                new AlgorithmRun(2, "Linear Search", 100, 30, 4, 2, "2026-08-11"),
                new AlgorithmRun(3, "Binary Search", 100, 5, 1, 1, "2026-08-11")
        };

        AlgorithmRunAverage[] averages = service.calculateAverages(runs);

        assertEquals(2, averages.length);
        assertEquals("Binary Search", averages[0].algorithmName());
        assertEquals(5.0, averages[0].averageExecutionTimeNs());
        assertEquals(1, averages[0].trialCount());
        assertEquals("Linear Search", averages[1].algorithmName());
        assertEquals(20.0, averages[1].averageExecutionTimeNs());
        assertEquals(3.0, averages[1].averageMemoryKb());
        assertEquals(2, averages[1].trialCount());
    }
}
