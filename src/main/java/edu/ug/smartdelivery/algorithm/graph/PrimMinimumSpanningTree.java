package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.graph.CustomGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphEdge;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;
import edu.ug.smartdelivery.datastructure.heap.CustomMinHeap;

public class PrimMinimumSpanningTree {
    public MinimumSpanningTreeResult build(CustomGraph graph, int startLocationId) {
        if (!graph.containsVertex(startLocationId)) {
            throw new IllegalArgumentException("start vertex not found: " + startLocationId);
        }
        GraphVertex[] vertices = graph.vertices();
        boolean[] visited = new boolean[vertices.length];
        GraphEdge[] selected = new GraphEdge[Math.max(0, vertices.length - 1)];
        TraceStep[] trace = new TraceStep[Math.max(1, vertices.length * vertices.length)];
        int selectedCount = 0;
        int traceCount = 0;
        double totalWeight = 0;
        CustomMinHeap<WeightedEdge> heap = new CustomMinHeap<>();

        visit(graph, vertices, visited, startLocationId, heap);
        while (!heap.isEmpty() && selectedCount < vertices.length - 1) {
            WeightedEdge candidate = heap.extractMin();
            if (visited[indexOf(vertices, candidate.edge.toLocationId())]) {
                continue;
            }
            selected[selectedCount++] = candidate.edge;
            totalWeight += candidate.edge.weight();
            trace[traceCount++] = new TraceStep(traceCount, "select " + candidate.edge.fromLocationId() + "-" + candidate.edge.toLocationId(), "weight=" + candidate.edge.weight());
            visit(graph, vertices, visited, candidate.edge.toLocationId(), heap);
        }
        return new MinimumSpanningTreeResult(trim(selected, selectedCount), totalWeight, trim(trace, traceCount));
    }

    private void visit(CustomGraph graph, GraphVertex[] vertices, boolean[] visited, int locationId, CustomMinHeap<WeightedEdge> heap) {
        visited[indexOf(vertices, locationId)] = true;
        GraphEdge[] neighbors = graph.neighborsOf(locationId);
        for (GraphEdge edge : neighbors) {
            if (!visited[indexOf(vertices, edge.toLocationId())]) {
                heap.insert(new WeightedEdge(edge));
            }
        }
    }

    private int indexOf(GraphVertex[] vertices, int locationId) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].locationId() == locationId) {
                return i;
            }
        }
        return -1;
    }

    private GraphEdge[] trim(GraphEdge[] values, int count) {
        GraphEdge[] copy = new GraphEdge[count];
        for (int i = 0; i < count; i++) {
            copy[i] = values[i];
        }
        return copy;
    }

    private TraceStep[] trim(TraceStep[] values, int count) {
        TraceStep[] copy = new TraceStep[count];
        for (int i = 0; i < count; i++) {
            copy[i] = values[i];
        }
        return copy;
    }

    private record WeightedEdge(GraphEdge edge) implements Comparable<WeightedEdge> {
        @Override
        public int compareTo(WeightedEdge other) {
            return Double.compare(edge.weight(), other.edge.weight());
        }
    }
}
