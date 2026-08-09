package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.database.DatabaseInitializer;
import edu.ug.smartdelivery.experiment.ExperimentMeasurement;
import edu.ug.smartdelivery.experiment.GraphExperiment;
import edu.ug.smartdelivery.experiment.HashExperiment;
import edu.ug.smartdelivery.experiment.HeapExperiment;
import edu.ug.smartdelivery.experiment.SearchExperiment;
import edu.ug.smartdelivery.experiment.SortingExperiment;
import edu.ug.smartdelivery.experiment.TreeExperiment;
import edu.ug.smartdelivery.model.AlgorithmRun;
import edu.ug.smartdelivery.repository.AlgorithmRunRepository;
import edu.ug.smartdelivery.util.CsvExporter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ExperimentService {
    private static final int[] DEFAULT_INPUT_SIZES = {50, 100, 200};
    private static final int DEFAULT_TRIALS = 3;

    private final DatabaseInitializer databaseInitializer;
    private final AlgorithmRunRepository algorithmRunRepository;
    private final CsvExporter csvExporter;
    private final SearchExperiment searchExperiment;
    private final SortingExperiment sortingExperiment;
    private final HashExperiment hashExperiment;
    private final HeapExperiment heapExperiment;
    private final TreeExperiment treeExperiment;
    private final GraphExperiment graphExperiment;

    public ExperimentService() {
        this(new DatabaseConnection());
    }

    public ExperimentService(DatabaseConnection databaseConnection) {
        this.databaseInitializer = new DatabaseInitializer(databaseConnection);
        this.algorithmRunRepository = new AlgorithmRunRepository(databaseConnection);
        this.csvExporter = new CsvExporter();
        this.searchExperiment = new SearchExperiment();
        this.sortingExperiment = new SortingExperiment();
        this.hashExperiment = new HashExperiment();
        this.heapExperiment = new HeapExperiment();
        this.treeExperiment = new TreeExperiment();
        this.graphExperiment = new GraphExperiment();
    }

    public AlgorithmRun[] runDefaultExperiments() throws SQLException, IOException {
        databaseInitializer.initialize();
        ExperimentMeasurement[] measurements = combine(
                searchExperiment.run(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS),
                sortingExperiment.run(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS),
                hashExperiment.run(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS),
                heapExperiment.run(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS),
                treeExperiment.run(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS),
                graphExperiment.run(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS)
        );
        AlgorithmRun[] runs = persist(measurements);
        exportRuns(Path.of("results", "csv", "algorithm_runs.csv"), runs);
        return runs;
    }

    public AlgorithmRun[] getStoredRuns() throws SQLException {
        List<AlgorithmRun> runs = algorithmRunRepository.findAll();
        return runs.toArray(AlgorithmRun[]::new);
    }

    public void exportRuns(Path outputFile, AlgorithmRun[] runs) throws IOException {
        csvExporter.exportAlgorithmRuns(outputFile, runs);
    }

    private AlgorithmRun[] persist(ExperimentMeasurement[] measurements) throws SQLException {
        AlgorithmRun[] runs = new AlgorithmRun[measurements.length];
        int runId = algorithmRunRepository.nextRunId();
        String dateRun = LocalDateTime.now().toString();
        for (int i = 0; i < measurements.length; i++) {
            ExperimentMeasurement measurement = measurements[i];
            AlgorithmRun run = new AlgorithmRun(
                    runId++,
                    measurement.algorithmName(),
                    measurement.inputSize(),
                    measurement.executionTimeNs(),
                    measurement.memoryKb(),
                    measurement.trialNumber(),
                    dateRun
            );
            algorithmRunRepository.upsert(run);
            runs[i] = run;
        }
        return runs;
    }

    private ExperimentMeasurement[] combine(ExperimentMeasurement[]... groups) {
        int total = 0;
        for (ExperimentMeasurement[] group : groups) {
            total += group.length;
        }
        ExperimentMeasurement[] combined = new ExperimentMeasurement[total];
        int position = 0;
        for (ExperimentMeasurement[] group : groups) {
            for (ExperimentMeasurement measurement : group) {
                combined[position++] = measurement;
            }
        }
        return combined;
    }
}
