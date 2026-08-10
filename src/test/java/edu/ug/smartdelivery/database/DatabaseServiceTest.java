package edu.ug.smartdelivery.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ug.smartdelivery.model.Location;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Rider;
import edu.ug.smartdelivery.repository.LocationRepository;
import edu.ug.smartdelivery.service.DatabaseService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DatabaseServiceTest {
    @TempDir
    Path tempDir;

    @Test
    void initializesDatabaseAndImportsCsvData() throws Exception {
        Path databasePath = tempDir.resolve("smart_delivery_test.db");
        Path dataDir = tempDir.resolve("data");
        Files.createDirectories(dataDir);
        writeSeedFiles(dataDir);

        DatabaseConnection connection = new DatabaseConnection("jdbc:sqlite:" + databasePath);
        DatabaseService service = new DatabaseService(connection);

        service.initializeDatabase();
        CsvImportResult result = service.importCsvData(dataDir);
        DatabaseSummary summary = service.getSummary();

        assertEquals(7, result.totalRows());
        assertEquals(2, summary.locations());
        assertEquals(1, summary.roads());
        assertEquals(1, summary.orders());

        List<Location> locations = new LocationRepository(connection).findAll();
        assertEquals("Legon Hall", locations.get(0).name());

        service.markOrderDispatched(service.getPendingOrders()[0]);

        assertEquals(0, service.getPendingOrders().length);
        assertEquals("DISPATCHED", service.getOrders()[0].status());
        assertEquals(1, service.getAuditEvents().length);
    }

    @Test
    void assignsOrderToRiderAndRecordsAuditEvents() throws Exception {
        Path databasePath = tempDir.resolve("smart_delivery_assignment_test.db");
        Path dataDir = tempDir.resolve("assignment-data");
        Files.createDirectories(dataDir);
        writeSeedFiles(dataDir);

        DatabaseConnection connection = new DatabaseConnection("jdbc:sqlite:" + databasePath);
        DatabaseService service = new DatabaseService(connection);

        service.initializeDatabase();
        service.importCsvData(dataDir);
        Order order = service.getPendingOrders()[0];
        Rider rider = service.getRiders()[0];

        service.assignOrderToRider(order, rider);

        assertEquals("ASSIGNED", service.getOrders()[0].status());
        assertEquals(rider.riderId(), service.getOrders()[0].assignedRiderId());
        assertEquals("BUSY", service.getRiders()[0].availabilityStatus());
        assertEquals(order.destinationLocationId(), service.getRiders()[0].currentLocationId());
        assertEquals(2, service.getAuditEvents().length);
    }

    private void writeSeedFiles(Path dataDir) throws Exception {
        Files.writeString(dataDir.resolve("locations.csv"), """
                location_id,name,area,type,latitude,longitude
                1,Legon Hall,Legon,Hostel,5.6500,-0.1900
                2,Bush Canteen,Legon,Restaurant Area,5.6510,-0.1910
                """);
        Files.writeString(dataDir.resolve("roads.csv"), """
                road_id,from_location_id,to_location_id,distance_km,travel_time_minutes,road_condition_weight,is_bidirectional
                1,1,2,0.6,4,1.0,1
                """);
        Files.writeString(dataDir.resolve("restaurants.csv"), """
                restaurant_id,name,location_id,opening_time,closing_time,status
                1,Bush Canteen Waakye,2,08:00,20:00,OPEN
                """);
        Files.writeString(dataDir.resolve("customers.csv"), """
                customer_id,name,phone,default_location_id
                1,Customer One,0200000001,1
                """);
        Files.writeString(dataDir.resolve("riders.csv"), """
                rider_id,name,home_location_id,vehicle_type,capacity,availability_status,current_location_id
                1,Rider One,1,Motorbike,2,AVAILABLE,1
                """);
        Files.writeString(dataDir.resolve("orders.csv"), """
                order_id,restaurant_id,customer_id,source_location_id,destination_location_id,category,urgency,time_submitted,deadline,status,estimated_distance,assigned_rider_id
                1,1,1,2,1,Lunch,3,2026-08-09T12:00,2026-08-09T12:45,PENDING,0.6,
                """);
        Files.writeString(dataDir.resolve("algorithm_runs.csv"), """
                run_id,algorithm_name,input_size,execution_time_ns,memory_kb,trial_number,date_run
                """);
    }
}
