package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.algorithm.dynamicprogramming.DynamicOrderSelection;
import edu.ug.smartdelivery.algorithm.dynamicprogramming.OrderSelectionResult;
import edu.ug.smartdelivery.algorithm.greedy.AssignmentResult;
import edu.ug.smartdelivery.algorithm.greedy.BruteForceAssignment;
import edu.ug.smartdelivery.algorithm.greedy.GreedyRiderAssignment;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Rider;

public class OptimizationService {
    private final GreedyRiderAssignment greedyRiderAssignment = new GreedyRiderAssignment();
    private final BruteForceAssignment bruteForceAssignment = new BruteForceAssignment();
    private final DynamicOrderSelection dynamicOrderSelection = new DynamicOrderSelection();

    public AssignmentResult assignRidersGreedily(Order[] orders, Rider[] riders, double[][] costs) {
        return greedyRiderAssignment.assign(orders, riders, costs);
    }

    public AssignmentResult assignRidersBruteForce(Order[] orders, Rider[] riders, double[][] costs) {
        return bruteForceAssignment.assign(orders, riders, costs);
    }

    public OrderSelectionResult selectOrdersWithinDistance(Order[] orders, int maxDistanceUnits) {
        return dynamicOrderSelection.selectByUrgencyWithinDistance(orders, maxDistanceUnits);
    }

    public String greedyFailureCounterexample() {
        return greedyRiderAssignment.counterexampleExplanation();
    }
}
