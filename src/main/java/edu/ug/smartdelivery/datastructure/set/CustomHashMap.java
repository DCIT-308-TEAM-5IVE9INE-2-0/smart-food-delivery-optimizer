package edu.ug.smartdelivery.datastructure.set;

import edu.ug.smartdelivery.datastructure.hash.CustomHashTable;

public class CustomHashMap<K, V> {
    private final CustomHashTable<K, V> table;

    public CustomHashMap() {
        table = new CustomHashTable<>();
    }

    public void put(K key, V value) {
        table.put(key, value);
    }

    public V get(K key) {
        return table.get(key);
    }

    public V getOrDefault(K key, V defaultValue) {
        return table.getOrDefault(key, defaultValue);
    }

    public boolean containsKey(K key) {
        return table.containsKey(key);
    }

    public V remove(K key) {
        return table.remove(key);
    }

    public int size() {
        return table.size();
    }
}
