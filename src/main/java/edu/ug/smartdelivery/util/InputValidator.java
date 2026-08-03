package edu.ug.smartdelivery.util;

public class InputValidator {
    public void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }
}
