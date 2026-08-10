package edu.ug.smartdelivery.app;

import edu.ug.smartdelivery.algorithm.dynamicprogramming.OrderSelectionResult;
import edu.ug.smartdelivery.algorithm.graph.MinimumSpanningTreeResult;
import edu.ug.smartdelivery.algorithm.graph.ShortestPathResult;
import edu.ug.smartdelivery.algorithm.graph.TraversalResult;
import edu.ug.smartdelivery.algorithm.greedy.AssignmentResult;
import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.graph.AdjacencyListGraph;
import edu.ug.smartdelivery.datastructure.heap.CustomPriorityQueue;
import edu.ug.smartdelivery.datastructure.queue.CustomQueue;
import edu.ug.smartdelivery.database.CsvImportResult;
import edu.ug.smartdelivery.database.DatabaseSummary;
import edu.ug.smartdelivery.model.AlgorithmRun;
import edu.ug.smartdelivery.model.AuditEvent;
import edu.ug.smartdelivery.model.Customer;
import edu.ug.smartdelivery.model.Location;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.PrioritizedOrder;
import edu.ug.smartdelivery.model.Restaurant;
import edu.ug.smartdelivery.model.Rider;
import edu.ug.smartdelivery.model.Road;
import edu.ug.smartdelivery.service.DatabaseService;
import edu.ug.smartdelivery.service.ExperimentService;
import edu.ug.smartdelivery.service.OptimizationService;
import edu.ug.smartdelivery.service.OrderComparators;
import edu.ug.smartdelivery.service.RouteService;
import edu.ug.smartdelivery.service.SearchService;
import edu.ug.smartdelivery.service.SortService;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleMenu {
    private static final int PAGE_LIMIT = 20;

    private final Scanner scanner;
    private final DatabaseService databaseService;
    private final SearchService searchService;
    private final SortService sortService;
    private final RouteService routeService;
    private final OptimizationService optimizationService;
    private final ExperimentService experimentService;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        this.databaseService = new DatabaseService();
        this.searchService = new SearchService();
        this.sortService = new SortService();
        this.routeService = new RouteService();
        this.optimizationService = new OptimizationService();
        this.experimentService = new ExperimentService();
    }

    public void start() {
        boolean running = true;
        while (running) {
            printHeader("SMART FOOD DELIVERY OPERATIONS OPTIMIZER");
            System.out.println("1. Data Setup");
            System.out.println("2. Browse Delivery Data");
            System.out.println("3. Order Dispatch");
            System.out.println("4. Search And Sort");
            System.out.println("5. Routes And Graphs");
            System.out.println("6. Optimization");
            System.out.println("7. Performance Lab");
            System.out.println("8. Audit And Undo");
            System.out.println("9. Guided Demo");
            System.out.println("0. Exit");

            int choice = readInt("Select option", 0, 9);
            try {
                switch (choice) {
                    case 0 -> {
                        System.out.println("Exiting SMART FOOD DELIVERY.");
                        running = false;
                    }
                    case 1 -> dataSetupMenu();
                    case 2 -> browseDataMenu();
                    case 3 -> orderDispatchMenu();
                    case 4 -> searchAndSortMenu();
                    case 5 -> routesAndGraphsMenu();
                    case 6 -> optimizationMenu();
                    case 7 -> performanceLabMenu();
                    case 8 -> auditAndUndoMenu();
                    case 9 -> guidedDemoMenu();
                    default -> System.out.println("Unknown option: " + choice);
                }
            } catch (Exception exception) {
                System.out.println("Operation failed: " + exception.getMessage());
                pause();
            }
        }
    }

    private void dataSetupMenu() throws Exception {
        boolean back = false;
        while (!back) {
            printHeader("Data Setup");
            System.out.println("1. Initialize Database");
            System.out.println("2. Import Default CSV Seed Data");
            System.out.println("3. View Database Summary");
            System.out.println("4. Show Dataset Requirement Status");
            System.out.println("0. Back");
            int choice = readInt("Select option", 0, 4);
            switch (choice) {
                case 0 -> back = true;
                case 1 -> initializeDatabase();
                case 2 -> importCsvSeedData();
                case 3 -> viewDatabaseSummary();
                case 4 -> showDatasetRequirementStatus();
                default -> System.out.println("Unknown option: " + choice);
            }
            pauseIfContinuing(choice);
        }
    }

    private void browseDataMenu() throws Exception {
        boolean back = false;
        while (!back) {
            printHeader("Browse Delivery Data");
            System.out.println("1. Locations");
            System.out.println("2. Roads");
            System.out.println("3. Restaurants");
            System.out.println("4. Customers");
            System.out.println("5. Riders");
            System.out.println("6. Orders");
            System.out.println("0. Back");
            int choice = readInt("Select option", 0, 6);
            switch (choice) {
                case 0 -> back = true;
                case 1 -> listLocations();
                case 2 -> listRoads();
                case 3 -> listRestaurants();
                case 4 -> listCustomers();
                case 5 -> listRiders();
                case 6 -> listOrders();
                default -> System.out.println("Unknown option: " + choice);
            }
            pauseIfContinuing(choice);
        }
    }

    private void orderDispatchMenu() throws Exception {
        boolean back = false;
        while (!back) {
            printHeader("Order Dispatch");
            System.out.println("1. Process Orders Using FIFO");
            System.out.println("2. Process Orders Using Priority");
            System.out.println("0. Back");
            int choice = readInt("Select option", 0, 2);
            switch (choice) {
                case 0 -> back = true;
                case 1 -> processOrdersFifo();
                case 2 -> processOrdersByPriority();
                default -> System.out.println("Unknown option: " + choice);
            }
            pauseIfContinuing(choice);
        }
    }

    private void searchAndSortMenu() throws Exception {
        boolean back = false;
        while (!back) {
            printHeader("Search And Sort");
            System.out.println("1. Search Order By ID");
            System.out.println("2. Sort Orders");
            System.out.println("0. Back");
            int choice = readInt("Select option", 0, 2);
            switch (choice) {
                case 0 -> back = true;
                case 1 -> searchOrderById();
                case 2 -> sortOrdersInteractive();
                default -> System.out.println("Unknown option: " + choice);
            }
            pauseIfContinuing(choice);
        }
    }

    private void routesAndGraphsMenu() throws Exception {
        boolean back = false;
        while (!back) {
            printHeader("Routes And Graphs");
            System.out.println("1. BFS Reachable Locations");
            System.out.println("2. DFS Traversal");
            System.out.println("3. Dijkstra Shortest Route");
            System.out.println("4. Prim Minimum Connection Network");
            System.out.println("5. Kruskal Minimum Connection Network");
            System.out.println("0. Back");
            int choice = readInt("Select option", 0, 5);
            switch (choice) {
                case 0 -> back = true;
                case 1 -> displayReachableLocations();
                case 2 -> displayDepthTraversal();
                case 3 -> findShortestDeliveryRoute();
                case 4 -> generatePrimConnectionNetwork();
                case 5 -> generateKruskalConnectionNetwork();
                default -> System.out.println("Unknown option: " + choice);
            }
            pauseIfContinuing(choice);
        }
    }

    private void optimizationMenu() throws Exception {
        boolean back = false;
        while (!back) {
            printHeader("Optimization");
            System.out.println("1. Greedy Rider Assignment");
            System.out.println("2. Brute Force Rider Assignment");
            System.out.println("3. Dynamic Programming Order Selection");
            System.out.println("4. Explain Greedy Failure Counterexample");
            System.out.println("0. Back");
            int choice = readInt("Select option", 0, 4);
            switch (choice) {
                case 0 -> back = true;
                case 1 -> assignRidersGreedy();
                case 2 -> assignRidersBruteForce();
                case 3 -> selectOrdersDynamicProgramming();
                case 4 -> System.out.println(optimizationService.greedyFailureCounterexample());
                default -> System.out.println("Unknown option: " + choice);
            }
            pauseIfContinuing(choice);
        }
    }

    private void performanceLabMenu() throws Exception {
        boolean back = false;
        while (!back) {
            printHeader("Performance Lab");
            System.out.println("1. Run Default Experiments");
            System.out.println("2. View Stored Results");
            System.out.println("3. Show Graph Command");
            System.out.println("0. Back");
            int choice = readInt("Select option", 0, 3);
            switch (choice) {
                case 0 -> back = true;
                case 1 -> runAlgorithmExperiments();
                case 2 -> viewPerformanceResults();
                case 3 -> showGraphCommand();
                default -> System.out.println("Unknown option: " + choice);
            }
            pauseIfContinuing(choice);
        }
    }

    private void auditAndUndoMenu() throws Exception {
        printHeader("Audit And Undo");
        AuditEvent[] events = databaseService.getAuditEvents();
        if (events.length == 0) {
            System.out.println("No audit events yet. Process or assign an order to create database-backed history.");
            pause();
            return;
        }
        System.out.println("Audit Events");
        for (int i = 0; i < Math.min(events.length, PAGE_LIMIT); i++) {
            AuditEvent event = events[i];
            System.out.println(event.eventId() + ". " + event.eventType()
                    + " " + event.entityType() + "#" + event.entityId()
                    + " | " + event.previousValue()
                    + " -> " + event.newValue()
                    + " @ " + event.eventTime());
        }
        printLimitedNotice(events.length);
        pause();
    }

    private void guidedDemoMenu() throws Exception {
        printHeader("Guided Demo");
        System.out.println("Recommended live demo sequence:");
        System.out.println("1. Initialize database");
        System.out.println("2. Import CSV seed data");
        System.out.println("3. Show database summary");
        System.out.println("4. Browse locations, riders and orders");
        System.out.println("5. Run FIFO and priority dispatch");
        System.out.println("6. Search and sort orders");
        System.out.println("7. Run Dijkstra, BFS/DFS and MST");
        System.out.println("8. Run greedy assignment and DP selection");
        System.out.println("9. Run/view performance lab");
        System.out.println("10. Point to tests and evidence docs");
        if (readYesNo("Run setup steps 1-3 now?")) {
            initializeDatabase();
            importCsvSeedData();
            viewDatabaseSummary();
        }
        pause();
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

    private void showDatasetRequirementStatus() throws Exception {
        databaseService.initializeDatabase();
        DatabaseSummary summary = databaseService.getSummary();
        System.out.println("Dataset Requirement Status");
        printRequirement("Locations", summary.locations(), 50);
        printRequirement("Roads", summary.roads(), 100);
        printRequirement("Riders/resources", summary.riders(), 30);
        printRequirement("Orders/service requests", summary.orders(), 300);
        printRequirement("Algorithm runs", summary.algorithmRuns(), 30);
    }

    private void listLocations() throws Exception {
        Location[] locations = requireLocations();
        System.out.println("Locations");
        for (int i = 0; i < Math.min(locations.length, PAGE_LIMIT); i++) {
            Location location = locations[i];
            System.out.println(location.locationId() + ". " + location.name() + " - " + location.area() + " (" + location.type() + ")");
        }
        printLimitedNotice(locations.length);
    }

    private void listRoads() throws Exception {
        Road[] roads = requireRoads();
        Location[] locations = requireLocations();
        System.out.println("Roads");
        for (int i = 0; i < Math.min(roads.length, PAGE_LIMIT); i++) {
            Road road = roads[i];
            System.out.println(road.roadId() + ". " + locationName(locations, road.fromLocationId())
                    + " -> " + locationName(locations, road.toLocationId())
                    + ", distance=" + road.distanceKm() + "km, time=" + road.travelTimeMinutes()
                    + "min, condition=" + road.roadConditionWeight());
        }
        printLimitedNotice(roads.length);
    }

    private void listRestaurants() throws Exception {
        Restaurant[] restaurants = databaseService.getRestaurants();
        Location[] locations = requireLocations();
        requireData(restaurants.length, "restaurants");
        System.out.println("Restaurants");
        for (int i = 0; i < Math.min(restaurants.length, PAGE_LIMIT); i++) {
            Restaurant restaurant = restaurants[i];
            System.out.println(restaurant.restaurantId() + ". " + restaurant.name()
                    + " at " + locationName(locations, restaurant.locationId())
                    + " (" + restaurant.status() + ")");
        }
        printLimitedNotice(restaurants.length);
    }

    private void listCustomers() throws Exception {
        Customer[] customers = databaseService.getCustomers();
        Location[] locations = requireLocations();
        requireData(customers.length, "customers");
        System.out.println("Customers");
        for (int i = 0; i < Math.min(customers.length, PAGE_LIMIT); i++) {
            Customer customer = customers[i];
            System.out.println(customer.customerId() + ". " + customer.name()
                    + " default location=" + locationName(locations, customer.defaultLocationId()));
        }
        printLimitedNotice(customers.length);
    }

    private void listRiders() throws Exception {
        Rider[] riders = requireRiders();
        Location[] locations = requireLocations();
        System.out.println("Riders");
        for (int i = 0; i < Math.min(riders.length, PAGE_LIMIT); i++) {
            Rider rider = riders[i];
            System.out.println(rider.riderId() + ". " + rider.name() + " - " + rider.vehicleType()
                    + ", status=" + rider.availabilityStatus()
                    + ", currentLocation=" + locationName(locations, rider.currentLocationId()));
        }
        printLimitedNotice(riders.length);
    }

    private void listOrders() throws Exception {
        Order[] orders = requireOrders();
        printOrders("Food Orders", orders, Math.min(orders.length, PAGE_LIMIT));
        printLimitedNotice(orders.length);
    }

    private void processOrdersFifo() throws Exception {
        Order[] selectedOrders = chooseOrderPrefix();
        CustomQueue<Order> queue = new CustomQueue<>();
        for (Order order : selectedOrders) {
            queue.enqueue(order);
        }
        System.out.println("FIFO queue before processing: " + queue.snapshot());
        if (!readYesNo("Mark these orders as DISPATCHED in the database?")) {
            System.out.println("Cancelled. No database changes were made.");
            return;
        }
        while (!queue.isEmpty()) {
            Order order = queue.dequeue();
            databaseService.markOrderDispatched(order);
            System.out.println("Process order " + order.orderId() + " submitted at " + order.timeSubmitted());
        }
        System.out.println("Database synced. Processed orders are now DISPATCHED and audit events were recorded.");
    }

    private void processOrdersByPriority() throws Exception {
        Order[] selectedOrders = chooseOrderPrefix();
        CustomPriorityQueue<PrioritizedOrder> queue = new CustomPriorityQueue<>();
        for (Order order : selectedOrders) {
            queue.insert(new PrioritizedOrder(order, order.urgency()));
        }
        System.out.println("Priority queue before processing: " + queue.snapshot());
        if (!readYesNo("Mark these orders as DISPATCHED in the database?")) {
            System.out.println("Cancelled. No database changes were made.");
            return;
        }
        while (!queue.isEmpty()) {
            PrioritizedOrder prioritizedOrder = queue.extractMin();
            databaseService.markOrderDispatched(prioritizedOrder.order());
            System.out.println("Process order " + prioritizedOrder.order().orderId()
                    + " urgency=" + prioritizedOrder.priorityScore()
                    + " deadline=" + prioritizedOrder.order().deadline());
        }
        System.out.println("Database synced. Processed orders are now DISPATCHED and audit events were recorded.");
    }

    private void searchOrderById() throws Exception {
        Order[] orders = requireOrders();
        printOrders("Available Orders", orders, Math.min(orders.length, PAGE_LIMIT));
        int orderId = readInt("Enter order ID to search", 1, Integer.MAX_VALUE);
        System.out.println("1. Linear Search");
        System.out.println("2. Binary Search");
        int algorithm = readInt("Choose search algorithm", 1, 2);

        Optional<Order> result;
        if (algorithm == 1) {
            result = searchService.findOrderLinear(orders, orderId);
            System.out.println("Linear search scans records in their current order.");
        } else {
            sortService.mergeSortOrders(orders, OrderComparators.byId());
            result = searchService.findOrderBinary(orders, orderId);
            System.out.println("Binary search sorted orders by ID before searching.");
        }

        result.ifPresentOrElse(
                order -> System.out.println("Found order " + order.orderId() + " category=" + order.category()
                        + ", urgency=" + order.urgency() + ", status=" + order.status()),
                () -> System.out.println("Order not found: " + orderId)
        );
    }

    private void sortOrdersInteractive() throws Exception {
        Order[] orders = requireOrders();
        Comparator<Order> comparator = chooseOrderComparator();
        int algorithm = chooseSortAlgorithm();

        System.out.println("Before sorting");
        printOrders("Sample", orders, Math.min(orders.length, 8));
        switch (algorithm) {
            case 1 -> sortService.selectionSortOrders(orders, comparator);
            case 2 -> sortService.insertionSortOrders(orders, comparator);
            case 3 -> sortService.mergeSortOrders(orders, comparator);
            case 4 -> sortService.quickSortOrders(orders, comparator);
            default -> throw new IllegalArgumentException("Unknown sort algorithm: " + algorithm);
        }
        System.out.println("After sorting");
        printOrders("Sorted Orders", orders, Math.min(orders.length, PAGE_LIMIT));
    }

    private void findShortestDeliveryRoute() throws Exception {
        AdjacencyListGraph graph = buildCurrentGraph();
        Location[] locations = requireLocations();
        int source = chooseLocationId("Choose source location", locations);
        int destination = chooseLocationId("Choose destination location", locations);
        ShortestPathResult result = routeService.shortestRoute(graph, source, destination);
        System.out.println("Shortest route from " + locationName(locations, source) + " to " + locationName(locations, destination));
        if (!result.reachable()) {
            System.out.println("Destination is unreachable from the selected source.");
        } else {
            System.out.println("Distance/cost: " + result.distance());
            System.out.println("Path: " + joinLocationNames(result.path(), locations));
        }
        printTrace(result.trace());
    }

    private void displayReachableLocations() throws Exception {
        AdjacencyListGraph graph = buildCurrentGraph();
        Location[] locations = requireLocations();
        int source = chooseLocationId("Choose BFS start location", locations);
        TraversalResult result = routeService.reachableLocations(graph, source);
        System.out.println("BFS reachable order from " + locationName(locations, source));
        System.out.println(joinLocationNames(result.order(), locations));
        printTrace(result.trace());
    }

    private void displayDepthTraversal() throws Exception {
        AdjacencyListGraph graph = buildCurrentGraph();
        Location[] locations = requireLocations();
        int source = chooseLocationId("Choose DFS start location", locations);
        TraversalResult result = routeService.depthTraversal(graph, source);
        System.out.println("DFS traversal order from " + locationName(locations, source));
        System.out.println(joinLocationNames(result.order(), locations));
        printTrace(result.trace());
    }

    private void generatePrimConnectionNetwork() throws Exception {
        AdjacencyListGraph graph = buildCurrentGraph();
        Location[] locations = requireLocations();
        int source = chooseLocationId("Choose Prim start location", locations);
        MinimumSpanningTreeResult result = routeService.primNetwork(graph, source);
        printMinimumNetwork("Prim minimum connection network", result, locations);
    }

    private void generateKruskalConnectionNetwork() throws Exception {
        MinimumSpanningTreeResult result = routeService.kruskalNetwork(buildCurrentGraph());
        printMinimumNetwork("Kruskal minimum connection network", result, requireLocations());
    }

    private void assignRidersGreedy() throws Exception {
        Order[] orders = chooseOrderSubset(8);
        Rider[] riders = chooseRiderSubset(Math.max(orders.length, 1));
        AssignmentResult result = optimizationService.assignRidersGreedily(orders, riders, buildAssignmentCosts(orders, riders));
        printAssignments("Greedy rider assignments", result);
        persistAssignmentsIfConfirmed(result, orders, riders);
    }

    private void assignRidersBruteForce() throws Exception {
        Order[] orders = chooseOrderSubset(8);
        Rider[] riders = chooseRiderSubset(orders.length);
        AssignmentResult result = optimizationService.assignRidersBruteForce(orders, riders, buildAssignmentCosts(orders, riders));
        printAssignments("Brute force rider assignments", result);
        persistAssignmentsIfConfirmed(result, orders, riders);
    }

    private void selectOrdersDynamicProgramming() throws Exception {
        Order[] orders = chooseOrderSubset(PAGE_LIMIT);
        int maxDistanceUnits = readInt("Enter max total distance/capacity units", 1, 1000);
        OrderSelectionResult result = optimizationService.selectOrdersWithinDistance(orders, maxDistanceUnits);
        System.out.println("Dynamic programming order selection, max distance units=" + maxDistanceUnits);
        for (Order order : result.selectedOrders()) {
            System.out.println("Selected order " + order.orderId() + " urgency=" + order.urgency() + " distance=" + order.estimatedDistance());
        }
        System.out.println("Total urgency value: " + result.totalValue());
        System.out.println("Total distance cost: " + result.totalCost());
        printTrace(result.trace());
    }

    private void runAlgorithmExperiments() throws Exception {
        System.out.println("Default performance lab uses input sizes 50, 100 and 200 with 3 trials.");
        if (!readYesNo("Run experiments now?")) {
            System.out.println("Cancelled.");
            return;
        }
        System.out.println("Running default performance lab. This may take a few seconds...");
        AlgorithmRun[] runs = experimentService.runDefaultExperiments();
        System.out.println("Experiment run complete.");
        System.out.println("Rows saved to database: " + runs.length);
        System.out.println("CSV exported to results/csv/algorithm_runs.csv");
        printAlgorithmRuns(runs, Math.min(12, runs.length));
    }

    private void viewPerformanceResults() throws Exception {
        AlgorithmRun[] runs = experimentService.getStoredRuns();
        requireData(runs.length, "algorithm runs");
        System.out.println("Stored performance results");
        printAlgorithmRuns(runs, Math.min(PAGE_LIMIT, runs.length));
        printLimitedNotice(runs.length);
        System.out.println("Total stored runs: " + runs.length);
    }

    private void showGraphCommand() {
        System.out.println("After running experiments, generate SVG graphs with:");
        System.out.println("python scripts/plot-results/plot_algorithm_runs.py");
        System.out.println("Output folder: results/graphs");
    }

    private Comparator<Order> chooseOrderComparator() {
        System.out.println("Sort by:");
        System.out.println("1. Urgency, highest first");
        System.out.println("2. Deadline, earliest first");
        System.out.println("3. Estimated distance, shortest first");
        System.out.println("4. Order ID");
        int choice = readInt("Choose sort key", 1, 4);
        return switch (choice) {
            case 1 -> OrderComparators.byUrgencyDescending();
            case 2 -> OrderComparators.byDeadline();
            case 3 -> OrderComparators.byEstimatedDistance();
            case 4 -> OrderComparators.byId();
            default -> OrderComparators.byId();
        };
    }

    private int chooseSortAlgorithm() {
        System.out.println("Sort algorithm:");
        System.out.println("1. Selection Sort");
        System.out.println("2. Insertion Sort");
        System.out.println("3. Merge Sort");
        System.out.println("4. Quick Sort");
        return readInt("Choose algorithm", 1, 4);
    }

    private int chooseLocationId(String prompt, Location[] locations) {
        System.out.println("Available locations");
        for (int i = 0; i < Math.min(locations.length, PAGE_LIMIT); i++) {
            Location location = locations[i];
            System.out.println(location.locationId() + ". " + location.name() + " - " + location.area());
        }
        printLimitedNotice(locations.length);
        while (true) {
            int locationId = readInt(prompt + " ID", 1, Integer.MAX_VALUE);
            if (hasLocation(locations, locationId)) {
                return locationId;
            }
            System.out.println("Location ID not found. Try again.");
        }
    }

    private Order[] chooseOrderPrefix() throws Exception {
        Order[] orders = requirePendingOrders();
        printOrders("Pending Orders", orders, Math.min(orders.length, PAGE_LIMIT));
        int count = readInt("How many orders should be processed?", 1, orders.length);
        return firstOrders(orders, count);
    }

    private Order[] chooseOrderSubset(int maxAllowed) throws Exception {
        Order[] orders = requirePendingOrders();
        printOrders("Pending Orders", orders, Math.min(orders.length, PAGE_LIMIT));
        int max = Math.min(maxAllowed, orders.length);
        int count = readInt("How many orders should be included? (max " + max + ")", 1, max);
        return firstOrders(orders, count);
    }

    private Rider[] chooseRiderSubset(int minimumRequired) throws Exception {
        Rider[] riders = requireRiders();
        listRiders();
        int min = Math.min(minimumRequired, riders.length);
        int count = readInt("How many riders should be included? (minimum " + min + ")", min, riders.length);
        return firstRiders(riders, count);
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

    private Order[] requirePendingOrders() throws Exception {
        Order[] orders = databaseService.getPendingOrders();
        requireData(orders.length, "pending orders");
        return orders;
    }

    private void requireData(int count, String entityName) {
        if (count == 0) {
            throw new IllegalStateException("No " + entityName + " found. Use Data Setup -> Import Default CSV Seed Data first.");
        }
    }

    private void printOrders(String title, Order[] orders, int limit) {
        System.out.println(title);
        for (int i = 0; i < Math.min(orders.length, limit); i++) {
            Order order = orders[i];
            System.out.println(order.orderId() + ". category=" + order.category()
                    + ", urgency=" + order.urgency()
                    + ", deadline=" + order.deadline()
                    + ", distance=" + order.estimatedDistance()
                    + ", status=" + order.status());
        }
    }

    private void printMinimumNetwork(String title, MinimumSpanningTreeResult result, Location[] locations) {
        System.out.println(title);
        for (var edge : result.edges()) {
            System.out.println(locationName(locations, edge.fromLocationId()) + " - "
                    + locationName(locations, edge.toLocationId()) + " weight=" + edge.weight());
        }
        System.out.println("Total weight: " + result.totalWeight());
        printTrace(result.trace());
    }

    private void printAssignments(String title, AssignmentResult result) {
        System.out.println(title);
        for (var assignment : result.assignments()) {
            System.out.println("Order " + assignment.orderId() + " -> Rider " + assignment.riderId() + " cost=" + assignment.cost());
        }
        System.out.println("Total cost: " + result.totalCost());
        printTrace(result.trace());
    }

    private void persistAssignmentsIfConfirmed(AssignmentResult result, Order[] orders, Rider[] riders) throws Exception {
        if (result.assignments().length == 0) {
            return;
        }
        if (!readYesNo("Save these assignments to the database?")) {
            System.out.println("Cancelled. No database changes were made.");
            return;
        }
        for (var assignment : result.assignments()) {
            Order order = findOrder(orders, assignment.orderId());
            Rider rider = findRider(riders, assignment.riderId());
            databaseService.assignOrderToRider(order, rider);
        }
        System.out.println("Database synced. Orders are now ASSIGNED, riders are BUSY, and audit events were recorded.");
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

    private Order findOrder(Order[] orders, int orderId) {
        for (Order order : orders) {
            if (order.orderId() == orderId) {
                return order;
            }
        }
        throw new IllegalArgumentException("order not found: " + orderId);
    }

    private Rider findRider(Rider[] riders, int riderId) {
        for (Rider rider : riders) {
            if (rider.riderId() == riderId) {
                return rider;
            }
        }
        throw new IllegalArgumentException("rider not found: " + riderId);
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

    private String joinLocationNames(int[] values, Location[] locations) {
        if (values.length == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(" -> ");
            }
            builder.append(locationName(locations, values[i]));
        }
        return builder.toString();
    }

    private String locationName(Location[] locations, int locationId) {
        for (Location location : locations) {
            if (location.locationId() == locationId) {
                return location.name() + " [" + location.locationId() + "]";
            }
        }
        return "Location " + locationId;
    }

    private boolean hasLocation(Location[] locations, int locationId) {
        for (Location location : locations) {
            if (location.locationId() == locationId) {
                return true;
            }
        }
        return false;
    }

    private void printTrace(TraceStep[] trace) {
        if (trace.length == 0) {
            System.out.println("Trace: []");
            return;
        }
        int limit = Math.min(trace.length, 8);
        for (int i = 0; i < limit; i++) {
            System.out.println("Trace " + trace[i].stepNumber() + ": " + trace[i].action() + " | " + trace[i].state());
        }
        if (trace.length > limit) {
            System.out.println("... " + (trace.length - limit) + " more trace steps");
        }
    }

    private void printAlgorithmRuns(AlgorithmRun[] runs, int limit) {
        for (int i = 0; i < Math.min(runs.length, limit); i++) {
            AlgorithmRun run = runs[i];
            System.out.println(run.runId() + ". " + run.algorithmName()
                    + " n=" + run.inputSize()
                    + ", trial=" + run.trialNumber()
                    + ", time=" + run.executionTimeNs() + "ns"
                    + ", memory=" + String.format("%.2f", run.memoryKb()) + "KB");
        }
    }

    private void printRequirement(String label, int actual, int required) {
        String status = actual >= required ? "OK" : "Needs " + (required - actual) + " more";
        System.out.println(label + ": " + actual + "/" + required + " - " + status);
    }

    private void printLimitedNotice(int totalCount) {
        if (totalCount > PAGE_LIMIT) {
            System.out.println("Showing first " + PAGE_LIMIT + " of " + totalCount + " records.");
        }
    }

    private void printHeader(String title) {
        System.out.println();
        System.out.println("==============================================");
        System.out.println(" " + title);
        System.out.println("==============================================");
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Enter a number from " + min + " to " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if ("y".equals(input) || "yes".equals(input)) {
                return true;
            }
            if ("n".equals(input) || "no".equals(input)) {
                return false;
            }
            System.out.println("Enter y or n.");
        }
    }

    private void pauseIfContinuing(int choice) {
        if (choice != 0) {
            pause();
        }
    }

    private void pause() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
