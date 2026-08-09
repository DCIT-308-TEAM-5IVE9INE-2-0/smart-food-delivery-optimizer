package edu.ug.smartdelivery.algorithm.dynamicprogramming;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.model.Order;

public record OrderSelectionResult(Order[] selectedOrders, int totalValue, int totalCost, int[][] table, TraceStep[] trace) {
}
