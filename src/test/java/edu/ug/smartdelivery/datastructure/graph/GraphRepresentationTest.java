package edu.ug.smartdelivery.datastructure.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GraphRepresentationTest {
    @Test
    void adjacencyListStoresNeighborsAndEdges() {
        AdjacencyListGraph graph = sampleListGraph();

        assertEquals(3, graph.vertexCount());
        assertEquals(4, graph.edgeCount());
        assertEquals(2, graph.neighborsOf(1).length);
        assertTrue(graph.containsVertex(3));
    }

    @Test
    void adjacencyMatrixStoresWeights() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(new GraphVertex(1, "Legon"));
        graph.addVertex(new GraphVertex(2, "Madina"));
        graph.addEdge(1, 2, 5.5, true);

        assertEquals(2, graph.edgeCount());
        assertEquals(5.5, graph.weightBetween(2, 1));
    }

    private AdjacencyListGraph sampleListGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(new GraphVertex(1, "A"));
        graph.addVertex(new GraphVertex(2, "B"));
        graph.addVertex(new GraphVertex(3, "C"));
        graph.addEdge(1, 2, 1.0, true);
        graph.addEdge(1, 3, 2.0, true);
        return graph;
    }
}
