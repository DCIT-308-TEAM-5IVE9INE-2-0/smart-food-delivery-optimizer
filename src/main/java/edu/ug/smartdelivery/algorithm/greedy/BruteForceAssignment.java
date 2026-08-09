package edu.ug.smartdelivery.algorithm.greedy;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Rider;

public class BruteForceAssignment {
    private Order[] orders;
    private Rider[] riders;
    private double[][] costs;
    private boolean[] riderUsed;
    private RiderAssignment[] current;
    private RiderAssignment[] best;
    private double bestCost;
    private TraceStep[] trace;
    private int traceCount;

    public AssignmentResult assign(Order[] orders, Rider[] riders, double[][] costByOrderAndRider) {
        validateInputs(orders, riders, costByOrderAndRider);
        this.orders = orders;
        this.riders = riders;
        this.costs = costByOrderAndRider;
        this.riderUsed = new boolean[riders.length];
        this.current = new RiderAssignment[Math.min(orders.length, riders.length)];
        this.best = new RiderAssignment[Math.min(orders.length, riders.length)];
        this.bestCost = Double.POSITIVE_INFINITY;
        this.trace = new TraceStep[Math.max(1, factorialBound(Math.min(orders.length, riders.length)))];
        this.traceCount = 0;

        search(0, 0);
        return new AssignmentResult(trim(best, Math.min(orders.length, riders.length)), bestCost, trim(trace, traceCount));
    }

    private void search(int orderIndex, double runningCost) {
        if (orderIndex == current.length) {
            if (runningCost < bestCost) {
                bestCost = runningCost;
                for (int i = 0; i < current.length; i++) {
                    best[i] = current[i];
                }
                addTrace("new best", "cost=" + bestCost);
            }
            return;
        }
        for (int riderIndex = 0; riderIndex < riders.length; riderIndex++) {
            if (!riderUsed[riderIndex]) {
                riderUsed[riderIndex] = true;
                current[orderIndex] = new RiderAssignment(
                        orders[orderIndex].orderId(),
                        riders[riderIndex].riderId(),
                        costs[orderIndex][riderIndex]
                );
                search(orderIndex + 1, runningCost + costs[orderIndex][riderIndex]);
                riderUsed[riderIndex] = false;
            }
        }
    }

    private void addTrace(String action, String state) {
        if (traceCount < trace.length) {
            trace[traceCount] = new TraceStep(traceCount + 1, action, state);
            traceCount++;
        }
    }

    private void validateInputs(Order[] orders, Rider[] riders, double[][] costs) {
        if (orders == null || riders == null || costs == null) {
            throw new IllegalArgumentException("orders, riders and costs are required");
        }
        if (orders.length > riders.length) {
            throw new IllegalArgumentException("brute force assignment expects at least one rider per order");
        }
        if (orders.length > 8) {
            throw new IllegalArgumentException("brute force is limited to at most 8 orders for tractability");
        }
        if (costs.length != orders.length) {
            throw new IllegalArgumentException("cost row count must match order count");
        }
        for (double[] row : costs) {
            if (row == null || row.length != riders.length) {
                throw new IllegalArgumentException("each cost row must match rider count");
            }
        }
    }

    private int factorialBound(int value) {
        int result = 1;
        for (int i = 2; i <= value; i++) {
            result *= i;
        }
        return result + 1;
    }

    private RiderAssignment[] trim(RiderAssignment[] values, int count) {
        RiderAssignment[] copy = new RiderAssignment[count];
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
