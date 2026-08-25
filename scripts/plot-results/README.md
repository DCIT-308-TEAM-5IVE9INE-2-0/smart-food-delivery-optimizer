# Plot Results

Optional Python or spreadsheet helper scripts for performance graphs can be placed here.

## Algorithm Runtime Graphs

Run the performance lab first from the Java console:

```bash
mvn exec:java
```

For quick demo results, choose:

```text
Performance Lab -> Run Quick Demo Experiments
```

This generates:

```text
results/csv/algorithm_runs.csv
results/csv/algorithm_run_averages.csv
```

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

For final report results, choose:

```text
Performance Lab -> Run Final Report Experiments
```

This generates:

```text
results/csv/algorithm_runs_report.csv
results/csv/algorithm_run_averages_report.csv
```

Create final report SVG graphs:

```bash
python scripts/plot-results/plot_algorithm_runs.py --input results/csv/algorithm_runs_report.csv --output-dir results/graphs-report
```

The final report graphs are written to `results/graphs-report`.

The script uses only Python standard-library modules.
