package edu.ug.smartdelivery.datastructure.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.PrioritizedOrder;
import org.junit.jupiter.api.Test;

class CustomPriorityQueueTest {
    @Test
    void dispatchesHighestPriorityOrderFirst() {
        CustomPriorityQueue<PrioritizedOrder> queue = new CustomPriorityQueue<>();

        queue.insert(priorityOrder(1, 2));
        queue.insert(priorityOrder(2, 5));
        queue.insert(priorityOrder(3, 3));

        assertEquals(2, queue.extractMin().order().orderId());
    }

    private PrioritizedOrder priorityOrder(int orderId, int priorityScore) {
        Order order = new Order(orderId, 1, 1, 1, 1, "Lunch", priorityScore, "2026-08-09T12:00", "2026-08-09T12:45", "PENDING", 1, null);
        return new PrioritizedOrder(order, priorityScore);
    }
}
