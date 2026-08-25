package edu.ug.smartdelivery.datastructure.tree;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class CustomRedBlackTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node<K, V> root;
    private int size;
    private int rotationCount;

    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        root = insert(root, key, value);

        if (root != null) {
            root.color = BLACK;
        }
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

    public int height() {
        return height(root);
    }

    public int size() {
        return size;
    }

    public int rotationCount() {
        return rotationCount;
    }

    public String levelOrderSnapshot() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        appendLevel(root, joiner, 1, height());

        return joiner.toString();
    }

    private Node<K, V> insert(Node<K, V> node, K key, V value) {

        // Create a new red node
        if (node == null) {
            size++;
            return new Node<>(key, value, RED);
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            node.left = insert(node.left, key, value);

        } else if (comparison > 0) {
            node.right = insert(node.right, key, value);

        } else {
            // Key already exists: update value
            node.value = value;
        }

        // Fix right-leaning red link
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }

        // Fix two consecutive red links
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        // Split a 4-node
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {

        Node<K, V> child = node.right;

        node.right = child.left;
        child.left = node;

        child.color = node.color;
        node.color = RED;

        rotationCount++;

        return child;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {

        Node<K, V> child = node.left;

        node.left = child.right;
        child.right = node;

        child.color = node.color;
        node.color = RED;

        rotationCount++;

        return child;
    }

    private void flipColors(Node<K, V> node) {

        node.color = !node.color;

        if (node.left != null) {
            node.left.color = !node.left.color;
        }

        if (node.right != null) {
            node.right.color = !node.right.color;
        }
    }

    private boolean isRed(Node<K, V> node) {
        return node != null && node.color == RED;
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

            if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    private int height(Node<K, V> node) {

        if (node == null) {
            return 0;
        }

        return 1 + Math.max(
                height(node.left),
                height(node.right)
        );
    }

    private void appendLevel(
            Node<K, V> node,
            StringJoiner joiner,
            int level,
            int maxLevel) {

        if (node == null || level > maxLevel) {
            return;
        }

        if (level == 1) {

            joiner.add(
                    node.key +
                    (node.color == RED ? "(R)" : "(B)")
            );

            return;
        }

        appendLevel(
                node.left,
                joiner,
                level - 1,
                maxLevel
        );

        appendLevel(
                node.right,
                joiner,
                level - 1,
                maxLevel
        );
    }

    /*
     * Validates the Red-Black Tree.
     *
     * Properties checked:
     * 1. Root must be black.
     * 2. No red node can have a red child.
     * 3. Every path from a node to a null leaf
     *    must contain the same number of black nodes.
     * 4. No right-leaning red links are allowed
     *    because this implementation is an LLRB tree.
     */
    public boolean validate() {

        if (root == null) {
            return true;
        }

        // Root must be black
        if (isRed(root)) {
            return false;
        }

        return validateNode(root) >= 0;
    }

    /*
     * Returns black height if valid.
     * Returns -1 if a Red-Black Tree property is violated.
     */
    private int validateNode(Node<K, V> node) {

        if (node == null) {
            return 1;
        }

        // Red node cannot have a red child
        if (isRed(node)) {

            if (isRed(node.left) || isRed(node.right)) {
                return -1;
            }
        }

        // LLRB trees cannot have right-leaning red links
        if (isRed(node.right)) {
            return -1;
        }

        int leftBlackHeight = validateNode(node.left);

        if (leftBlackHeight == -1) {
            return -1;
        }

        int rightBlackHeight = validateNode(node.right);

        if (rightBlackHeight == -1) {
            return -1;
        }

        // Both paths must have the same black height
        if (leftBlackHeight != rightBlackHeight) {
            return -1;
        }

        // Add this node if it is black
        if (node.color == BLACK) {
            return leftBlackHeight + 1;
        }

        return leftBlackHeight;
    }

    private static final class Node<K, V> {

        private final K key;
        private V value;
        private boolean color;

        private Node<K, V> left;
        private Node<K, V> right;

        private Node(K key, V value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }
}