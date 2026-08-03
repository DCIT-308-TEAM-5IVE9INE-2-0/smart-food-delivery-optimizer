package edu.ug.smartdelivery.datastructure.queue;

import java.util.NoSuchElementException;

public class CustomCircularQueue<T> {
    private final Object[] values;
    private int front;
    private int rear;
    private int size;

    public CustomCircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        values = new Object[capacity];
    }

    public void enqueue(T value) {
        if (size == values.length) {
            throw new IllegalStateException("circular queue is full");
        }
        values[rear] = value;
        rear = (rear + 1) % values.length;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("circular queue is empty");
        }
        T value = elementAt(front);
        values[front] = null;
        front = (front + 1) % values.length;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int frontIndex() {
        return front;
    }

    public int rearIndex() {
        return rear;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) values[index];
    }
}
