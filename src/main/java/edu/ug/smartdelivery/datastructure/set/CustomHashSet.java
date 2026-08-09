package edu.ug.smartdelivery.datastructure.set;

import edu.ug.smartdelivery.datastructure.hash.CustomHashTable;

public class CustomHashSet<T> {
    private static final Object PRESENT = new Object();
    private final CustomHashTable<T, Object> table;

    public CustomHashSet() {
        table = new CustomHashTable<>();
    }

    public void add(T value) {
        table.put(value, PRESENT);
    }

    public boolean contains(T value) {
        return table.containsKey(value);
    }

    public void remove(T value) {
        table.remove(value);
    }

    public int size() {
        return table.size();
    }
}
