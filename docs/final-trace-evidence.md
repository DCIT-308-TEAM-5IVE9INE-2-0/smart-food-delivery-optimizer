# Final Trace Evidence

Project: SMART FOOD DELIVERY

These trace tables provide report-ready evidence for six required algorithm areas. The examples use records and values from the seed dataset plus the group-specific Student-ID parameters documented in `docs/index-number-parameters.md`.

Student-ID parameters used:

| Parameter | Value |
| --- | ---: |
| Priority weight | 2 |
| Route penalty | 2 |
| Hash table initial size | 71 |
| Dynamic programming capacity | 17 |

## 1. Binary Search Trace

Input order IDs from the seed order data:

```text
[1, 2, 3, 4, 5, 6, 7, 8]
```

Target:

```text
7
```

| Step | Action | State |
| ---: | --- | --- |
| 1 | check mid=3 | low=0, high=7, value=4 |
| 2 | check mid=5 | low=4, high=7, value=6 |
| 3 | check mid=6 | low=6, high=7, value=7 |
| 4 | target found | index=6 |

Output: order ID `7` found at index `6`.

## 2. Insertion Sort Trace

Input urgency values from seed orders `1` to `6`:

```text
[3, 2, 2, 1, 1, 2]
```

| Step | Action | State |
| ---: | --- | --- |
| 0 | initial | [3, 2, 2, 1, 1, 2] |
| 1 | insert index 1 | [2, 3, 2, 1, 1, 2] |
| 2 | insert index 2 | [2, 2, 3, 1, 1, 2] |
| 3 | insert index 3 | [1, 2, 2, 3, 1, 2] |
| 4 | insert index 4 | [1, 1, 2, 2, 3, 2] |
| 5 | insert index 5 | [1, 1, 2, 2, 2, 3] |

Output: urgencies sorted in ascending order.

## 3. Merge Sort Trace

Input urgency values from seed orders `1` to `6`:

```text
[3, 2, 2, 1, 1, 2]
```

| Step | Action | State |
| ---: | --- | --- |
| 0 | initial | [3, 2, 2, 1, 1, 2] |
| 1 | merge 0-1 | [2, 3, 2, 1, 1, 2] |
| 2 | merge 0-2 | [2, 2, 3, 1, 1, 2] |
| 3 | merge 3-4 | [2, 2, 3, 1, 1, 2] |
| 4 | merge 3-5 | [2, 2, 3, 1, 1, 2] |
| 5 | merge 0-5 | [1, 1, 2, 2, 2, 3] |

Output: urgencies sorted in ascending order.

## 4. Dijkstra Shortest Route Trace

Seed roads used:

| Road ID | From | To | Base Cost | Student-ID Route Penalty | Final Weight |
| ---: | ---: | ---: | ---: | ---: | ---: |
| 8 | 51 | 74 | 2.36 | 2 | 4.36 |
| 9 | 74 | 23 | 1.51 | 2 | 3.51 |
| 4 | 23 | 25 | 2.52 | 2 | 4.52 |

Location names:

| ID | Name |
| ---: | --- |
| 51 | Central Cafeteria, CC |
| 74 | Block B |
| 23 | Sarbah Main |
| 25 | Mensah Sarbah Dining Hall |

Source: `51`

Destination: `25`

| Step | Action | State |
| ---: | --- | --- |
| 1 | settle 51 | distance=0.0 |
| 2 | settle 74 | distance=4.36 |
| 3 | settle 23 | distance=7.87 |
| 4 | settle 25 | distance=12.39 |

Output path:

```text
51 -> 74 -> 23 -> 25
```

Output distance: `12.39`.

## 5. Kruskal Minimum Connection Network Trace

The same route subgraph is used for MST evidence.

| Step | Action | State |
| ---: | --- | --- |
| 1 | accept 74-23 | weight=3.51 |
| 2 | accept 51-74 | weight=4.36 |
| 3 | accept 23-25 | weight=4.52 |

Output selected edges:

```text
74-23, 51-74, 23-25
```

Output total weight: `12.39`.

## 6. Dynamic Programming Order Selection Trace

Input orders from seed orders `1` to `4`:

| Order ID | Urgency Value | Estimated Distance | DP Cost |
| ---: | ---: | ---: | ---: |
| 1 | 3 | 4.45 | 5 |
| 2 | 2 | 3.59 | 4 |
| 3 | 2 | 4.69 | 5 |
| 4 | 1 | 1.97 | 2 |

Capacity: Student-ID dynamic programming capacity `17`.

| Step | Action | State |
| ---: | --- | --- |
| 1 | process order 1 | bestValueAtCapacity=3 |
| 2 | process order 2 | bestValueAtCapacity=5 |
| 3 | process order 3 | bestValueAtCapacity=7 |
| 4 | process order 4 | bestValueAtCapacity=8 |

Output selected orders:

```text
1, 2, 3, 4
```

Output total urgency value: `8`.

Output total distance cost: `16`.
