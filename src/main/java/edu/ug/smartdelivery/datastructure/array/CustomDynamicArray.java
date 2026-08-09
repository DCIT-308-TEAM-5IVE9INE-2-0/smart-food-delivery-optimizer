package edu.ug.smartdelivery.datastructure.array;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class CustomDynamicArray<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public CustomDynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public CustomDynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("initialCapacity must be positive");
        }
        this.elements = new Object[initialCapacity];
    }

    public void add(T value) {
        ensureCapacity(size + 1);
        elements[size++] = value;
    }

    public void insert(int index, T value) {
        checkInsertIndex(index);
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = value;
        size++;
    }

    public T get(int index) {
        checkElementIndex(index);
        return elementAt(index);
    }

    public void set(int index, T value) {
        checkElementIndex(index);
        elements[index] = value;
    }

    public T remove(int index) {
        checkElementIndex(index);
        T removed = elementAt(index);
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removed;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return elements.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object[] toArray() {
        Object[] copy = new Object[size];
        for (int i = 0; i < size; i++) {
            copy[i] = elements[i];
        }
        return copy;
    }

    public String snapshot() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            joiner.add(String.valueOf(elements[i]));
        }
        return "size=" + size + ", capacity=" + elements.length + ", values=" + joiner;
    }

    private void ensureCapacity(int neededCapacity) {
        if (neededCapacity <= elements.length) {
            return;
        }
        int newCapacity = elements.length * 2;
        while (newCapacity < neededCapacity) {
            newCapacity *= 2;
        }
        Object[] resized = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            resized[i] = elements[i];
        }
        elements = resized;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("index out of bounds: " + index);
        }
    }

    private void checkInsertIndex(int index) {
        if (index < 0 || index > size) {
            throw new NoSuchElementException("index out of bounds: " + index);
        }
    }
}
