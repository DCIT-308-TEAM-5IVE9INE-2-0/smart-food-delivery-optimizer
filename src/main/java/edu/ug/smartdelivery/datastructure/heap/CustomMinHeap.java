package edu.ug.smartdelivery.datastructure.heap;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class CustomMinHeap<T extends Comparable<T>> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[] values;
    private int size;

    public CustomMinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public CustomMinHeap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        values = new Object[capacity];
    }

    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
        ensureCapacity(size + 1);
        values[size] = value;
        siftUp(size);
        size++;
    }

    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }
        T min = elementAt(0);
        values[0] = values[size - 1];
        values[size - 1] = null;
        size--;
        if (!isEmpty()) {
            siftDown(0);
        }
        return min;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }
        return elementAt(0);
    }

    public void heapify(T[] input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        values = new Object[Math.max(DEFAULT_CAPACITY, input.length)];
        size = input.length;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) {
                throw new IllegalArgumentException("heap values cannot be null");
            }
            values[i] = input[i];
        }
        for (int i = parent(size - 1); i >= 0; i--) {
            siftDown(i);
        }
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
            joiner.add(String.valueOf(values[i]));
        }
        return joiner.toString();
    }

    private void siftUp(int index) {
        int current = index;
        while (current > 0) {
            int parent = parent(current);
            if (elementAt(current).compareTo(elementAt(parent)) >= 0) {
                break;
            }
            swap(current, parent);
            current = parent;
        }
    }

    private void siftDown(int index) {
        int current = index;
        while (leftChild(current) < size) {
            int smallerChild = leftChild(current);
            int rightChild = rightChild(current);
            if (rightChild < size && elementAt(rightChild).compareTo(elementAt(smallerChild)) < 0) {
                smallerChild = rightChild;
            }
            if (elementAt(current).compareTo(elementAt(smallerChild)) <= 0) {
                break;
            }
            swap(current, smallerChild);
            current = smallerChild;
        }
    }

    private void ensureCapacity(int neededCapacity) {
        if (neededCapacity <= values.length) {
            return;
        }
        Object[] resized = new Object[values.length * 2];
        for (int i = 0; i < size; i++) {
            resized[i] = values[i];
        }
        values = resized;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    private void swap(int first, int second) {
        Object temp = values[first];
        values[first] = values[second];
        values[second] = temp;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) values[index];
    }
}
