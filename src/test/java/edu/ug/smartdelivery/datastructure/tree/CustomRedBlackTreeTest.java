package edu.ug.smartdelivery.datastructure.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomRedBlackTreeTest {
    @Test
    void insertsWithRotationsAndKeepsSearchWorking() {
        CustomRedBlackTree<Integer, String> tree = new CustomRedBlackTree<>();

        tree.insert(1, "A");
        tree.insert(2, "B");
        tree.insert(3, "C");
        tree.insert(4, "D");
        tree.insert(5, "E");

        assertEquals("D", tree.search(4));
        assertTrue(tree.height() < 5);
        assertTrue(tree.rotationCount() > 0);
        assertTrue(tree.levelOrderSnapshot().contains("(B)"));
    }
}
