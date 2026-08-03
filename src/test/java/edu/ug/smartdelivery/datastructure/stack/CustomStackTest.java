package edu.ug.smartdelivery.datastructure.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class CustomStackTest {
    @Test
    void popsMostRecentValueFirst() {
        CustomStack<String> stack = new CustomStack<>();

        stack.push("create order");
        stack.push("assign rider");

        assertEquals("assign rider", stack.pop());
        assertEquals("create order", stack.peek());
    }

    @Test
    void emptyPopThrowsException() {
        CustomStack<String> stack = new CustomStack<>();

        assertThrows(NoSuchElementException.class, stack::pop);
    }
}
