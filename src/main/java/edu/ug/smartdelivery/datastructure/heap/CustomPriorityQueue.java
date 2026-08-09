package edu.ug.smartdelivery.datastructure.heap;

public class CustomPriorityQueue<T extends Comparable<T>> {
    private final CustomMinHeap<T> heap;

    public CustomPriorityQueue() {
        this.heap = new CustomMinHeap<>();
    }

    public void insert(T value) {
        heap.insert(value);
    }

    public T extractMin() {
        return heap.extractMin();
    }

    public T peek() {
        return heap.peek();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public String snapshot() {
        return heap.snapshot();
    }
}
