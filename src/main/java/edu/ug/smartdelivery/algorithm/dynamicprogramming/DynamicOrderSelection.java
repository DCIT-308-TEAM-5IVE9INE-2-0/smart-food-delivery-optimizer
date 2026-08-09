package edu.ug.smartdelivery.algorithm.dynamicprogramming;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.model.Order;

public class DynamicOrderSelection {
    public OrderSelectionResult select(Order[] orders, int[] costs, int[] values, int capacity) {
        validateInputs(orders, costs, values, capacity);
        int[][] table = new int[orders.length + 1][capacity + 1];
        TraceStep[] trace = new TraceStep[orders.length];
        int traceCount = 0;

        for (int i = 1; i <= orders.length; i++) {
            int orderCost = costs[i - 1];
            int orderValue = values[i - 1];
            for (int currentCapacity = 0; currentCapacity <= capacity; currentCapacity++) {
                if (orderCost <= currentCapacity) {
                    int include = orderValue + table[i - 1][currentCapacity - orderCost];
                    int exclude = table[i - 1][currentCapacity];
                    table[i][currentCapacity] = Math.max(include, exclude);
                } else {
                    table[i][currentCapacity] = table[i - 1][currentCapacity];
                }
            }
            trace[traceCount++] = new TraceStep(traceCount, "process order " + orders[i - 1].orderId(), "bestValueAtCapacity=" + table[i][capacity]);
        }

        Order[] selected = reconstruct(orders, costs, table, capacity);
        int totalCost = totalCost(selected, orders, costs);
        return new OrderSelectionResult(selected, table[orders.length][capacity], totalCost, table, trim(trace, traceCount));
    }

    public OrderSelectionResult selectByUrgencyWithinDistance(Order[] orders, int maxDistanceUnits) {
        int[] costs = new int[orders.length];
        int[] values = new int[orders.length];
        for (int i = 0; i < orders.length; i++) {
            costs[i] = Math.max(1, (int) Math.ceil(orders[i].estimatedDistance()));
            values[i] = orders[i].urgency();
        }
        return select(orders, costs, values, maxDistanceUnits);
    }

    private Order[] reconstruct(Order[] orders, int[] costs, int[][] table, int capacity) {
        Order[] reversed = new Order[orders.length];
        int count = 0;
        int currentCapacity = capacity;
        for (int i = orders.length; i > 0; i--) {
            if (table[i][currentCapacity] != table[i - 1][currentCapacity]) {
                reversed[count++] = orders[i - 1];
                currentCapacity -= costs[i - 1];
            }
        }
        Order[] selected = new Order[count];
        for (int i = 0; i < count; i++) {
            selected[i] = reversed[count - 1 - i];
        }
        return selected;
    }

    private int totalCost(Order[] selected, Order[] orders, int[] costs) {
        int total = 0;
        for (Order selectedOrder : selected) {
            for (int i = 0; i < orders.length; i++) {
                if (orders[i].orderId() == selectedOrder.orderId()) {
                    total += costs[i];
                    break;
                }
            }
        }
        return total;
    }

    private void validateInputs(Order[] orders, int[] costs, int[] values, int capacity) {
        if (orders == null || costs == null || values == null) {
            throw new IllegalArgumentException("orders, costs and values are required");
        }
        if (orders.length != costs.length || orders.length != values.length) {
            throw new IllegalArgumentException("orders, costs and values must have the same length");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity cannot be negative");
        }
        for (int i = 0; i < orders.length; i++) {
            if (costs[i] <= 0 || values[i] < 0) {
                throw new IllegalArgumentException("costs must be positive and values non-negative");
            }
        }
    }

    private TraceStep[] trim(TraceStep[] values, int count) {
        TraceStep[] copy = new TraceStep[count];
        for (int i = 0; i < count; i++) {
            copy[i] = values[i];
        }
        return copy;
    }
}
