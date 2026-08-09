package edu.ug.smartdelivery.datastructure.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ug.smartdelivery.datastructure.iterator.CustomIterator;
import org.junit.jupiter.api.Test;

class CustomBinarySearchTreeTest {
    @Test
    void searchesAndReturnsInorderKeys() {
        CustomBinarySearchTree<Integer, String> tree = new CustomBinarySearchTree<>();

        tree.insert(40, "Order 40");
        tree.insert(20, "Order 20");
        tree.insert(60, "Order 60");

        assertEquals("Order 20", tree.search(20));
        assertEquals("40 -> 20", tree.searchPath(20));

        CustomIterator<Integer> iterator = tree.inorderKeys().iterator();
        assertEquals(20, iterator.next());
        assertEquals(40, iterator.next());
        assertEquals(60, iterator.next());
    }

    @Test
    void heightReflectsTreeShape() {
        CustomBinarySearchTree<Integer, String> tree = new CustomBinarySearchTree<>();

        tree.insert(1, "A");
        tree.insert(2, "B");
        tree.insert(3, "C");

        assertTrue(tree.height() >= 3);
    }
}
