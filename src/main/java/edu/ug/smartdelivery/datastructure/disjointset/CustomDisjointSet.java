package edu.ug.smartdelivery.datastructure.disjointset;

public class CustomDisjointSet {
    private int[] values;
    private int[] parent;
    private int[] rank;
    private int size;

    public CustomDisjointSet() {
        values = new int[16];
        parent = new int[16];
        rank = new int[16];
    }

    public void makeSet(int value) {
        if (indexOf(value) >= 0) {
            return;
        }
        ensureCapacity(size + 1);
        values[size] = value;
        parent[size] = size;
        rank[size] = 0;
        size++;
    }

    public int find(int value) {
        int index = indexOf(value);
        if (index < 0) {
            throw new IllegalArgumentException("value not found: " + value);
        }
        return values[findIndex(index)];
    }

    public boolean union(int first, int second) {
        int firstRoot = findIndex(indexOrThrow(first));
        int secondRoot = findIndex(indexOrThrow(second));
        if (firstRoot == secondRoot) {
            return false;
        }
        if (rank[firstRoot] < rank[secondRoot]) {
            parent[firstRoot] = secondRoot;
        } else if (rank[firstRoot] > rank[secondRoot]) {
            parent[secondRoot] = firstRoot;
        } else {
            parent[secondRoot] = firstRoot;
            rank[firstRoot]++;
        }
        return true;
    }

    public boolean connected(int first, int second) {
        return find(first) == find(second);
    }

    public int size() {
        return size;
    }

    private int findIndex(int index) {
        if (parent[index] != index) {
            parent[index] = findIndex(parent[index]);
        }
        return parent[index];
    }

    private int indexOrThrow(int value) {
        int index = indexOf(value);
        if (index < 0) {
            throw new IllegalArgumentException("value not found: " + value);
        }
        return index;
    }

    private int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (values[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCapacity(int neededCapacity) {
        if (neededCapacity <= values.length) {
            return;
        }
        int newCapacity = values.length * 2;
        int[] resizedValues = new int[newCapacity];
        int[] resizedParent = new int[newCapacity];
        int[] resizedRank = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            resizedValues[i] = values[i];
            resizedParent[i] = parent[i];
            resizedRank[i] = rank[i];
        }
        values = resizedValues;
        parent = resizedParent;
        rank = resizedRank;
    }
}
