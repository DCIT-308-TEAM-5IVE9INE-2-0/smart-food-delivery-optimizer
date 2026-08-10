package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.database.CsvDataLoader;
import edu.ug.smartdelivery.database.CsvImportResult;
import edu.ug.smartdelivery.database.CsvReader;
import edu.ug.smartdelivery.database.DatabaseConnection;
import edu.ug.smartdelivery.database.DatabaseInitializer;
import edu.ug.smartdelivery.database.DatabaseSummary;
import edu.ug.smartdelivery.model.Customer;
import edu.ug.smartdelivery.model.AuditEvent;
import edu.ug.smartdelivery.model.Location;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Restaurant;
import edu.ug.smartdelivery.model.Rider;
import edu.ug.smartdelivery.model.Road;
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
import java.time.LocalDateTime;
import java.util.List;

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

    public Location[] getLocations() throws SQLException {
        List<Location> locations = locationRepository.findAll();
        return locations.toArray(Location[]::new);
    }

    public Road[] getRoads() throws SQLException {
        List<Road> roads = roadRepository.findAll();
        return roads.toArray(Road[]::new);
    }

    public Restaurant[] getRestaurants() throws SQLException {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.toArray(Restaurant[]::new);
    }

    public Customer[] getCustomers() throws SQLException {
        List<Customer> customers = customerRepository.findAll();
        return customers.toArray(Customer[]::new);
    }

    public Rider[] getRiders() throws SQLException {
        List<Rider> riders = riderRepository.findAll();
        return riders.toArray(Rider[]::new);
    }

    public Order[] getOrders() throws SQLException {
        List<Order> orders = orderRepository.findAll();
        return orders.toArray(Order[]::new);
    }

    public Order[] getPendingOrders() throws SQLException {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> "PENDING".equalsIgnoreCase(order.status()))
                .toArray(Order[]::new);
    }

    public AuditEvent[] getAuditEvents() throws SQLException {
        List<AuditEvent> events = auditEventRepository.findAll();
        return events.toArray(AuditEvent[]::new);
    }

    public void markOrderDispatched(Order order) throws SQLException {
        updateOrderState(
                order,
                "DISPATCHED",
                order.assignedRiderId(),
                "ORDER_DISPATCHED",
                "status=" + order.status() + ", assignedRiderId=" + order.assignedRiderId(),
                "status=DISPATCHED, assignedRiderId=" + order.assignedRiderId()
        );
    }

    public void assignOrderToRider(Order order, Rider rider) throws SQLException {
        updateOrderState(
                order,
                "ASSIGNED",
                rider.riderId(),
                "ORDER_ASSIGNED",
                "status=" + order.status() + ", assignedRiderId=" + order.assignedRiderId(),
                "status=ASSIGNED, assignedRiderId=" + rider.riderId()
        );
        riderRepository.updateStatusAndCurrentLocation(rider.riderId(), "BUSY", order.destinationLocationId());
        recordAuditEvent(
                "RIDER_STATUS_UPDATED",
                "riders",
                rider.riderId(),
                "status=" + rider.availabilityStatus() + ", currentLocationId=" + rider.currentLocationId(),
                "status=BUSY, currentLocationId=" + order.destinationLocationId()
        );
    }

    private void updateOrderState(
            Order order,
            String newStatus,
            Integer assignedRiderId,
            String eventType,
            String previousValue,
            String newValue
    ) throws SQLException {
        orderRepository.updateStatusAndAssignedRider(order.orderId(), newStatus, assignedRiderId);
        recordAuditEvent(eventType, "orders", order.orderId(), previousValue, newValue);
    }

    private void recordAuditEvent(String eventType, String entityType, int entityId, String previousValue, String newValue) throws SQLException {
        auditEventRepository.upsert(new AuditEvent(
                auditEventRepository.nextEventId(),
                eventType,
                entityType,
                entityId,
                previousValue,
                newValue,
                LocalDateTime.now().toString()
        ));
    }
}
