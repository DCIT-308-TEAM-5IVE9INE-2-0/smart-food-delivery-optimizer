package edu.ug.smartdelivery.evidence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ug.smartdelivery.algorithm.dynamicprogramming.DynamicOrderSelection;
import edu.ug.smartdelivery.algorithm.dynamicprogramming.OrderSelectionResult;
import edu.ug.smartdelivery.algorithm.graph.DijkstraShortestPath;
import edu.ug.smartdelivery.algorithm.graph.KruskalMinimumSpanningTree;
import edu.ug.smartdelivery.algorithm.graph.MinimumSpanningTreeResult;
import edu.ug.smartdelivery.algorithm.graph.ShortestPathResult;
import edu.ug.smartdelivery.algorithm.search.BinarySearch;
import edu.ug.smartdelivery.algorithm.sort.InsertionSort;
import edu.ug.smartdelivery.algorithm.sort.MergeSort;
import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.graph.AdjacencyListGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;
import edu.ug.smartdelivery.model.Order;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

class FinalTraceEvidenceTest {
    @Test
    void binarySearchTraceMatchesFinalEvidence() {
        Integer[] orderIds = {1, 2, 3, 4, 5, 6, 7, 8};

        List<TraceStep> trace = new BinarySearch().trace(orderIds, 7, Comparator.naturalOrder());

        assertEquals(4, trace.size());
        assertEquals("check mid=3", trace.get(0).action());
        assertEquals("check mid=5", trace.get(1).action());
        assertEquals("check mid=6", trace.get(2).action());
        assertEquals("target found", trace.get(3).action());
    }

    @Test
    void insertionSortTraceMatchesFinalEvidence() {
        Integer[] urgencies = {3, 2, 2, 1, 1, 2};

        List<TraceStep> trace = new InsertionSort().trace(urgencies, Comparator.naturalOrder());

        assertEquals(6, trace.size());
        assertArrayEquals(new Integer[] {1, 1, 2, 2, 2, 3}, urgencies);
        assertEquals("[1, 1, 2, 2, 2, 3]", trace.get(5).state());
    }

    @Test
    void mergeSortTraceMatchesFinalEvidence() {
        Integer[] urgencies = {3, 2, 2, 1, 1, 2};

        List<TraceStep> trace = new MergeSort().trace(urgencies, Comparator.naturalOrder());

        assertEquals(6, trace.size());
        assertArrayEquals(new Integer[] {1, 1, 2, 2, 2, 3}, urgencies);
        assertEquals("merge 0-5", trace.get(5).action());
    }

    @Test
    void dijkstraAndKruskalTracesMatchFinalEvidence() {
        AdjacencyListGraph graph = evidenceGraph();

        ShortestPathResult shortestPath = new DijkstraShortestPath().findShortestPath(graph, 51, 25);
        MinimumSpanningTreeResult minimumTree = new KruskalMinimumSpanningTree().build(graph);

        assertArrayEquals(new int[] {51, 74, 23, 25}, shortestPath.path());
        assertEquals(12.39, shortestPath.distance(), 0.0001);
        assertEquals("settle 51", shortestPath.trace()[0].action());
        assertEquals("settle 25", shortestPath.trace()[3].action());
        assertEquals(3, minimumTree.edges().length);
        assertEquals(12.39, minimumTree.totalWeight(), 0.0001);
        assertEquals("accept 74-23", minimumTree.trace()[0].action());
    }

    @Test
    void dynamicProgrammingTraceMatchesFinalEvidence() {
        Order[] orders = {
                order(1, 3, 4.45),
                order(2, 2, 3.59),
                order(3, 2, 4.69),
                order(4, 1, 1.97)
        };

        OrderSelectionResult result = new DynamicOrderSelection().selectByUrgencyWithinDistance(orders, 17);

        assertEquals(8, result.totalValue());
        assertEquals(16, result.totalCost());
        assertEquals(4, result.selectedOrders().length);
        assertEquals("process order 4", result.trace()[3].action());
        assertEquals("bestValueAtCapacity=8", result.trace()[3].state());
    }

    private AdjacencyListGraph evidenceGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(new GraphVertex(51, "Central Cafeteria, CC"));
        graph.addVertex(new GraphVertex(74, "Block B"));
        graph.addVertex(new GraphVertex(23, "Sarbah Main"));
        graph.addVertex(new GraphVertex(25, "Mensah Sarbah Dining Hall"));
        graph.addEdge(51, 74, 4.36, true);
        graph.addEdge(74, 23, 3.51, true);
        graph.addEdge(23, 25, 4.52, true);
        return graph;
    }

    private Order order(int orderId, int urgency, double estimatedDistance) {
        return new Order(
                orderId,
                1,
                1,
                51,
                25,
                "Food",
                urgency,
                "2026-08-04T08:00",
                "2026-08-04T09:00",
                "PENDING",
                estimatedDistance,
                null
        );
    }
}
