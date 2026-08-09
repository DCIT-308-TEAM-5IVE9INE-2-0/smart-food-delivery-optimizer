package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.datastructure.TraceStep;
import edu.ug.smartdelivery.datastructure.array.CustomDynamicArray;
import edu.ug.smartdelivery.datastructure.iterator.CustomIterator;
import edu.ug.smartdelivery.datastructure.list.CustomLinkedList;
import edu.ug.smartdelivery.datastructure.queue.CustomCircularQueue;
import edu.ug.smartdelivery.datastructure.queue.CustomDeque;
import edu.ug.smartdelivery.datastructure.queue.CustomQueue;
import edu.ug.smartdelivery.datastructure.stack.CustomStack;
import java.util.ArrayList;
import java.util.List;

public class StructureDemoService {
    public List<TraceStep> dynamicArrayResizeTrace() {
        CustomDynamicArray<String> array = new CustomDynamicArray<>(2);
        List<TraceStep> trace = new ArrayList<>();
        array.add("Order-101");
        trace.add(new TraceStep(1, "add Order-101", array.snapshot()));
        array.add("Order-102");
        trace.add(new TraceStep(2, "add Order-102", array.snapshot()));
        array.add("Order-103");
        trace.add(new TraceStep(3, "add Order-103 causes resize", array.snapshot()));
        return trace;
    }

    public List<String> linkedListIteratorDemo() {
        CustomLinkedList<String> history = new CustomLinkedList<>();
        history.addLast("Order created");
        history.addLast("Rider assigned");
        history.addLast("Order delivered");
        List<String> events = new ArrayList<>();
        CustomIterator<String> iterator = history.iterator();
        while (iterator.hasNext()) {
            events.add(iterator.next());
        }
        return events;
    }

    public List<TraceStep> stackAuditDemo() {
        CustomStack<String> audit = new CustomStack<>();
        List<TraceStep> trace = new ArrayList<>();
        audit.push("CREATE_ORDER");
        trace.add(new TraceStep(1, "push CREATE_ORDER", audit.snapshot()));
        audit.push("ASSIGN_RIDER");
        trace.add(new TraceStep(2, "push ASSIGN_RIDER", audit.snapshot()));
        audit.pop();
        trace.add(new TraceStep(3, "pop undo latest action", audit.snapshot()));
        return trace;
    }

    public List<TraceStep> fifoOrderDemo() {
        CustomQueue<String> queue = new CustomQueue<>(2);
        List<TraceStep> trace = new ArrayList<>();
        queue.enqueue("Order-201");
        trace.add(new TraceStep(1, "enqueue Order-201", queue.snapshot()));
        queue.enqueue("Order-202");
        trace.add(new TraceStep(2, "enqueue Order-202", queue.snapshot()));
        queue.dequeue();
        trace.add(new TraceStep(3, "dequeue first order", queue.snapshot()));
        return trace;
    }

    public List<TraceStep> circularRiderDemo() {
        CustomCircularQueue<String> riders = new CustomCircularQueue<>(3);
        List<TraceStep> trace = new ArrayList<>();
        riders.enqueue("Rider-A");
        riders.enqueue("Rider-B");
        riders.enqueue("Rider-C");
        trace.add(new TraceStep(1, "enqueue riders", riders.snapshot()));
        riders.dequeue();
        trace.add(new TraceStep(2, "Rider-A assigned and leaves front", riders.snapshot()));
        riders.enqueue("Rider-A");
        trace.add(new TraceStep(3, "Rider-A returns at rear", riders.snapshot()));
        return trace;
    }

    public List<TraceStep> urgentDequeDemo() {
        CustomDeque<String> deque = new CustomDeque<>(4);
        List<TraceStep> trace = new ArrayList<>();
        deque.addRear("Normal-301");
        trace.add(new TraceStep(1, "add normal order at rear", deque.snapshot()));
        deque.addRear("Normal-302");
        trace.add(new TraceStep(2, "add normal order at rear", deque.snapshot()));
        deque.addFront("Urgent-399");
        trace.add(new TraceStep(3, "add urgent order at front", deque.snapshot()));
        return trace;
    }
}
