# Student-ID-Derived Algorithm Parameters

Project: SMART FOOD DELIVERY

Group: Group 39, Team_5ive9ine_2.0

## Purpose

The official project brief requires each team to derive at least three algorithm parameters from member index numbers. Meeting 4 clarified that all 17 member IDs should be included in the parameter-generation input.

These parameters make the project team-specific and are used as evidence that the algorithm traces and demonstrations belong to this group.

## Student IDs Used

```text
22020618, 22012447, 22166686, 22146249, 22106332, 22042260,
22042713, 22370501, 22411093, 22399487, 22262272, 22306912,
22308781, 22382964, 22413798, 22402374, 22408680
```

Number of IDs used: 17

## Base Calculations

| Calculation | Value |
| --- | ---: |
| Sum of all digits in all IDs | 461 |
| Sum of the last two digits of each ID | 967 |
| Sum of all full ID numbers | 378204167 |
| Sum of final digit of each ID | 67 |

## Derived Parameters

| Parameter | Formula | Value | Intended Use |
| --- | --- | ---: | --- |
| Priority weight | `(sum of all ID digits % 5) + 1` | 2 | Priority dispatch scoring |
| Route penalty | `(sum of last two digits of each ID % 7) + 1` | 2 | Route and graph edge weighting |
| Hash table initial size | `nextPrimeAfter((sum of all IDs % 50) + 50)` | 71 | Hash table indexing and experiments |
| Random data seed | `sum of all IDs % 1,000,000` | 204167 | Repeatable generated data and performance experiments |
| Dynamic programming capacity | `(sum of final ID digits % 20) + 10` | 17 | Order-selection capacity for DP demo |

## Formula Explanation

### Priority Weight

The priority weight is based on the sum of every digit in every member ID.

```text
priorityWeight = (461 % 5) + 1
priorityWeight = 2
```

This value is used by the console priority dispatch flow:

```text
priorityScore = orderUrgency * priorityWeight
```

### Route Penalty

The route penalty is based on the last two digits of each member ID.

```text
routePenalty = (967 % 7) + 1
routePenalty = 2
```

This value is used by `RouteService` when building the graph used by Dijkstra, BFS, DFS, Prim and Kruskal:

```text
routeCost = (travelTimeMinutes * roadConditionWeight) + routePenalty
```

### Hash Table Initial Size

The hash table initial size is based on the sum of the full student ID numbers.

```text
baseSize = (378204167 % 50) + 50
baseSize = 67

hashTableInitialSize = next prime after 67
hashTableInitialSize = 71
```

This prime value is used as the initial capacity for the hash table performance experiment.

### Random Data Seed

The random-data seed is based on the sum of the full student ID numbers.

```text
randomDataSeed = 378204167 % 1000000
randomDataSeed = 204167
```

This value can be used when generating repeatable synthetic orders, riders or performance-test inputs.

### Dynamic Programming Capacity

The dynamic programming capacity is based on the final digit of every member ID.

```text
dynamicProgrammingCapacity = (67 % 20) + 10
dynamicProgrammingCapacity = 17
```

This value is shown as the default capacity constraint for the dynamic-programming order-selection demo.

## Source-Code Evidence

The calculations are implemented in:

```text
src/main/java/edu/ug/smartdelivery/service/StudentIdParameterService.java
src/main/java/edu/ug/smartdelivery/service/StudentIdParameters.java
```

The parameters are applied in:

```text
src/main/java/edu/ug/smartdelivery/app/ConsoleMenu.java
src/main/java/edu/ug/smartdelivery/service/RouteService.java
src/main/java/edu/ug/smartdelivery/service/ExperimentService.java
src/main/java/edu/ug/smartdelivery/experiment/HashExperiment.java
```

The deterministic tests are implemented in:

```text
src/test/java/edu/ug/smartdelivery/service/StudentIdParameterServiceTest.java
```

The console application displays the calculated values through:

```text
Main Menu -> Student-ID Parameters
```

## Verification

The parameter calculations are verified by unit tests. The test suite confirms:

- All 17 IDs are included.
- The base sums are correct.
- The derived parameter values are deterministic.
- Priority dispatch and route scoring use the derived parameters.
- The performance lab hash experiment uses the derived initial table size.
- Returned student-ID arrays are defensively copied.
- Missing or invalid student IDs are rejected.
