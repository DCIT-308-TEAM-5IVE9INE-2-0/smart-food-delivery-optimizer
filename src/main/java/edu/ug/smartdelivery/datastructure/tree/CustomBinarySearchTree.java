package edu.ug.smartdelivery.datastructure.tree;

import edu.ug.smartdelivery.datastructure.list.CustomLinkedList;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class CustomBinarySearchTree<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private int size;

    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        root = insert(root, key, value);
    }

    public V search(K key) {
        Node<K, V> node = findNode(key);
        if (node == null) {
            throw new NoSuchElementException("key not found: " + key);
        }
        return node.value;
    }

    public boolean contains(K key) {
        return findNode(key) != null;
    }

    public String searchPath(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        StringJoiner joiner = new StringJoiner(" -> ");
        Node<K, V> current = root;
        while (current != null) {
            joiner.add(String.valueOf(current.key));
            int comparison = key.compareTo(current.key);
            if (comparison == 0) {
                break;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return joiner.toString();
    }

    public CustomLinkedList<K> inorderKeys() {
        CustomLinkedList<K> keys = new CustomLinkedList<>();
        inorder(root, keys);
        return keys;
    }

    public int height() {
        return height(root);
    }

    public int size() {
        return size;
    }

    private Node<K, V> insert(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = insert(node.left, key, value);
        } else if (comparison > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    private Node<K, V> findNode(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Node<K, V> current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison == 0) {
                return current;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return null;
    }

    private void inorder(Node<K, V> node, CustomLinkedList<K> keys) {
        if (node == null) {
            return;
        }
        inorder(node.left, keys);
        keys.addLast(node.key);
        inorder(node.right, keys);
    }

    private int height(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private static final class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
