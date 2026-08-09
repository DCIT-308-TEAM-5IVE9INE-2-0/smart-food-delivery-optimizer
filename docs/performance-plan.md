# Performance Plan

Performance results will be exported as CSV and plotted using Python or Excel.

The console application can run the default performance lab from option 19. The
run records are saved in the SQLite `algorithm_runs` table and exported to:

```text
results/csv/algorithm_runs.csv
```

Console option 20 displays the stored performance results.

SVG graphs can be generated with:

```bash
python scripts/plot-results/plot_algorithm_runs.py
```

## Required Experiments

- Linear search vs binary search.
- Selection sort, insertion sort, merge sort and quicksort.
- Hash table load factor and collision count.
- BST vs balanced tree.
- Heap priority dispatch.
- BFS, DFS, Dijkstra and MST algorithms.

Each experiment should run at least three times for each input size, and the report should use average runtime.

Default implemented input sizes:

```text
50, 100, 200
```

Default trials per input size:

```text
3
```

The default sizes are intentionally small enough for a live console demo. Larger
sizes may be used later for final report graphs after confirming machine time.

## CSV Columns

```text
run_id,algorithm_name,input_size,execution_time_ns,memory_kb,trial_number,date_run
```

Suggested graph columns:

- X-axis: `input_size`
- Y-axis: average `execution_time_ns`
- Series: `algorithm_name`

Generated graph files:

- `results/graphs/search.svg`
- `results/graphs/sorting.svg`
- `results/graphs/hash.svg`
- `results/graphs/heap.svg`
- `results/graphs/tree.svg`
- `results/graphs/graph.svg`

## Machine Specification

The final report must state the machine used for measurements.
