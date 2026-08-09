package edu.ug.smartdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Rider;
import org.junit.jupiter.api.Test;

class OptimizationServiceTest {
    @Test
    void exposesGreedyDpAndCounterexampleOperations() {
        OptimizationService service = new OptimizationService();

        assertEquals(1, service.assignRidersGreedily(orders(), riders(), new double[][] {{1}}).assignments().length);
        assertEquals(3, service.selectOrdersWithinDistance(orders(), 1).totalValue());
        assertTrue(service.greedyFailureCounterexample().contains("greedy"));
    }

    private Order[] orders() {
        return new Order[] {
                new Order(1, 1, 1, 1, 2, "Lunch", 3, "2026-08-09T12:00", "2026-08-09T12:40", "PENDING", 1.0, null)
        };
    }

    private Rider[] riders() {
        return new Rider[] {
                new Rider(1, "Rider A", 1, "Motorbike", 2, "AVAILABLE", 1)
        };
    }
}
