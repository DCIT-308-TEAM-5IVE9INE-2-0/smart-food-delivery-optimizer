package edu.ug.smartdelivery.datastructure.stack;

import edu.ug.smartdelivery.datastructure.array.CustomDynamicArray;
import java.util.NoSuchElementException;

public class CustomStack<T> {
    private final CustomDynamicArray<T> values = new CustomDynamicArray<>();

    public void push(T value) {
        values.add(value);
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }
        return values.remove(values.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }
        return values.get(values.size() - 1);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }

    public String snapshot() {
        return values.snapshot();
    }
}
