package edu.ug.smartdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.model.Order;
import org.junit.jupiter.api.Test;

class SearchAndSortServiceTest {
    @Test
    void findsOrderUsingBinarySearchOnSortedOrders() {
        Order[] orders = sampleOrders();

        var result = new SearchService().findOrderBinary(orders, 2);

        assertTrue(result.isPresent());
        assertEquals(5, result.get().urgency());
    }

    @Test
    void sortsOrdersByUrgencyDescending() {
        Order[] orders = sampleOrders();

        new SortService().quickSortOrders(orders, OrderComparators.byUrgencyDescending());

        assertEquals(2, orders[0].orderId());
        assertEquals(3, orders[1].orderId());
        assertEquals(1, orders[2].orderId());
    }

    private Order[] sampleOrders() {
        return new Order[] {
                new Order(1, 1, 1, 1, 2, "Lunch", 2, "2026-08-09T12:00", "2026-08-09T12:45", "PENDING", 0.7, null),
                new Order(2, 1, 1, 1, 2, "Lunch", 5, "2026-08-09T12:05", "2026-08-09T12:30", "PENDING", 0.7, null),
                new Order(3, 1, 1, 1, 2, "Lunch", 3, "2026-08-09T12:10", "2026-08-09T13:00", "PENDING", 0.7, null)
        };
    }
}
