# Performance Plan

Performance results will be exported as CSV and plotted using Python or Excel.

The console application has two performance modes:

- Quick demo mode: `Performance Lab -> Run Quick Demo Experiments`
- Final report mode: `Performance Lab -> Run Final Report Experiments`

Both modes save raw trial records in the SQLite `algorithm_runs` table.

Quick demo raw results are exported to:

```text
results/csv/algorithm_runs.csv
```

Quick demo averages are exported to:

```text
results/csv/algorithm_run_averages.csv
```

Final report raw results are exported to:

```text
results/csv/algorithm_runs_report.csv
```

Final report averages are exported to:

```text
results/csv/algorithm_run_averages_report.csv
```

Stored results can be viewed from `Performance Lab -> View Stored Results`.

SVG graphs can be generated with:

```bash
python scripts/plot-results/plot_algorithm_runs.py
```

Final report SVG graphs can be generated with:

```bash
python scripts/plot-results/plot_algorithm_runs.py --input results/csv/algorithm_runs_report.csv --output-dir results/graphs-report
```

## Required Experiments

- Linear search vs binary search.
- Selection sort, insertion sort, merge sort and quicksort.
- Hash table load factor and collision count.
- BST vs balanced tree.
- Heap priority dispatch.
- BFS, DFS, Dijkstra and MST algorithms.

Each experiment should run at least three times for each input size, and the report should use average runtime.

Quick demo input sizes:

```text
50, 100, 200
```

Default trials per input size:

```text
3
```

The default sizes are intentionally small enough for a live console demo. Larger
sizes are available in final report mode.

Final report input sizes:

| Experiment group | Input sizes |
| --- | --- |
| Search | 100, 500, 1000, 5000, 10000 |
| Sorting | 100, 500, 1000, 5000, 10000 |
| Hash table | 100, 500, 1000, 5000, 10000, 20000 |
| Heap | 100, 500, 1000, 5000, 10000, 20000 |
| Tree | 100, 500, 1000, 5000, 10000 |
| Graph | 50, 100, 200, 500 |

Expected final report raw rows:

```text
282
```

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

Final report graph files use the same names inside:

```text
results/graphs-report
```

## Machine Specification

The final report must state the machine used for measurements.
