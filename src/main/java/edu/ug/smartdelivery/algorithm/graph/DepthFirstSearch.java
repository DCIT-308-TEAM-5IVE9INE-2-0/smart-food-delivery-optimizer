package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.graph.CustomGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphEdge;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;

public class DepthFirstSearch {
    private CustomGraph graph;
    private GraphVertex[] vertices;
    private boolean[] visited;
    private int[] order;
    private TraceStep[] trace;
    private int orderCount;
    private int traceCount;

    public TraversalResult traverse(CustomGraph graph, int startLocationId) {
        if (!graph.containsVertex(startLocationId)) {
            throw new IllegalArgumentException("start vertex not found: " + startLocationId);
        }
        this.graph = graph;
        this.vertices = graph.vertices();
        this.visited = new boolean[vertices.length];
        this.order = new int[vertices.length];
        this.trace = new TraceStep[vertices.length];
        this.orderCount = 0;
        this.traceCount = 0;
        dfs(startLocationId);
        return new TraversalResult(trim(order, orderCount), trim(trace, traceCount));
    }

    private void dfs(int locationId) {
        int index = indexOf(locationId);
        visited[index] = true;
        order[orderCount++] = locationId;
        trace[traceCount++] = new TraceStep(traceCount, "visit " + locationId, "depthOrder=" + orderCount);
        GraphEdge[] neighbors = graph.neighborsOf(locationId);
        for (GraphEdge edge : neighbors) {
            int neighborIndex = indexOf(edge.toLocationId());
            if (!visited[neighborIndex]) {
                dfs(edge.toLocationId());
            }
        }
    }

    private int indexOf(int locationId) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].locationId() == locationId) {
                return i;
            }
        }
        return -1;
    }

    private int[] trim(int[] values, int count) {
        int[] copy = new int[count];
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
}
