package edu.ug.smartdelivery.algorithm.graph;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.datastructure.disjointset.CustomDisjointSet;
import edu.ug.smartdelivery.datastructure.graph.AdjacencyListGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;
import org.junit.jupiter.api.Test;

class GraphAlgorithmsTest {
    @Test
    void breadthFirstSearchFindsReachableLocations() {
        TraversalResult result = new BreadthFirstSearch().traverse(sampleGraph(), 1);

        assertEquals(4, result.order().length);
        assertEquals(1, result.order()[0]);
        assertTrue(result.trace().length > 0);
    }

    @Test
    void depthFirstSearchTraversesGraph() {
        TraversalResult result = new DepthFirstSearch().traverse(sampleGraph(), 1);

        assertEquals(4, result.order().length);
        assertEquals(1, result.order()[0]);
    }

    @Test
    void dijkstraReturnsShortestPath() {
        ShortestPathResult result = new DijkstraShortestPath().findShortestPath(sampleGraph(), 1, 4);

        assertEquals(4.0, result.distance());
        assertArrayEquals(new int[] {1, 2, 3, 4}, result.path());
        assertTrue(result.reachable());
    }

    @Test
    void disjointSetUnionsAndFindsConnections() {
        CustomDisjointSet set = new CustomDisjointSet();
        set.makeSet(1);
        set.makeSet(2);
        set.makeSet(3);

        set.union(1, 2);

        assertTrue(set.connected(1, 2));
    }

    @Test
    void primBuildsMinimumNetwork() {
        MinimumSpanningTreeResult result = new PrimMinimumSpanningTree().build(sampleGraph(), 1);

        assertEquals(3, result.edges().length);
        assertEquals(4.0, result.totalWeight());
    }

    @Test
    void kruskalBuildsMinimumNetwork() {
        MinimumSpanningTreeResult result = new KruskalMinimumSpanningTree().build(sampleGraph());

        assertEquals(3, result.edges().length);
        assertEquals(4.0, result.totalWeight());
        assertTrue(result.trace().length > 0);
    }

    private AdjacencyListGraph sampleGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(new GraphVertex(1, "Legon Hall"));
        graph.addVertex(new GraphVertex(2, "Bush Canteen"));
        graph.addVertex(new GraphVertex(3, "JQB"));
        graph.addVertex(new GraphVertex(4, "Madina"));
        graph.addEdge(1, 2, 1.0, true);
        graph.addEdge(1, 3, 5.0, true);
        graph.addEdge(2, 3, 2.0, true);
        graph.addEdge(3, 4, 1.0, true);
        graph.addEdge(2, 4, 8.0, true);
        return graph;
    }
}
