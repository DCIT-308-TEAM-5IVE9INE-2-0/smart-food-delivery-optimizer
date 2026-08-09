package edu.ug.smartdelivery.model;

public record PrioritizedOrder(Order order, int priorityScore) implements Comparable<PrioritizedOrder> {
    public PrioritizedOrder {
        if (order == null) {
            throw new IllegalArgumentException("order cannot be null");
        }
    }

    @Override
    public int compareTo(PrioritizedOrder other) {
        int priorityComparison = Integer.compare(other.priorityScore, priorityScore);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return order.deadline().compareTo(other.order.deadline());
    }
}
