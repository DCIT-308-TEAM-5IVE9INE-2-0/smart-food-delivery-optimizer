package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.model.Order;
import java.util.Comparator;

public final class OrderComparators {
    private OrderComparators() {
    }

    public static Comparator<Order> byId() {
        return Comparator.comparingInt(Order::orderId);
    }

    public static Comparator<Order> byUrgencyDescending() {
        return Comparator.comparingInt(Order::urgency).reversed().thenComparingInt(Order::orderId);
    }

    public static Comparator<Order> byDeadline() {
        return Comparator.comparing(Order::deadline).thenComparingInt(Order::orderId);
    }

    public static Comparator<Order> byEstimatedDistance() {
        return Comparator.comparingDouble(Order::estimatedDistance).thenComparingInt(Order::orderId);
    }

    public static Comparator<Order> byTimeSubmitted() {
        return Comparator.comparing(Order::timeSubmitted).thenComparingInt(Order::orderId);
    }
}
