package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.algorithm.sort.MergeSort;
import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.disjointset.CustomDisjointSet;
import edu.ug.smartdelivery.datastructure.graph.CustomGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphEdge;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;
import java.util.Comparator;

public class KruskalMinimumSpanningTree {
    public MinimumSpanningTreeResult build(CustomGraph graph) {
        GraphVertex[] vertices = graph.vertices();
        GraphEdge[] edges = uniqueUndirectedEdges(graph.edges());
        new MergeSort().sort(edges, Comparator.comparingDouble(GraphEdge::weight));

        CustomDisjointSet disjointSet = new CustomDisjointSet();
        for (GraphVertex vertex : vertices) {
            disjointSet.makeSet(vertex.locationId());
        }

        GraphEdge[] selected = new GraphEdge[Math.max(0, vertices.length - 1)];
        TraceStep[] trace = new TraceStep[Math.max(1, edges.length)];
        int selectedCount = 0;
        int traceCount = 0;
        double totalWeight = 0;

        for (GraphEdge edge : edges) {
            if (selectedCount == vertices.length - 1) {
                break;
            }
            if (disjointSet.union(edge.fromLocationId(), edge.toLocationId())) {
                selected[selectedCount++] = edge;
                totalWeight += edge.weight();
                trace[traceCount++] = new TraceStep(traceCount, "accept " + edge.fromLocationId() + "-" + edge.toLocationId(), "weight=" + edge.weight());
            } else {
                trace[traceCount++] = new TraceStep(traceCount, "reject cycle " + edge.fromLocationId() + "-" + edge.toLocationId(), "weight=" + edge.weight());
            }
        }
        return new MinimumSpanningTreeResult(trim(selected, selectedCount), totalWeight, trim(trace, traceCount));
    }

    private GraphEdge[] uniqueUndirectedEdges(GraphEdge[] edges) {
        GraphEdge[] unique = new GraphEdge[edges.length];
        int count = 0;
        for (GraphEdge edge : edges) {
            int a = Math.min(edge.fromLocationId(), edge.toLocationId());
            int b = Math.max(edge.fromLocationId(), edge.toLocationId());
            boolean seen = false;
            for (int i = 0; i < count; i++) {
                int existingA = Math.min(unique[i].fromLocationId(), unique[i].toLocationId());
                int existingB = Math.max(unique[i].fromLocationId(), unique[i].toLocationId());
                if (a == existingA && b == existingB) {
                    seen = true;
                    break;
                }
            }
            if (!seen) {
                unique[count++] = edge;
            }
        }
        return trim(unique, count);
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
}
