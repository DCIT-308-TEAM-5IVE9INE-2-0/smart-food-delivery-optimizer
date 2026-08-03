package edu.ug.smartdelivery.datastructure.list;

import edu.ug.smartdelivery.datastructure.iterator.CustomIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void insertAfter(T existingValue, T newValue) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.value, existingValue)) {
                Node<T> node = new Node<>(newValue);
                node.next = current.next;
                current.next = node;
                if (tail == current) {
                    tail = node;
                }
                size++;
                return;
            }
            current = current.next;
        }
        throw new NoSuchElementException("value not found: " + existingValue);
    }

    public boolean remove(T value) {
        Node<T> previous = null;
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.value, value)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                if (tail == current) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public CustomIterator<T> iterator() {
        return new CustomIterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException("no next value");
                }
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }
}
