package edu.ug.smartdelivery.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvReaderTest {
    @TempDir
    Path tempDir;

    @Test
    void readsRowsAndHandlesQuotedCommas() throws Exception {
        Path csvPath = tempDir.resolve("locations.csv");
        Files.writeString(csvPath, """
                id,name,area
                1,"Night Market, Legon",Legon
                """);

        List<String[]> rows = new CsvReader().read(csvPath);

        assertEquals(1, rows.size());
        assertEquals("Night Market, Legon", rows.get(0)[1]);
    }
}
