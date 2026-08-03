# Architecture

SMART FOOD DELIVERY uses a simple layered architecture.

## Layers

1. Model: entity classes such as `Location`, `Road`, `Restaurant`, `Customer`, `Rider`, `Order`, `AlgorithmRun` and `AuditEvent`.
2. Data structures: custom implementations required by the project brief.
3. Algorithms: search, sort, graph, greedy, dynamic programming and brute-force demonstrations.
4. Repository: SQLite/JDBC read and write operations.
5. Service: food-delivery operations combining models, structures, algorithms and database records.
6. Console app: examiner-facing menu for running demonstrations.

## Base Package

```java
edu.ug.smartdelivery
```

## Important Rule

Assessed data structures and algorithms must be implemented by the team. Built-in Java structures may be used for JDBC, file reading, test scaffolding and non-assessed infrastructure, but not as replacements for the required structures.
