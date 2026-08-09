# Testing And Correctness Evidence

Project: SMART FOOD DELIVERY

Phase: 8 - Testing And Correctness Evidence

## Purpose

This document records the main correctness checks used to show that the custom
data structures, algorithms, database loading, and service workflows behave as
expected.

## Test Command

```bash
mvn test
```

The same command is used locally and in the GitHub Actions CI workflow.

## Coverage Summary

The test suite covers:

- Custom dynamic array.
- Custom linked-list-backed queue behavior through service demos.
- Custom stack.
- Custom circular queue.
- Custom priority queue and min heap.
- Custom hash table, hash set and hash map.
- Custom binary search tree.
- Custom red-black tree.
- Custom B-tree.
- Adjacency-list and adjacency-matrix graph representations.
- BFS and DFS traversal.
- Dijkstra shortest path.
- Prim and Kruskal minimum spanning tree.
- Linear and binary search.
- Selection, insertion, merge and quick sort.
- Greedy rider assignment.
- Brute-force assignment for comparison.
- Dynamic programming order selection.
- CSV reading and seed-data import.
- Route, search, sort, structure-demo and optimization services.

## Correctness Categories

Normal cases:

- Insert, remove, search, sort and traverse valid data.
- Load valid CSV seed data into SQLite.
- Run routing and assignment algorithms on connected sample delivery data.

Boundary cases:

- Empty search input.
- Empty stack and queue operations.
- Circular queue wraparound.
- Disconnected graph route.
- Small assignment and dynamic-programming datasets.

Invalid-input cases:

- Null algorithm arguments.
- Invalid indexes.
- Missing graph vertices.
- Invalid cost matrices.
- More brute-force orders than riders.
- Negative dynamic-programming capacity.
- Invalid CSV paths and database setup failures.

## Oral Defense Notes

Key explanation points:

- The assessed structures are implemented manually in `src/main/java`.
- Java collection utilities are avoided for assessed core behavior.
- Java utilities are only used for allowed infrastructure such as JDBC, files,
  tests and console wiring.
- Trace objects are returned by major algorithms to support step-by-step
  explanation during the demo.
- Greedy rider assignment is intentionally compared with brute force to show
  where greedy may fail.
- Dynamic programming stores a decision table and reconstructs the selected
  orders from that table.
- Dijkstra returns an empty path when a destination is unreachable.

## Interactive Console Requirement

The project must remain console-first and examiner-demo friendly. The current
console connects the major features, but the next integration pass must allow
users to choose real IDs, limits and options during runtime instead of relying
on default sample values.
