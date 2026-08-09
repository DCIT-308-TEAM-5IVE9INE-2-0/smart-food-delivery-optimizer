package edu.ug.smartdelivery.datastructure.tree;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class CustomBTree<K extends Comparable<K>, V> {
    private static final int DEFAULT_MIN_DEGREE = 2;
    private final int minDegree;
    private Node<K, V> root;
    private int size;
    private int splitCount;

    public CustomBTree() {
        this(DEFAULT_MIN_DEGREE);
    }

    public CustomBTree(int minDegree) {
        if (minDegree < 2) {
            throw new IllegalArgumentException("minDegree must be at least 2");
        }
        this.minDegree = minDegree;
        this.root = new Node<>(minDegree, true);
    }

    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (root.keyCount == maxKeys()) {
            Node<K, V> newRoot = new Node<>(minDegree, false);
            newRoot.children[0] = root;
            splitChild(newRoot, 0);
            root = newRoot;
        }
        if (insertNonFull(root, key, value)) {
            size++;
        }
    }

    public V search(K key) {
        SearchResult<K, V> result = search(root, key);
        if (result == null) {
            throw new NoSuchElementException("key not found: " + key);
        }
        return result.node.valueAt(result.index);
    }

    public boolean contains(K key) {
        return search(root, key) != null;
    }

    public String searchTrace(K key) {
        StringJoiner joiner = new StringJoiner(" -> ");
        Node<K, V> current = root;
        while (current != null) {
            joiner.add(current.keysSnapshot());
            int i = 0;
            while (i < current.keyCount && key.compareTo(current.keyAt(i)) > 0) {
                i++;
            }
            if (i < current.keyCount && key.compareTo(current.keyAt(i)) == 0) {
                break;
            }
            current = current.leaf ? null : current.childAt(i);
        }
        return joiner.toString();
    }

    public int size() {
        return size;
    }

    public int splitCount() {
        return splitCount;
    }

    public int rootKeyCount() {
        return root.keyCount;
    }

    private boolean insertNonFull(Node<K, V> node, K key, V value) {
        int i = node.keyCount - 1;
        if (node.leaf) {
            while (i >= 0 && key.compareTo(node.keyAt(i)) < 0) {
                node.keys[i + 1] = node.keys[i];
                node.values[i + 1] = node.values[i];
                i--;
            }
            if (i >= 0 && key.compareTo(node.keyAt(i)) == 0) {
                node.values[i] = value;
                return false;
            }
            node.keys[i + 1] = key;
            node.values[i + 1] = value;
            node.keyCount++;
            return true;
        }
        while (i >= 0 && key.compareTo(node.keyAt(i)) < 0) {
            i--;
        }
        if (i >= 0 && key.compareTo(node.keyAt(i)) == 0) {
            node.values[i] = value;
            return false;
        }
        i++;
        if (node.childAt(i).keyCount == maxKeys()) {
            splitChild(node, i);
            int comparison = key.compareTo(node.keyAt(i));
            if (comparison == 0) {
                node.values[i] = value;
                return false;
            }
            if (comparison > 0) {
                i++;
            }
        }
        return insertNonFull(node.childAt(i), key, value);
    }

    private void splitChild(Node<K, V> parent, int childIndex) {
        Node<K, V> fullChild = parent.childAt(childIndex);
        Node<K, V> sibling = new Node<>(minDegree, fullChild.leaf);
        sibling.keyCount = minDegree - 1;

        for (int j = 0; j < minDegree - 1; j++) {
            sibling.keys[j] = fullChild.keys[j + minDegree];
            sibling.values[j] = fullChild.values[j + minDegree];
            fullChild.keys[j + minDegree] = null;
            fullChild.values[j + minDegree] = null;
        }
        if (!fullChild.leaf) {
            for (int j = 0; j < minDegree; j++) {
                sibling.children[j] = fullChild.children[j + minDegree];
                fullChild.children[j + minDegree] = null;
            }
        }
        fullChild.keyCount = minDegree - 1;

        for (int j = parent.keyCount; j >= childIndex + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[childIndex + 1] = sibling;

        for (int j = parent.keyCount - 1; j >= childIndex; j--) {
            parent.keys[j + 1] = parent.keys[j];
            parent.values[j + 1] = parent.values[j];
        }
        parent.keys[childIndex] = fullChild.keys[minDegree - 1];
        parent.values[childIndex] = fullChild.values[minDegree - 1];
        fullChild.keys[minDegree - 1] = null;
        fullChild.values[minDegree - 1] = null;
        parent.keyCount++;
        splitCount++;
    }

    private SearchResult<K, V> search(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int i = 0;
        while (i < node.keyCount && key.compareTo(node.keyAt(i)) > 0) {
            i++;
        }
        if (i < node.keyCount && key.compareTo(node.keyAt(i)) == 0) {
            return new SearchResult<>(node, i);
        }
        return node.leaf ? null : search(node.childAt(i), key);
    }

    private int maxKeys() {
        return minDegree * 2 - 1;
    }

    private static final class SearchResult<K extends Comparable<K>, V> {
        private final Node<K, V> node;
        private final int index;

        private SearchResult(Node<K, V> node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    private static final class Node<K extends Comparable<K>, V> {
        private final Object[] keys;
        private final Object[] values;
        private final Node<K, V>[] children;
        private final boolean leaf;
        private int keyCount;

        @SuppressWarnings("unchecked")
        private Node(int minDegree, boolean leaf) {
            this.keys = new Object[minDegree * 2 - 1];
            this.values = new Object[minDegree * 2 - 1];
            this.children = (Node<K, V>[]) new Node[minDegree * 2];
            this.leaf = leaf;
        }

        @SuppressWarnings("unchecked")
        private K keyAt(int index) {
            return (K) keys[index];
        }

        @SuppressWarnings("unchecked")
        private V valueAt(int index) {
            return (V) values[index];
        }

        private Node<K, V> childAt(int index) {
            return children[index];
        }

        private String keysSnapshot() {
            StringJoiner joiner = new StringJoiner(",", "[", "]");
            for (int i = 0; i < keyCount; i++) {
                joiner.add(String.valueOf(keys[i]));
            }
            return joiner.toString();
        }
    }
}
