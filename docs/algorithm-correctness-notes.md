# Algorithm Correctness Notes

Project : Smart Food Delivery

## Binary Search

Precondition: the order records must be sorted by the same key used by the
binary-search comparator.

Loop invariant:

- If the target order exists, it is always inside the current `low..high`
  search range.
- Values before `low` are smaller than the target.
- Values after `high` are greater than the target.

At each step the middle record is checked. If it is smaller than the target,
the left half cannot contain the target and `low` moves right. If it is larger,
the right half cannot contain the target and `high` moves left. The range gets
smaller every step, so the algorithm either finds the target or proves it is
absent.

Counterexample:

Binary search can fail on unsorted data because the decision to discard half of
the array depends on sorted order.

Example:

```text
values = [30, 10, 20]
target = 10
```

The middle value is `10` only by chance here. For a different target, the
algorithm may discard the side containing the real value because the ordering
rule is broken.

## Insertion Sort

Loop invariant:

- Before each outer-loop pass, the left side of the array is already sorted.
- The next unsorted item is inserted into its correct position inside that
  sorted left side.

The algorithm starts with one sorted item. Each pass increases the sorted
section by one item. When all items have been inserted, the entire array is
sorted.

## Merge Sort

Correctness idea:

- A list of size zero or one is already sorted.
- The algorithm recursively sorts the left and right halves.
- The merge step repeatedly chooses the smaller front item from the two sorted
  halves, producing one sorted output.

By induction, if both halves are sorted correctly, the merge operation produces
a correctly sorted whole array.

## Dijkstra Shortest Path

Precondition: edge weights must be non-negative.

Correctness idea:

- The unsettled vertex with the smallest known distance is selected next.
- Because all weights are non-negative, no later route can produce a smaller
  distance to that settled vertex.
- Each edge relaxation improves the best known distance to neighbouring
  locations.

When the target is settled, its distance is final. If the remaining reachable
set is exhausted and the target still has infinity distance, the destination is
unreachable.

## Kruskal Minimum Spanning Tree

Correctness idea:

- Edges are considered from lowest weight to highest weight.
- An edge is added only if it connects two previously separate components.
- The disjoint-set structure prevents cycles.

By always selecting the cheapest edge that safely connects two components, the
algorithm builds a minimum-cost connection network.

## Dynamic Programming Order Selection

Problem model:

- Each order has a cost, represented by estimated delivery distance units.
- Each order has a value, represented by urgency.
- The capacity is the maximum total distance the rider system can accept in the
  current selection.

Correctness idea:

- `table[i][c]` stores the best urgency value using the first `i` orders with
  capacity `c`.
- For each order, the algorithm chooses the better of two choices:
  include the order if it fits, or exclude it.
- The table builds from smaller subproblems to the full problem.

The selected orders are reconstructed by walking backward through the completed
table and checking where including an order changed the best value.

## Greedy Rider Assignment Counterexample

Greedy chooses the nearest available rider for the current order. This is fast
but not always globally optimal.

Counterexample cost matrix:

```text
          Rider 1   Rider 2
Order 1      1        2
Order 2      2      100
```

Greedy chooses Rider 1 for Order 1 with cost `1`, leaving Rider 2 for Order 2
with cost `100`. Total cost is `101`.

The better assignment is:

```text
Order 1 -> Rider 2 = 2
Order 2 -> Rider 1 = 2
Total = 4
```

This is why the project includes brute-force assignment for small cases as a
correctness comparison.
