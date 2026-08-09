# Plot Results

Optional Python or spreadsheet helper scripts for performance graphs can be placed here.

## Algorithm Runtime Graphs

Run the performance lab first from the Java console:

```bash
mvn exec:java
```

Then choose option 19 to generate `results/csv/algorithm_runs.csv`.

Create SVG graphs:

```bash
python scripts/plot-results/plot_algorithm_runs.py
```

The script writes graph files to `results/graphs`:

- `search.svg`
- `sorting.svg`
- `hash.svg`
- `heap.svg`
- `tree.svg`
- `graph.svg`

The script uses only Python standard-library modules.
