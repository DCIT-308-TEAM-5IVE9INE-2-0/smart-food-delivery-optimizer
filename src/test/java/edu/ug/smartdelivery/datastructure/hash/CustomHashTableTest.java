package edu.ug.smartdelivery.datastructure.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomHashTableTest {
    @Test
    void storesUpdatesAndRemovesValues() {
        CustomHashTable<Integer, String> table = new CustomHashTable<>(4);

        table.put(101, "Order 101");
        table.put(101, "Updated Order 101");
        table.put(102, "Order 102");

        assertEquals("Updated Order 101", table.get(101));
        assertEquals("Order 102", table.remove(102));
        assertEquals(1, table.size());
    }

    @Test
    void recordsCollisionsForSameBucket() {
        CustomHashTable<Integer, String> table = new CustomHashTable<>(4);

        table.put(1, "A");
        table.put(5, "B");

        assertTrue(table.collisionCount() >= 1);
        assertEquals(2, table.bucketSize(1));
    }
}
