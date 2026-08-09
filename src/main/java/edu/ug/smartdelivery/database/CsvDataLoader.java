package edu.ug.smartdelivery.database;

import edu.ug.smartdelivery.model.AlgorithmRun;
import edu.ug.smartdelivery.model.Customer;
import edu.ug.smartdelivery.model.Location;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Restaurant;
import edu.ug.smartdelivery.model.Rider;
import edu.ug.smartdelivery.model.Road;
import edu.ug.smartdelivery.repository.AlgorithmRunRepository;
import edu.ug.smartdelivery.repository.CustomerRepository;
import edu.ug.smartdelivery.repository.LocationRepository;
import edu.ug.smartdelivery.repository.OrderRepository;
import edu.ug.smartdelivery.repository.RestaurantRepository;
import edu.ug.smartdelivery.repository.RiderRepository;
import edu.ug.smartdelivery.repository.RoadRepository;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

public class CsvDataLoader {
    private final CsvReader csvReader;
    private final LocationRepository locationRepository;
    private final RoadRepository roadRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final RiderRepository riderRepository;
    private final OrderRepository orderRepository;
    private final AlgorithmRunRepository algorithmRunRepository;

    public CsvDataLoader(
            CsvReader csvReader,
            LocationRepository locationRepository,
            RoadRepository roadRepository,
            RestaurantRepository restaurantRepository,
            CustomerRepository customerRepository,
            RiderRepository riderRepository,
            OrderRepository orderRepository,
            AlgorithmRunRepository algorithmRunRepository
    ) {
        this.csvReader = csvReader;
        this.locationRepository = locationRepository;
        this.roadRepository = roadRepository;
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.riderRepository = riderRepository;
        this.orderRepository = orderRepository;
        this.algorithmRunRepository = algorithmRunRepository;
    }

    public CsvImportResult loadAll(Path dataDirectory) throws IOException, SQLException {
        int locations = loadLocations(dataDirectory.resolve("locations.csv"));
        int roads = loadRoads(dataDirectory.resolve("roads.csv"));
        int restaurants = loadRestaurants(dataDirectory.resolve("restaurants.csv"));
        int customers = loadCustomers(dataDirectory.resolve("customers.csv"));
        int riders = loadRiders(dataDirectory.resolve("riders.csv"));
        int orders = loadOrders(dataDirectory.resolve("orders.csv"));
        int algorithmRuns = loadAlgorithmRuns(dataDirectory.resolve("algorithm_runs.csv"));
        return new CsvImportResult(locations, roads, restaurants, customers, riders, orders, algorithmRuns);
    }

    public int loadLocations(Path csvPath) throws IOException, SQLException {
        List<String[]> rows = csvReader.read(csvPath);
        for (String[] row : rows) {
            requireColumns(csvPath, row, 6);
            locationRepository.upsert(new Location(
                    intValue(row[0]),
                    row[1],
                    row[2],
                    row[3],
                    doubleValue(row[4]),
                    doubleValue(row[5])
            ));
        }
        return rows.size();
    }

    public int loadRoads(Path csvPath) throws IOException, SQLException {
        List<String[]> rows = csvReader.read(csvPath);
        for (String[] row : rows) {
            requireColumns(csvPath, row, 7);
            roadRepository.upsert(new Road(
                    intValue(row[0]),
                    intValue(row[1]),
                    intValue(row[2]),
                    doubleValue(row[3]),
                    intValue(row[4]),
                    doubleValue(row[5]),
                    intValue(row[6]) == 1
            ));
        }
        return rows.size();
    }

    public int loadRestaurants(Path csvPath) throws IOException, SQLException {
        List<String[]> rows = csvReader.read(csvPath);
        for (String[] row : rows) {
            requireColumns(csvPath, row, 6);
            restaurantRepository.upsert(new Restaurant(
                    intValue(row[0]),
                    row[1],
                    intValue(row[2]),
                    row[3],
                    row[4],
                    row[5]
            ));
        }
        return rows.size();
    }

    public int loadCustomers(Path csvPath) throws IOException, SQLException {
        List<String[]> rows = csvReader.read(csvPath);
        for (String[] row : rows) {
            requireColumns(csvPath, row, 4);
            customerRepository.upsert(new Customer(
                    intValue(row[0]),
                    row[1],
                    row[2],
                    intValue(row[3])
            ));
        }
        return rows.size();
    }

    public int loadRiders(Path csvPath) throws IOException, SQLException {
        List<String[]> rows = csvReader.read(csvPath);
        for (String[] row : rows) {
            requireColumns(csvPath, row, 7);
            riderRepository.upsert(new Rider(
                    intValue(row[0]),
                    row[1],
                    intValue(row[2]),
                    row[3],
                    intValue(row[4]),
                    row[5],
                    intValue(row[6])
            ));
        }
        return rows.size();
    }

    public int loadOrders(Path csvPath) throws IOException, SQLException {
        List<String[]> rows = csvReader.read(csvPath);
        for (String[] row : rows) {
            requireColumns(csvPath, row, 12);
            orderRepository.upsert(new Order(
                    intValue(row[0]),
                    intValue(row[1]),
                    intValue(row[2]),
                    intValue(row[3]),
                    intValue(row[4]),
                    row[5],
                    intValue(row[6]),
                    row[7],
                    row[8],
                    row[9],
                    doubleValue(row[10]),
                    nullableInt(row[11])
            ));
        }
        return rows.size();
    }

    public int loadAlgorithmRuns(Path csvPath) throws IOException, SQLException {
        List<String[]> rows = csvReader.read(csvPath);
        for (String[] row : rows) {
            requireColumns(csvPath, row, 7);
            algorithmRunRepository.upsert(new AlgorithmRun(
                    intValue(row[0]),
                    row[1],
                    intValue(row[2]),
                    longValue(row[3]),
                    doubleValue(row[4]),
                    intValue(row[5]),
                    row[6]
            ));
        }
        return rows.size();
    }

    private void requireColumns(Path csvPath, String[] row, int expectedColumns) {
        if (row.length != expectedColumns) {
            throw new IllegalArgumentException(csvPath + " expected " + expectedColumns + " columns but found " + row.length);
        }
    }

    private int intValue(String value) {
        return Integer.parseInt(value.trim());
    }

    private long longValue(String value) {
        return Long.parseLong(value.trim());
    }

    private double doubleValue(String value) {
        return Double.parseDouble(value.trim());
    }

    private Integer nullableInt(String value) {
        String trimmed = value.trim();
        if (trimmed.isEmpty() || "null".equalsIgnoreCase(trimmed)) {
            return null;
        }
        return Integer.parseInt(trimmed);
    }
}
