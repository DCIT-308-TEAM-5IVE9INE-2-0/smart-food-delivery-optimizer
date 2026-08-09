package edu.ug.smartdelivery.algorithm.greedy;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Rider;

public class GreedyRiderAssignment {
    public AssignmentResult assign(Order[] orders, Rider[] riders, double[][] costByOrderAndRider) {
        validateInputs(orders, riders, costByOrderAndRider);
        boolean[] riderUsed = new boolean[riders.length];
        RiderAssignment[] assignments = new RiderAssignment[Math.min(orders.length, riders.length)];
        TraceStep[] trace = new TraceStep[orders.length];
        int assignmentCount = 0;
        int traceCount = 0;
        double totalCost = 0;

        for (int orderIndex = 0; orderIndex < orders.length; orderIndex++) {
            int selectedRiderIndex = nearestAvailableRider(orderIndex, riderUsed, costByOrderAndRider);
            if (selectedRiderIndex < 0) {
                trace[traceCount++] = new TraceStep(traceCount, "no rider for order " + orders[orderIndex].orderId(), "all riders already assigned");
                continue;
            }
            riderUsed[selectedRiderIndex] = true;
            double cost = costByOrderAndRider[orderIndex][selectedRiderIndex];
            assignments[assignmentCount++] = new RiderAssignment(orders[orderIndex].orderId(), riders[selectedRiderIndex].riderId(), cost);
            totalCost += cost;
            trace[traceCount++] = new TraceStep(traceCount, "assign order " + orders[orderIndex].orderId(), "rider=" + riders[selectedRiderIndex].riderId() + ", cost=" + cost);
        }
        return new AssignmentResult(trim(assignments, assignmentCount), totalCost, trim(trace, traceCount));
    }

    public String counterexampleExplanation() {
        return "Greedy can fail when it assigns the nearest rider to the first order, leaving a much worse rider for a later order. "
                + "Example cost matrix [[1,2],[2,100]]: greedy picks cost 1 then 100 = 101, but optimal is 2 then 2 = 4.";
    }

    private int nearestAvailableRider(int orderIndex, boolean[] riderUsed, double[][] costs) {
        int selected = -1;
        double bestCost = Double.POSITIVE_INFINITY;
        for (int riderIndex = 0; riderIndex < riderUsed.length; riderIndex++) {
            if (!riderUsed[riderIndex] && costs[orderIndex][riderIndex] < bestCost) {
                bestCost = costs[orderIndex][riderIndex];
                selected = riderIndex;
            }
        }
        return selected;
    }

    private void validateInputs(Order[] orders, Rider[] riders, double[][] costs) {
        if (orders == null || riders == null || costs == null) {
            throw new IllegalArgumentException("orders, riders and costs are required");
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
