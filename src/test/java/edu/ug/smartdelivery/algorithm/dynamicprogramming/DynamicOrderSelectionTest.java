package edu.ug.smartdelivery.algorithm.dynamicprogramming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.model.Order;
import org.junit.jupiter.api.Test;

class DynamicOrderSelectionTest {
    @Test
    void selectsBestOrdersWithinCapacityAndReconstructsSelection() {
        Order[] orders = orders();
        int[] costs = {2, 3, 4};
        int[] values = {4, 5, 10};

        OrderSelectionResult result = new DynamicOrderSelection().select(orders, costs, values, 5);

        assertEquals(10, result.totalValue());
        assertEquals(4, result.totalCost());
        assertEquals(1, result.selectedOrders().length);
        assertEquals(3, result.selectedOrders()[0].orderId());
        assertEquals(10, result.table()[3][5]);
        assertTrue(result.trace().length > 0);
    }

    @Test
    void selectsByUrgencyWithinDistance() {
        OrderSelectionResult result = new DynamicOrderSelection().selectByUrgencyWithinDistance(orders(), 5);

        assertEquals(9, result.totalValue());
        assertEquals(5, result.totalCost());
    }

    private Order[] orders() {
        return new Order[] {
                new Order(1, 1, 1, 1, 2, "Lunch", 4, "2026-08-09T12:00", "2026-08-09T12:40", "PENDING", 2.0, null),
                new Order(2, 1, 1, 1, 2, "Lunch", 5, "2026-08-09T12:05", "2026-08-09T12:35", "PENDING", 3.0, null),
                new Order(3, 1, 1, 1, 2, "Lunch", 3, "2026-08-09T12:10", "2026-08-09T13:00", "PENDING", 4.0, null)
        };
    }
}
