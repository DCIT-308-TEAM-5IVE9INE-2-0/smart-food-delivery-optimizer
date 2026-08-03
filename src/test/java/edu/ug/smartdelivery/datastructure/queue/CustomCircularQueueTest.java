package edu.ug.smartdelivery.datastructure.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CustomCircularQueueTest {
    @Test
    void wrapsRearIndexAfterDequeue() {
        CustomCircularQueue<String> queue = new CustomCircularQueue<>(3);

        queue.enqueue("Rider A");
        queue.enqueue("Rider B");
        queue.enqueue("Rider C");
        assertEquals("Rider A", queue.dequeue());
        queue.enqueue("Rider D");

        assertEquals(1, queue.frontIndex());
        assertEquals(1, queue.rearIndex());
    }

    @Test
    void fullQueueThrowsException() {
        CustomCircularQueue<Integer> queue = new CustomCircularQueue<>(1);

        queue.enqueue(1);

        assertThrows(IllegalStateException.class, () -> queue.enqueue(2));
    }
}
