package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.database.CsvDataLoader;
import edu.ug.smartdelivery.database.CsvImportResult;
import edu.ug.smartdelivery.database.CsvReader;
import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.database.DatabaseInitializer;
import edu.ug.smartdelivery.database.DatabaseSummary;
import edu.ug.smartdelivery.repository.AlgorithmRunRepository;
import edu.ug.smartdelivery.repository.AuditEventRepository;
import edu.ug.smartdelivery.repository.CustomerRepository;
import edu.ug.smartdelivery.repository.LocationRepository;
import edu.ug.smartdelivery.repository.OrderRepository;
import edu.ug.smartdelivery.repository.RestaurantRepository;
import edu.ug.smartdelivery.repository.RiderRepository;
import edu.ug.smartdelivery.repository.RoadRepository;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

public class DatabaseService {
    private final DatabaseInitializer databaseInitializer;
    private final CsvDataLoader csvDataLoader;
    private final LocationRepository locationRepository;
    private final RoadRepository roadRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final RiderRepository riderRepository;
    private final OrderRepository orderRepository;
    private final AlgorithmRunRepository algorithmRunRepository;
    private final AuditEventRepository auditEventRepository;

    public DatabaseService() {
        this(new DatabaseConnection());
    }

    public DatabaseService(DatabaseConnection databaseConnection) {
        this.locationRepository = new LocationRepository(databaseConnection);
        this.roadRepository = new RoadRepository(databaseConnection);
        this.restaurantRepository = new RestaurantRepository(databaseConnection);
        this.customerRepository = new CustomerRepository(databaseConnection);
        this.riderRepository = new RiderRepository(databaseConnection);
        this.orderRepository = new OrderRepository(databaseConnection);
        this.algorithmRunRepository = new AlgorithmRunRepository(databaseConnection);
        this.auditEventRepository = new AuditEventRepository(databaseConnection);
        this.databaseInitializer = new DatabaseInitializer(databaseConnection);
        this.csvDataLoader = new CsvDataLoader(
                new CsvReader(),
                locationRepository,
                roadRepository,
                restaurantRepository,
                customerRepository,
                riderRepository,
                orderRepository,
                algorithmRunRepository
        );
    }

    public void initializeDatabase() throws SQLException, IOException {
        databaseInitializer.initialize();
    }

    public CsvImportResult importCsvData(Path dataDirectory) throws SQLException, IOException {
        return csvDataLoader.loadAll(dataDirectory);
    }

    public DatabaseSummary getSummary() throws SQLException {
        return new DatabaseSummary(
                locationRepository.count(),
                roadRepository.count(),
                restaurantRepository.count(),
                customerRepository.count(),
                riderRepository.count(),
                orderRepository.count(),
                algorithmRunRepository.count(),
                auditEventRepository.count()
        );
    }
}
