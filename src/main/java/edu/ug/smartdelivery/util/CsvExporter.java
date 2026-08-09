package edu.ug.smartdelivery.util;

import edu.ug.smartdelivery.model.AlgorithmRun;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvExporter {
    public void exportAlgorithmRuns(Path outputFile, AlgorithmRun[] runs) throws IOException {
        if (outputFile == null || runs == null) {
            throw new IllegalArgumentException("outputFile and runs are required");
        }
        Path parent = outputFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            writer.write("run_id,algorithm_name,input_size,execution_time_ns,memory_kb,trial_number,date_run");
            writer.newLine();
            for (AlgorithmRun run : runs) {
                writer.write(run.runId() + ",");
                writer.write(escape(run.algorithmName()) + ",");
                writer.write(run.inputSize() + ",");
                writer.write(run.executionTimeNs() + ",");
                writer.write(run.memoryKb() + ",");
                writer.write(run.trialNumber() + ",");
                writer.write(escape(run.dateRun()));
                writer.newLine();
            }
        }
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        if (!value.contains(",") && !value.contains("\"") && !value.contains("\n")) {
            return value;
        }
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
