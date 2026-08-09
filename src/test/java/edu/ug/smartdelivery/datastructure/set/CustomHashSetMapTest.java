package edu.ug.smartdelivery.datastructure.set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomHashSetMapTest {
    @Test
    void hashSetTracksMembership() {
        CustomHashSet<Integer> set = new CustomHashSet<>();

        set.add(1);
        set.add(1);
        set.add(2);

        assertTrue(set.contains(1));
        assertEquals(2, set.size());
    }

    @Test
    void hashMapStoresKeyValuePairs() {
        CustomHashMap<Integer, String> map = new CustomHashMap<>();

        map.put(7, "Rider Seven");

        assertEquals("Rider Seven", map.get(7));
    }
}
