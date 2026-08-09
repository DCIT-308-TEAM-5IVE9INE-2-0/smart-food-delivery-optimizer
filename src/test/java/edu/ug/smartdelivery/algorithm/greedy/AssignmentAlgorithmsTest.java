package edu.ug.smartdelivery.algorithm.greedy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.model.Order;
import edu.ug.smartdelivery.model.Rider;
import org.junit.jupiter.api.Test;

class AssignmentAlgorithmsTest {
    @Test
    void greedyAssignsNearestAvailableRiderPerOrder() {
        AssignmentResult result = new GreedyRiderAssignment().assign(orders(), riders(), new double[][] {
                {3, 1},
                {2, 5}
        });

        assertEquals(2, result.assignments().length);
        assertEquals(2, result.assignments()[0].riderId());
        assertEquals(1, result.assignments()[1].riderId());
        assertEquals(3.0, result.totalCost());
    }

    @Test
    void bruteForceFindsBetterSolutionForGreedyCounterexample() {
        Order[] orders = orders();
        Rider[] riders = riders();
        double[][] costs = {
                {1, 2},
                {2, 100}
        };

        AssignmentResult greedy = new GreedyRiderAssignment().assign(orders, riders, costs);
        AssignmentResult optimal = new BruteForceAssignment().assign(orders, riders, costs);

        assertEquals(101.0, greedy.totalCost());
        assertEquals(4.0, optimal.totalCost());
        assertTrue(optimal.trace().length > 0);
    }

    private Order[] orders() {
        return new Order[] {
                new Order(1, 1, 1, 1, 2, "Lunch", 2, "2026-08-09T12:00", "2026-08-09T12:40", "PENDING", 1.0, null),
                new Order(2, 1, 1, 1, 2, "Lunch", 5, "2026-08-09T12:05", "2026-08-09T12:35", "PENDING", 2.0, null)
        };
    }

    private Rider[] riders() {
        return new Rider[] {
                new Rider(1, "Rider A", 1, "Motorbike", 2, "AVAILABLE", 1),
                new Rider(2, "Rider B", 2, "Motorbike", 2, "AVAILABLE", 2)
        };
    }
}
