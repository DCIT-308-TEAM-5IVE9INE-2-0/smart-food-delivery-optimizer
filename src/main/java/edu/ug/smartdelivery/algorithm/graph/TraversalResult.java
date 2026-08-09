package edu.ug.smartdelivery.algorithm.graph;

import edu.ug.smartdelivery.datastructure.TraceStep;

public record TraversalResult(int[] order, TraceStep[] trace) {
}
