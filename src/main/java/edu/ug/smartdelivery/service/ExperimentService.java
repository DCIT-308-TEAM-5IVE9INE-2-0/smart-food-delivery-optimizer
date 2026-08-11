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
import edu.ug.smartdelivery.model.AlgorithmRunAverage;
import edu.ug.smartdelivery.repository.AlgorithmRunRepository;
import edu.ug.smartdelivery.util.CsvExporter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExperimentService {
    private static final int[] DEFAULT_INPUT_SIZES = {50, 100, 200};
    private static final int DEFAULT_TRIALS = 3;
    private static final int[] REPORT_SEARCH_SIZES = {100, 500, 1000, 5000, 10000};
    private static final int[] REPORT_SORTING_SIZES = {100, 500, 1000, 5000, 10000};
    private static final int[] REPORT_HASH_HEAP_SIZES = {100, 500, 1000, 5000, 10000, 20000};
    private static final int[] REPORT_TREE_SIZES = {100, 500, 1000, 5000, 10000};
    private static final int[] REPORT_GRAPH_SIZES = {50, 100, 200, 500};
    private static final int REPORT_TRIALS = 3;

    private final DatabaseInitializer databaseInitializer;
    private final AlgorithmRunRepository algorithmRunRepository;
    private final CsvExporter csvExporter;
    private final SearchExperiment searchExperiment;
    private final SortingExperiment sortingExperiment;
    private final HashExperiment hashExperiment;
    private final HeapExperiment heapExperiment;
    private final TreeExperiment treeExperiment;
    private final GraphExperiment graphExperiment;
    private final StudentIdParameterService studentIdParameterService;

    public ExperimentService() {
        this(new DatabaseConnection());
    }

    public ExperimentService(DatabaseConnection databaseConnection) {
        this.databaseInitializer = new DatabaseInitializer(databaseConnection);
        this.algorithmRunRepository = new AlgorithmRunRepository(databaseConnection);
        this.csvExporter = new CsvExporter();
        this.studentIdParameterService = new StudentIdParameterService();
        this.searchExperiment = new SearchExperiment();
        this.sortingExperiment = new SortingExperiment();
        this.hashExperiment = new HashExperiment(studentIdParameterService.calculateParameters().hashTableInitialSize());
        this.heapExperiment = new HeapExperiment();
        this.treeExperiment = new TreeExperiment();
        this.graphExperiment = new GraphExperiment();
    }

    public int hashExperimentInitialCapacity() {
        return hashExperiment.initialCapacity();
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
        exportAverages(Path.of("results", "csv", "algorithm_run_averages.csv"), calculateAverages(runs));
        return runs;
    }

    public AlgorithmRun[] runReportExperiments() throws SQLException, IOException {
        databaseInitializer.initialize();
        ExperimentMeasurement[] measurements = combine(
                searchExperiment.run(REPORT_SEARCH_SIZES, REPORT_TRIALS),
                sortingExperiment.run(REPORT_SORTING_SIZES, REPORT_TRIALS),
                hashExperiment.run(REPORT_HASH_HEAP_SIZES, REPORT_TRIALS),
                heapExperiment.run(REPORT_HASH_HEAP_SIZES, REPORT_TRIALS),
                treeExperiment.run(REPORT_TREE_SIZES, REPORT_TRIALS),
                graphExperiment.run(REPORT_GRAPH_SIZES, REPORT_TRIALS)
        );
        AlgorithmRun[] runs = persist(measurements);
        exportRuns(Path.of("results", "csv", "algorithm_runs_report.csv"), runs);
        exportAverages(Path.of("results", "csv", "algorithm_run_averages_report.csv"), calculateAverages(runs));
        return runs;
    }

    public AlgorithmRun[] getStoredRuns() throws SQLException {
        List<AlgorithmRun> runs = algorithmRunRepository.findAll();
        return runs.toArray(AlgorithmRun[]::new);
    }

    public void exportRuns(Path outputFile, AlgorithmRun[] runs) throws IOException {
        csvExporter.exportAlgorithmRuns(outputFile, runs);
    }

    public void exportAverages(Path outputFile, AlgorithmRunAverage[] averages) throws IOException {
        csvExporter.exportAlgorithmRunAverages(outputFile, averages);
    }

    public AlgorithmRunAverage[] calculateAverages(AlgorithmRun[] runs) {
        if (runs == null) {
            throw new IllegalArgumentException("runs are required");
        }
        Map<AverageKey, AverageAccumulator> grouped = new LinkedHashMap<>();
        for (AlgorithmRun run : runs) {
            AverageKey key = new AverageKey(run.algorithmName(), run.inputSize());
            grouped.computeIfAbsent(key, ignored -> new AverageAccumulator()).add(run);
        }
        List<AlgorithmRunAverage> averages = new ArrayList<>();
        for (Map.Entry<AverageKey, AverageAccumulator> entry : grouped.entrySet()) {
            AverageKey key = entry.getKey();
            AverageAccumulator accumulator = entry.getValue();
            averages.add(new AlgorithmRunAverage(
                    key.algorithmName(),
                    key.inputSize(),
                    accumulator.averageExecutionTimeNs(),
                    accumulator.averageMemoryKb(),
                    accumulator.count()
            ));
        }
        averages.sort(Comparator
                .comparing(AlgorithmRunAverage::algorithmName)
                .thenComparingInt(AlgorithmRunAverage::inputSize));
        return averages.toArray(AlgorithmRunAverage[]::new);
    }

    public int expectedDefaultRunCount() {
        return expectedRunCount(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS, 2)
                + expectedRunCount(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS, 4)
                + expectedRunCount(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS, 2)
                + expectedRunCount(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS, 2)
                + expectedRunCount(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS, 4)
                + expectedRunCount(DEFAULT_INPUT_SIZES, DEFAULT_TRIALS, 5);
    }

    public int expectedReportRunCount() {
        return expectedRunCount(REPORT_SEARCH_SIZES, REPORT_TRIALS, 2)
                + expectedRunCount(REPORT_SORTING_SIZES, REPORT_TRIALS, 4)
                + expectedRunCount(REPORT_HASH_HEAP_SIZES, REPORT_TRIALS, 2)
                + expectedRunCount(REPORT_HASH_HEAP_SIZES, REPORT_TRIALS, 2)
                + expectedRunCount(REPORT_TREE_SIZES, REPORT_TRIALS, 4)
                + expectedRunCount(REPORT_GRAPH_SIZES, REPORT_TRIALS, 5);
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

    private int expectedRunCount(int[] inputSizes, int trials, int algorithmsPerSize) {
        return inputSizes.length * trials * algorithmsPerSize;
    }

    private record AverageKey(String algorithmName, int inputSize) {
    }

    private static final class AverageAccumulator {
        private double executionTimeTotal;
        private double memoryTotal;
        private int count;

        private void add(AlgorithmRun run) {
            executionTimeTotal += run.executionTimeNs();
            memoryTotal += run.memoryKb();
            count++;
        }

        private double averageExecutionTimeNs() {
            return executionTimeTotal / count;
        }

        private double averageMemoryKb() {
            return memoryTotal / count;
        }

        private int count() {
            return count;
        }
    }
}
