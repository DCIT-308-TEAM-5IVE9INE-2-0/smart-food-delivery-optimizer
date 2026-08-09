package edu.ug.smartdelivery.datastructure.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomBTreeTest {
    @Test
    void splitsNodesAndSearchesKeys() {
        CustomBTree<Integer, String> tree = new CustomBTree<>(2);

        tree.insert(10, "Order 10");
        tree.insert(20, "Order 20");
        tree.insert(5, "Order 5");
        tree.insert(6, "Order 6");
        tree.insert(12, "Order 12");

        assertEquals("Order 6", tree.search(6));
        assertEquals(5, tree.size());
        assertTrue(tree.splitCount() > 0);
        assertTrue(tree.searchTrace(6).contains("["));
    }
}
