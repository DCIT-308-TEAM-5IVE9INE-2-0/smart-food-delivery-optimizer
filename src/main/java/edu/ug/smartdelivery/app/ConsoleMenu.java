package edu.ug.smartdelivery.app;

import edu.ug.smartdelivery.database.CsvImportResult;
import edu.ug.smartdelivery.database.DatabaseSummary;
import edu.ug.smartdelivery.service.DatabaseService;
import java.nio.file.Path;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;
    private final DatabaseService databaseService;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        this.databaseService = new DatabaseService();
    }

    public void start() {
        int choice;
        do {
            printMainMenu();
            choice = readChoice();
            handleChoice(choice);
        } while (choice != 0);
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println(" SMART FOOD DELIVERY OPERATIONS OPTIMIZER");
        System.out.println("==============================================");
        System.out.println("1. Initialize Database");
        System.out.println("2. Import CSV Seed Data");
        System.out.println("3. View Database Summary");
        System.out.println("4. Manage Locations");
        System.out.println("5. Manage Roads");
        System.out.println("6. Manage Restaurants");
        System.out.println("7. Manage Customers");
        System.out.println("8. Manage Riders");
        System.out.println("9. Manage Food Orders");
        System.out.println("10. Process Orders Using FIFO");
        System.out.println("11. Process Orders Using Priority");
        System.out.println("12. Search Records");
        System.out.println("13. Sort Orders");
        System.out.println("14. Find Shortest Delivery Route");
        System.out.println("15. Display Reachable Locations");
        System.out.println("16. Generate Minimum Connection Network");
        System.out.println("17. Assign Riders Using Greedy Strategy");
        System.out.println("18. Select Orders Using Dynamic Programming");
        System.out.println("19. Run Algorithm Experiments");
        System.out.println("20. View Performance Results");
        System.out.println("21. View Audit and Undo History");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private int readChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void handleChoice(int choice) {
        try {
            switch (choice) {
                case 0 -> System.out.println("Exiting SMART FOOD DELIVERY.");
                case 1 -> initializeDatabase();
                case 2 -> importCsvSeedData();
                case 3 -> viewDatabaseSummary();
                default -> System.out.println("Option " + choice + " is ready for implementation in the next milestone.");
            }
        } catch (Exception exception) {
            System.out.println("Operation failed: " + exception.getMessage());
        }
    }

    private void initializeDatabase() throws Exception {
        databaseService.initializeDatabase();
        System.out.println("Database initialized successfully.");
    }

    private void importCsvSeedData() throws Exception {
        databaseService.initializeDatabase();
        CsvImportResult result = databaseService.importCsvData(Path.of("data"));
        System.out.println("CSV import complete.");
        System.out.println("Locations: " + result.locations());
        System.out.println("Roads: " + result.roads());
        System.out.println("Restaurants: " + result.restaurants());
        System.out.println("Customers: " + result.customers());
        System.out.println("Riders: " + result.riders());
        System.out.println("Orders: " + result.orders());
        System.out.println("Algorithm runs: " + result.algorithmRuns());
        System.out.println("Total imported rows: " + result.totalRows());
    }

    private void viewDatabaseSummary() throws Exception {
        databaseService.initializeDatabase();
        DatabaseSummary summary = databaseService.getSummary();
        System.out.println("Database Summary");
        System.out.println("Locations: " + summary.locations());
        System.out.println("Roads: " + summary.roads());
        System.out.println("Restaurants: " + summary.restaurants());
        System.out.println("Customers: " + summary.customers());
        System.out.println("Riders: " + summary.riders());
        System.out.println("Orders: " + summary.orders());
        System.out.println("Algorithm runs: " + summary.algorithmRuns());
        System.out.println("Audit events: " + summary.auditEvents());
    }
}
