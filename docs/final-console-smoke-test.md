# Final Console Smoke Test

Project: SMART FOOD DELIVERY

Purpose: prove the console app can be demonstrated smoothly and that important actions write back to the SQLite database.

## Pre-Test

From the project root:

```bash
Imvn clean test
mvn exec:java
```

Expected test result:

```text
BUILD SUCCESS
```

## Smoke-Test Path

Run these menu actions in order.

| Step | Menu Action                                           | Expected Evidence                                                                             |
| ---: | ----------------------------------------------------- | --------------------------------------------------------------------------------------------- |
|    1 | `Data Setup -> Initialize Database`                   | Database schema is created in `database/smart_delivery.db`.                                   |
|    2 | `Data Setup -> Import Default CSV Seed Data`          | CSV rows are imported without foreign-key errors.                                             |
|    3 | `Data Setup -> View Database Summary`                 | Counts for locations, roads, riders, orders and algorithm runs are displayed.                 |
|    4 | `Data Setup -> Show Dataset Requirement Status`       | Requirement counts are shown for the final report.                                            |
|    5 | `Student-ID Parameters`                               | All 17 member IDs and derived parameters are displayed.                                       |
|    6 | `Browse Delivery Data -> Orders`                      | Orders are listed from the database.                                                          |
|    7 | `Order Dispatch -> Process Orders Using Priority`     | Selected orders can be marked `DISPATCHED`; audit events are recorded.                        |
|    8 | `Optimization -> Greedy Rider Assignment`             | Selected orders can be assigned to riders; orders become `ASSIGNED` and riders become `BUSY`. |
|    9 | `Routes And Graphs -> Dijkstra Shortest Route`        | A route, cost and trace table are displayed.                                                  |
|   10 | `Optimization -> Dynamic Programming Order Selection` | Student-ID capacity is offered and DP trace is displayed.                                     |
|   11 | `Audit And Undo`                                      | Recent dispatch/assignment audit events are visible.                                          |
|   12 | `Performance Lab -> Run Quick Demo Experiments`       | Raw and averaged CSVs are exported under `results/csv`.                                       |
|   13 | `Performance Lab -> Show Graph Commands`              | Quick and final report graph commands are displayed.                                          |

## Final Report Performance Evidence

Run this only when producing final report evidence, because it uses larger input sizes:

```text
Performance Lab -> Run Final Report Experiments
```

Expected output files:

```text
results/csv/algorithm_runs_report.csv
results/csv/algorithm_run_averages_report.csv
```

Generate final report graphs:

```bash
python scripts/plot-results/plot_algorithm_runs.py --input results/csv/algorithm_runs_report.csv --output-dir results/graphs-report
```

## Pass Criteria

- The app starts from `mvn exec:java`.
- Database setup and CSV import complete.
- Dispatch and assignment actions change database-backed statuses.
- Audit events appear after database-changing actions.
- Student-ID parameters appear before affected algorithm flows.
- Search, sort, route, optimization and performance menus run without crashing.
- Performance CSV files are produced.

## Delivered/Cancelled Status Note

The project brief requires database-backed service request handling, dispatching, assignment and evidence of state changes. The current demo flow already persists `PENDING -> DISPATCHED` and `PENDING/DISPATCHED -> ASSIGNED` transitions with audit events. Separate `DELIVERED` or `CANCELLED` transitions are not required for the current brief unless the examiner specifically asks for a full order-lifecycle extension.
