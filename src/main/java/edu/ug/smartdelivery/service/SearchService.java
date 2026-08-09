package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.algorithm.search.BinarySearch;
import edu.ug.smartdelivery.algorithm.search.LinearSearch;
import edu.ug.smartdelivery.model.Order;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class SearchService {
    private final LinearSearch linearSearch = new LinearSearch();
    private final BinarySearch binarySearch = new BinarySearch();

    public Optional<Order> findOrderLinear(Order[] orders, int orderId) {
        Objects.requireNonNull(orders, "orders");
        for (Order order : orders) {
            if (order != null && order.orderId() == orderId) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    public Optional<Order> findOrderBinary(Order[] sortedOrders, int orderId) {
        Objects.requireNonNull(sortedOrders, "sortedOrders");
        Order probe = new Order(orderId, 1, 1, 1, 1, "Probe", 0, "0", "0", "PENDING", 0, null);
        int index = binarySearch.find(sortedOrders, probe, Comparator.comparingInt(Order::orderId));
        return index >= 0 ? Optional.of(sortedOrders[index]) : Optional.empty();
    }

    public <T> int linearIndex(T[] values, T target) {
        return linearSearch.find(values, target);
    }
}
