package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.graph.CustomGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphEdge;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;

public class BreadthFirstSearch {
    public TraversalResult traverse(CustomGraph graph, int startLocationId) {
        if (!graph.containsVertex(startLocationId)) {
            throw new IllegalArgumentException("start vertex not found: " + startLocationId);
        }
        GraphVertex[] vertices = graph.vertices();
        boolean[] visited = new boolean[vertices.length];
        int[] queue = new int[vertices.length];
        int[] order = new int[vertices.length];
        TraceStep[] trace = new TraceStep[vertices.length];
        int front = 0;
        int rear = 0;
        int orderCount = 0;
        int traceCount = 0;

        int startIndex = indexOf(vertices, startLocationId);
        visited[startIndex] = true;
        queue[rear++] = startLocationId;

        while (front < rear) {
            int current = queue[front++];
            order[orderCount++] = current;
            trace[traceCount++] = new TraceStep(traceCount, "visit " + current, "queueSize=" + (rear - front));
            GraphEdge[] neighbors = graph.neighborsOf(current);
            for (GraphEdge edge : neighbors) {
                int neighborIndex = indexOf(vertices, edge.toLocationId());
                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue[rear++] = edge.toLocationId();
                }
            }
        }
        return new TraversalResult(trim(order, orderCount), trim(trace, traceCount));
    }

    private int indexOf(GraphVertex[] vertices, int locationId) {
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
