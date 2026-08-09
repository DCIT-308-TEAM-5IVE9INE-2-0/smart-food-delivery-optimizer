package edu.ug.smartdelivery.datastructure.queue;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class CustomDeque<T> {
    private final Object[] values;
    private int front;
    private int size;

    public CustomDeque(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        values = new Object[capacity];
    }

    public void addFront(T value) {
        ensureNotFull();
        front = (front - 1 + values.length) % values.length;
        values[front] = value;
        size++;
    }

    public void addRear(T value) {
        ensureNotFull();
        values[(front + size) % values.length] = value;
        size++;
    }

    public T removeFront() {
        ensureNotEmpty();
        T value = elementAt(front);
        values[front] = null;
        front = (front + 1) % values.length;
        size--;
        return value;
    }

    public T removeRear() {
        ensureNotEmpty();
        int rearIndex = (front + size - 1) % values.length;
        T value = elementAt(rearIndex);
        values[rearIndex] = null;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public String snapshot() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            joiner.add(String.valueOf(values[(front + i) % values.length]));
        }
        return "front=" + front + ", size=" + size + ", values=" + joiner;
    }

    private void ensureNotFull() {
        if (size == values.length) {
            throw new IllegalStateException("deque is full");
        }
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) values[index];
    }
}
