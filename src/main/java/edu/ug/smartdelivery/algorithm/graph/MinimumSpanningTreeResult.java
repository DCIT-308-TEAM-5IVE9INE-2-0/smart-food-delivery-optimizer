package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.graph.GraphEdge;

public record MinimumSpanningTreeResult(GraphEdge[] edges, double totalWeight, TraceStep[] trace) {
}
