package edu.ug.smartdelivery.evidence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ug.smartdelivery.algorithm.dynamicprogramming.DynamicOrderSelection;
import edu.ug.smartdelivery.algorithm.graph.DijkstraShortestPath;
import edu.ug.smartdelivery.algorithm.graph.ShortestPathResult;
import edu.ug.smartdelivery.algorithm.greedy.BruteForceAssignment;
import edu.ug.smartdelivery.algorithm.greedy.GreedyRiderAssignment;
import edu.ug.smartdelivery.algorithm.search.BinarySearch;
import edu.ug.smartdelivery.algorithm.search.LinearSearch;
import edu.ug.smartdelivery.datastructure.graph.AdjacencyListGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;
import edu.ug.smartdelivery.datastructure.queue.CustomCircularQueue;
import edu.ug.smartdelivery.datastructure.queue.CustomQueue;
import edu.ug.smartdelivery.datastructure.stack.CustomStack;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Rider;
import java.util.Comparator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class CorrectnessEvidenceTest {
    @Test
    void linearSearchReturnsMinusOneWhenFoodOrderAreaIsMissing() {
        String[] deliveryAreas = {"Legon", "Madina", "East Legon"};

        assertEquals(-1, new LinearSearch().find(deliveryAreas, "Adenta"));
    }

    @Test
    void binarySearchHandlesEmptyOrderList() {
        Integer[] orderIds = {};

        assertEquals(-1, new BinarySearch().find(orderIds, 42, Comparator.naturalOrder()));
    }

    @Test
    void binarySearchRejectsMissingComparator() {
        Integer[] orderIds = {10, 20, 30};

        assertThrows(NullPointerException.class, () -> new BinarySearch().find(orderIds, 20, null));
    }

    @Test
    void emptyStackAndQueueOperationsFailClearly() {
        CustomStack<String> undoStack = new CustomStack<>();
        CustomQueue<String> orderQueue = new CustomQueue<>();

        assertThrows(NoSuchElementException.class, undoStack::pop);
        assertThrows(NoSuchElementException.class, orderQueue::dequeue);
    }

    @Test
    void circularQueueRejectsOverflowAndPreservesWraparoundOrder() {
        CustomCircularQueue<Integer> queue = new CustomCircularQueue<>(2);
        queue.enqueue(101);
        queue.enqueue(102);

        assertThrows(IllegalStateException.class, () -> queue.enqueue(103));
        assertEquals(101, queue.dequeue());

        queue.enqueue(103);

        assertEquals(102, queue.dequeue());
        assertEquals(103, queue.dequeue());
    }

    @Test
    void dijkstraReportsDisconnectedDestinationAsUnreachable() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(new GraphVertex(1, "Legon Hall"));
        graph.addVertex(new GraphVertex(2, "Bush Canteen"));
        graph.addVertex(new GraphVertex(3, "Madina"));
        graph.addEdge(1, 2, 3.0, true);

        ShortestPathResult result = new DijkstraShortestPath().findShortestPath(graph, 1, 3);

        assertFalse(result.reachable());
        assertArrayEquals(new int[0], result.path());
    }

    @Test
    void greedyAssignmentRejectsMismatchedCostMatrix() {
        Order[] orders = {sampleOrder(1), sampleOrder(2)};
        Rider[] riders = {sampleRider(1)};
        double[][] incompleteCosts = {{2.0}};

        assertThrows(
                IllegalArgumentException.class,
                () -> new GreedyRiderAssignment().assign(orders, riders, incompleteCosts)
        );
    }

    @Test
    void bruteForceAssignmentRejectsMoreOrdersThanRiders() {
        Order[] orders = {sampleOrder(1), sampleOrder(2)};
        Rider[] riders = {sampleRider(1)};
        double[][] costs = {{2.0}, {3.0}};

        assertThrows(
                IllegalArgumentException.class,
                () -> new BruteForceAssignment().assign(orders, riders, costs)
        );
    }

    @Test
    void dynamicProgrammingRejectsNegativeCapacity() {
        Order[] orders = {sampleOrder(1)};

        assertThrows(
                IllegalArgumentException.class,
                () -> new DynamicOrderSelection().select(orders, new int[] {1}, new int[] {5}, -1)
        );
    }

    private Order sampleOrder(int orderId) {
        return new Order(
                orderId,
                1,
                1,
                1,
                2,
                "Lunch",
                3,
                "2026-08-09T12:00",
                "2026-08-09T12:45",
                "PENDING",
                2.0,
                null
        );
    }

    private Rider sampleRider(int riderId) {
        return new Rider(riderId, "Rider " + riderId, 1, "Motorbike", 2, "AVAILABLE", 1);
    }
}
