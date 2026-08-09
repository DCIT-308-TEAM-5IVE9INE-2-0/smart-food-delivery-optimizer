package edu.ug.smartdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StructureDemoServiceTest {
    private final StructureDemoService service = new StructureDemoService();

    @Test
    void dynamicArrayTraceShowsResize() {
        var trace = service.dynamicArrayResizeTrace();

        assertEquals(3, trace.size());
        assertTrue(trace.get(2).state().contains("capacity=4"));
    }

    @Test
    void linkedListIteratorReturnsEventsInOrder() {
        var events = service.linkedListIteratorDemo();

        assertEquals("Order created", events.get(0));
        assertEquals("Order delivered", events.get(2));
    }

    @Test
    void urgentDequePlacesUrgentOrderFirst() {
        var trace = service.urgentDequeDemo();

        assertTrue(trace.get(2).state().contains("Urgent-399"));
    }
}
