package edu.ug.smartdelivery.datastructure.hash;

import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomHashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;
    private Entry<K, V>[] buckets;
    private int size;
    private int collisions;

    public CustomHashTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public CustomHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        buckets = (Entry<K, V>[]) new Entry[capacity];
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if ((size + 1.0) / buckets.length > MAX_LOAD_FACTOR) {
            resize();
        }
        putWithoutResize(key, value, true);
    }

    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        if (entry == null) {
            throw new NoSuchElementException("key not found: " + key);
        }
        return entry.value;
    }

    public V getOrDefault(K key, V defaultValue) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? defaultValue : entry.value;
    }

    public boolean containsKey(K key) {
        return findEntry(key) != null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        int index = indexFor(key, buckets.length);
        Entry<K, V> previous = null;
        Entry<K, V> current = buckets[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        throw new NoSuchElementException("key not found: " + key);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return buckets.length;
    }

    public int collisionCount() {
        return collisions;
    }

    public double loadFactor() {
        return size / (double) buckets.length;
    }

    public int bucketSize(int index) {
        if (index < 0 || index >= buckets.length) {
            throw new IllegalArgumentException("bucket index out of bounds");
        }
        int count = 0;
        Entry<K, V> current = buckets[index];
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    private void putWithoutResize(K key, V value, boolean countCollision) {
        int index = indexFor(key, buckets.length);
        Entry<K, V> current = buckets[index];
        if (current == null) {
            buckets[index] = new Entry<>(key, value);
            size++;
            return;
        }
        if (countCollision) {
            collisions++;
        }
        while (true) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            if (current.next == null) {
                current.next = new Entry<>(key, value);
                size++;
                return;
            }
            current = current.next;
        }
    }

    private Entry<K, V> findEntry(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Entry<K, V> current = buckets[indexFor(key, buckets.length)];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        buckets = (Entry<K, V>[]) new Entry[oldBuckets.length * 2];
        int oldSize = size;
        size = 0;
        for (Entry<K, V> bucket : oldBuckets) {
            Entry<K, V> current = bucket;
            while (current != null) {
                putWithoutResize(current.key, current.value, false);
                current = current.next;
            }
        }
        size = oldSize;
    }

    private int indexFor(K key, int capacity) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    private static final class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
