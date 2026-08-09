package edu.ug.smartdelivery.algorithm.greedy;

import edu.ug.smartdelivery.datastructure.TraceStep;

public record AssignmentResult(RiderAssignment[] assignments, double totalCost, TraceStep[] trace) {
}
