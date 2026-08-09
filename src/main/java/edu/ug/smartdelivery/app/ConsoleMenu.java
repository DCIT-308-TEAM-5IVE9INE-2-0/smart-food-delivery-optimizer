package edu.ug.smartdelivery.app;

import edu.ug.smartdelivery.algorithm.dynamicprogramming.OrderSelectionResult;
import edu.ug.smartdelivery.algorithm.graph.MinimumSpanningTreeResult;
import edu.ug.smartdelivery.algorithm.graph.ShortestPathResult;
import edu.ug.smartdelivery.algorithm.graph.TraversalResult;
import edu.ug.smartdelivery.algorithm.greedy.AssignmentResult;
import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.heap.CustomPriorityQueue;
import edu.ug.smartdelivery.datastructure.queue.CustomQueue;
import edu.ug.smartdelivery.datastructure.graph.AdjacencyListGraph;
import edu.ug.smartdelivery.database.CsvImportResult;
import edu.ug.smartdelivery.database.DatabaseSummary;
import edu.ug.smartdelivery.model.Customer;
import edu.ug.smartdelivery.model.Location;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.PrioritizedOrder;
import edu.ug.smartdelivery.model.Restaurant;
import edu.ug.smartdelivery.model.Rider;
import edu.ug.smartdelivery.model.Road;
import edu.ug.smartdelivery.service.DatabaseService;
import edu.ug.smartdelivery.service.OptimizationService;
import edu.ug.smartdelivery.service.OrderComparators;
import edu.ug.smartdelivery.service.RouteService;
import edu.ug.smartdelivery.service.SearchService;
import edu.ug.smartdelivery.service.SortService;
import java.nio.file.Path;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;
    private final DatabaseService databaseService;
    private final SearchService searchService;
    private final SortService sortService;
    private final RouteService routeService;
    private final OptimizationService optimizationService;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        this.databaseService = new DatabaseService();
        this.searchService = new SearchService();
        this.sortService = new SortService();
        this.routeService = new RouteService();
        this.optimizationService = new OptimizationService();
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
                case 4 -> listLocations();
                case 5 -> listRoads();
                case 6 -> listRestaurants();
                case 7 -> listCustomers();
                case 8 -> listRiders();
                case 9 -> listOrders();
                case 10 -> processOrdersFifo();
                case 11 -> processOrdersByPriority();
                case 12 -> searchRecords();
                case 13 -> sortOrders();
                case 14 -> findShortestDeliveryRoute();
                case 15 -> displayReachableLocations();
                case 16 -> generateMinimumConnectionNetwork();
                case 17 -> assignRidersGreedy();
                case 18 -> selectOrdersDynamicProgramming();
                case 19 -> System.out.println("Experiment runner belongs to Phase 9. Current algorithms are ready for experiments.");
                case 20 -> viewDatabaseSummary();
                case 21 -> System.out.println("Audit events table is ready. Stack-based undo demo is covered in structure traces.");
                default -> System.out.println("Unknown option: " + choice);
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

    private void listLocations() throws Exception {
        Location[] locations = requireLocations();
        System.out.println("Locations");
        for (Location location : locations) {
            System.out.println(location.locationId() + ". " + location.name() + " - " + location.area() + " (" + location.type() + ")");
        }
    }

    private void listRoads() throws Exception {
        Road[] roads = requireRoads();
        System.out.println("Roads");
        for (Road road : roads) {
            System.out.println(road.roadId() + ". " + road.fromLocationId() + " -> " + road.toLocationId()
                    + ", distance=" + road.distanceKm() + "km, time=" + road.travelTimeMinutes()
                    + "min, condition=" + road.roadConditionWeight());
        }
    }

    private void listRestaurants() throws Exception {
        Restaurant[] restaurants = databaseService.getRestaurants();
        requireData(restaurants.length, "restaurants");
        System.out.println("Restaurants");
        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant.restaurantId() + ". " + restaurant.name() + " at location " + restaurant.locationId()
                    + " (" + restaurant.status() + ")");
        }
    }

    private void listCustomers() throws Exception {
        Customer[] customers = databaseService.getCustomers();
        requireData(customers.length, "customers");
        System.out.println("Customers");
        for (Customer customer : customers) {
            System.out.println(customer.customerId() + ". " + customer.name() + " default location=" + customer.defaultLocationId());
        }
    }

    private void listRiders() throws Exception {
        Rider[] riders = requireRiders();
        System.out.println("Riders");
        for (Rider rider : riders) {
            System.out.println(rider.riderId() + ". " + rider.name() + " - " + rider.vehicleType()
                    + ", status=" + rider.availabilityStatus() + ", currentLocation=" + rider.currentLocationId());
        }
    }

    private void listOrders() throws Exception {
        Order[] orders = requireOrders();
        printOrders("Food Orders", orders);
    }

    private void processOrdersFifo() throws Exception {
        Order[] orders = requireOrders();
        CustomQueue<Order> queue = new CustomQueue<>();
        for (Order order : orders) {
            queue.enqueue(order);
        }
        System.out.println("FIFO dispatch order");
        while (!queue.isEmpty()) {
            Order order = queue.dequeue();
            System.out.println("Process order " + order.orderId() + " submitted at " + order.timeSubmitted());
        }
    }

    private void processOrdersByPriority() throws Exception {
        Order[] orders = requireOrders();
        CustomPriorityQueue<PrioritizedOrder> queue = new CustomPriorityQueue<>();
        for (Order order : orders) {
            queue.insert(new PrioritizedOrder(order, order.urgency()));
        }
        System.out.println("Priority dispatch order");
        while (!queue.isEmpty()) {
            PrioritizedOrder prioritizedOrder = queue.extractMin();
            System.out.println("Process order " + prioritizedOrder.order().orderId()
                    + " urgency=" + prioritizedOrder.priorityScore()
                    + " deadline=" + prioritizedOrder.order().deadline());
        }
    }

    private void searchRecords() throws Exception {
        Order[] orders = requireOrders();
        sortService.mergeSortOrders(orders, OrderComparators.byId());
        int targetOrderId = orders[orders.length - 1].orderId();
        var result = searchService.findOrderBinary(orders, targetOrderId);
        System.out.println("Binary search for order ID " + targetOrderId);
        result.ifPresentOrElse(
                order -> System.out.println("Found order " + order.orderId() + " category=" + order.category() + ", urgency=" + order.urgency()),
                () -> System.out.println("Order not found")
        );
    }

    private void sortOrders() throws Exception {
        Order[] orders = requireOrders();
        sortService.quickSortOrders(orders, OrderComparators.byUrgencyDescending());
        printOrders("Orders sorted by urgency", orders);
    }

    private void findShortestDeliveryRoute() throws Exception {
        AdjacencyListGraph graph = buildCurrentGraph();
        Location[] locations = requireLocations();
        int source = locations[0].locationId();
        int destination = locations[locations.length - 1].locationId();
        ShortestPathResult result = routeService.shortestRoute(graph, source, destination);
        System.out.println("Shortest route from " + source + " to " + destination);
        System.out.println("Distance/cost: " + result.distance());
        System.out.println("Path: " + join(result.path()));
        printTrace(result.trace());
    }

    private void displayReachableLocations() throws Exception {
        AdjacencyListGraph graph = buildCurrentGraph();
        Location[] locations = requireLocations();
        TraversalResult result = routeService.reachableLocations(graph, locations[0].locationId());
        System.out.println("Reachable locations from " + locations[0].name());
        System.out.println(join(result.order()));
        printTrace(result.trace());
    }

    private void generateMinimumConnectionNetwork() throws Exception {
        AdjacencyListGraph graph = buildCurrentGraph();
        MinimumSpanningTreeResult result = routeService.kruskalNetwork(graph);
        System.out.println("Minimum connection network");
        for (var edge : result.edges()) {
            System.out.println(edge.fromLocationId() + " - " + edge.toLocationId() + " weight=" + edge.weight());
        }
        System.out.println("Total weight: " + result.totalWeight());
        printTrace(result.trace());
    }

    private void assignRidersGreedy() throws Exception {
        Order[] orders = firstOrders(requireOrders(), 3);
        Rider[] riders = firstRiders(requireRiders(), Math.min(3, orders.length));
        AssignmentResult result = optimizationService.assignRidersGreedily(orders, riders, buildAssignmentCosts(orders, riders));
        System.out.println("Greedy rider assignments");
        for (var assignment : result.assignments()) {
            System.out.println("Order " + assignment.orderId() + " -> Rider " + assignment.riderId() + " cost=" + assignment.cost());
        }
        System.out.println("Total cost: " + result.totalCost());
        System.out.println(optimizationService.greedyFailureCounterexample());
    }

    private void selectOrdersDynamicProgramming() throws Exception {
        OrderSelectionResult result = optimizationService.selectOrdersWithinDistance(requireOrders(), 6);
        System.out.println("Dynamic programming order selection, max distance units=6");
        for (Order order : result.selectedOrders()) {
            System.out.println("Selected order " + order.orderId() + " urgency=" + order.urgency() + " distance=" + order.estimatedDistance());
        }
        System.out.println("Total urgency value: " + result.totalValue());
        System.out.println("Total distance cost: " + result.totalCost());
        printTrace(result.trace());
    }

    private AdjacencyListGraph buildCurrentGraph() throws Exception {
        return routeService.buildGraph(requireLocations(), requireRoads());
    }

    private Location[] requireLocations() throws Exception {
        Location[] locations = databaseService.getLocations();
        requireData(locations.length, "locations");
        return locations;
    }

    private Road[] requireRoads() throws Exception {
        Road[] roads = databaseService.getRoads();
        requireData(roads.length, "roads");
        return roads;
    }

    private Rider[] requireRiders() throws Exception {
        Rider[] riders = databaseService.getRiders();
        requireData(riders.length, "riders");
        return riders;
    }

    private Order[] requireOrders() throws Exception {
        Order[] orders = databaseService.getOrders();
        requireData(orders.length, "orders");
        return orders;
    }

    private void requireData(int count, String entityName) {
        if (count == 0) {
            throw new IllegalStateException("No " + entityName + " found. Run option 2 to import CSV seed data first.");
        }
    }

    private void printOrders(String title, Order[] orders) {
        System.out.println(title);
        for (Order order : orders) {
            System.out.println(order.orderId() + ". category=" + order.category() + ", urgency=" + order.urgency()
                    + ", deadline=" + order.deadline() + ", distance=" + order.estimatedDistance());
        }
    }

    private Order[] firstOrders(Order[] orders, int max) {
        int count = Math.min(max, orders.length);
        Order[] copy = new Order[count];
        for (int i = 0; i < count; i++) {
            copy[i] = orders[i];
        }
        return copy;
    }

    private Rider[] firstRiders(Rider[] riders, int max) {
        int count = Math.min(max, riders.length);
        Rider[] copy = new Rider[count];
        for (int i = 0; i < count; i++) {
            copy[i] = riders[i];
        }
        return copy;
    }

    private double[][] buildAssignmentCosts(Order[] orders, Rider[] riders) {
        double[][] costs = new double[orders.length][riders.length];
        for (int orderIndex = 0; orderIndex < orders.length; orderIndex++) {
            for (int riderIndex = 0; riderIndex < riders.length; riderIndex++) {
                costs[orderIndex][riderIndex] = Math.abs(riders[riderIndex].currentLocationId() - orders[orderIndex].sourceLocationId())
                        + orders[orderIndex].estimatedDistance();
            }
        }
        return costs;
    }

    private String join(int[] values) {
        if (values.length == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(" -> ");
            }
            builder.append(values[i]);
        }
        return builder.toString();
    }

    private void printTrace(TraceStep[] trace) {
        int limit = Math.min(trace.length, 6);
        for (int i = 0; i < limit; i++) {
            System.out.println("Trace " + trace[i].stepNumber() + ": " + trace[i].action() + " | " + trace[i].state());
        }
    }
}
