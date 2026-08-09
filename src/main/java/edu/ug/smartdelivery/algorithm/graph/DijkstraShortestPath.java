package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.graph.CustomGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphEdge;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;

public class DijkstraShortestPath {
    public ShortestPathResult findShortestPath(CustomGraph graph, int sourceLocationId, int targetLocationId) {
        if (!graph.containsVertex(sourceLocationId) || !graph.containsVertex(targetLocationId)) {
            throw new IllegalArgumentException("source and target must exist");
        }
        GraphVertex[] vertices = graph.vertices();
        int n = vertices.length;
        double[] distance = new double[n];
        int[] previous = new int[n];
        boolean[] visited = new boolean[n];
        TraceStep[] trace = new TraceStep[n];
        int traceCount = 0;

        for (int i = 0; i < n; i++) {
            distance[i] = Double.POSITIVE_INFINITY;
            previous[i] = -1;
        }
        distance[indexOf(vertices, sourceLocationId)] = 0;

        for (int step = 0; step < n; step++) {
            int currentIndex = minDistanceIndex(distance, visited);
            if (currentIndex < 0) {
                break;
            }
            visited[currentIndex] = true;
            int currentLocationId = vertices[currentIndex].locationId();
            trace[traceCount++] = new TraceStep(traceCount, "settle " + currentLocationId, "distance=" + distance[currentIndex]);
            if (currentLocationId == targetLocationId) {
                break;
            }
            GraphEdge[] neighbors = graph.neighborsOf(currentLocationId);
            for (GraphEdge edge : neighbors) {
                int neighborIndex = indexOf(vertices, edge.toLocationId());
                if (!visited[neighborIndex]) {
                    double candidate = distance[currentIndex] + edge.weight();
                    if (candidate < distance[neighborIndex]) {
                        distance[neighborIndex] = candidate;
                        previous[neighborIndex] = currentIndex;
                    }
                }
            }
        }

        int targetIndex = indexOf(vertices, targetLocationId);
        return new ShortestPathResult(
                sourceLocationId,
                targetLocationId,
                distance[targetIndex],
                buildPath(vertices, previous, targetIndex, distance[targetIndex]),
                trim(trace, traceCount)
        );
    }

    private int minDistanceIndex(double[] distance, boolean[] visited) {
        double best = Double.POSITIVE_INFINITY;
        int bestIndex = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && distance[i] < best) {
                best = distance[i];
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    private int[] buildPath(GraphVertex[] vertices, int[] previous, int targetIndex, double targetDistance) {
        if (Double.isInfinite(targetDistance)) {
            return new int[0];
        }
        int count = 0;
        int current = targetIndex;
        while (current >= 0) {
            count++;
            current = previous[current];
        }
        int[] path = new int[count];
        current = targetIndex;
        for (int i = count - 1; i >= 0; i--) {
            path[i] = vertices[current].locationId();
            current = previous[current];
        }
        return path;
    }

    private int indexOf(GraphVertex[] vertices, int locationId) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].locationId() == locationId) {
                return i;
            }
        }
        return -1;
    }

    private TraceStep[] trim(TraceStep[] values, int count) {
        TraceStep[] copy = new TraceStep[count];
        for (int i = 0; i < count; i++) {
            copy[i] = values[i];
        }
        return copy;
    }
}
