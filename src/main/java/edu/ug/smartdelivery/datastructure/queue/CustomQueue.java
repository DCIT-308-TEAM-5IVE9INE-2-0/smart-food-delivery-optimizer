package edu.ug.smartdelivery.datastructure.queue;

import java.util.NoSuchElementException;

public class CustomQueue<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] values;
    private int front;
    private int rear;
    private int size;

    public CustomQueue() {
        this(DEFAULT_CAPACITY);
    }

    public CustomQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        values = new Object[capacity];
    }

    public void enqueue(T value) {
        ensureCapacity(size + 1);
        values[rear] = value;
        rear = (rear + 1) % values.length;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        T value = elementAt(front);
        values[front] = null;
        front = (front + 1) % values.length;
        size--;
        return value;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        return elementAt(front);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity(int neededCapacity) {
        if (neededCapacity <= values.length) {
            return;
        }
        Object[] resized = new Object[values.length * 2];
        for (int i = 0; i < size; i++) {
            resized[i] = values[(front + i) % values.length];
        }
        values = resized;
        front = 0;
        rear = size;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) values[index];
    }
}
