package edu.ug.smartdelivery.algorithm.search;

import java.util.Objects;

public class LinearSearch {
    public <T> int find(T[] values, T target) {
        Objects.requireNonNull(values, "values");
        for (int i = 0; i < values.length; i++) {
            if (Objects.equals(values[i], target)) {
                return i;
            }
        }
        return -1;
    }
}
