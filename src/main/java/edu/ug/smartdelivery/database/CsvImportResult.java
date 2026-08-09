package edu.ug.smartdelivery.database;

public record CsvImportResult(
        int locations,
        int roads,
        int restaurants,
        int customers,
        int riders,
        int orders,
        int algorithmRuns
) {
    public int totalRows() {
        return locations + roads + restaurants + customers + riders + orders + algorithmRuns;
    }
}
