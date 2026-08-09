package edu.ug.smartdelivery.datastructure.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class CustomMinHeapTest {
    @Test
    void extractsValuesInAscendingOrder() {
        CustomMinHeap<Integer> heap = new CustomMinHeap<>(2);

        heap.insert(5);
        heap.insert(1);
        heap.insert(3);

        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void heapifyBuildsValidHeap() {
        CustomMinHeap<Integer> heap = new CustomMinHeap<>();

        heap.heapify(new Integer[] {9, 4, 7, 1});

        assertEquals(1, heap.peek());
    }

    @Test
    void extractFromEmptyHeapThrowsException() {
        CustomMinHeap<Integer> heap = new CustomMinHeap<>();

        assertThrows(NoSuchElementException.class, heap::extractMin);
    }
}
