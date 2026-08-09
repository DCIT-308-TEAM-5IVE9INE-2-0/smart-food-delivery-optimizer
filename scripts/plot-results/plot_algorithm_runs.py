#!/usr/bin/env python3
"""
Create SVG performance graphs from results/csv/algorithm_runs.csv.

This script uses only the Python standard library so teammates do not need to
install matplotlib. It averages repeated trials by algorithm and input size,
then writes one SVG per experiment group to results/graphs.
"""

from __future__ import annotations

import argparse
import csv
from collections import defaultdict
from pathlib import Path


GROUPS = {
    "search": {"Linear Search", "Binary Search"},
    "sorting": {"Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"},
    "hash": {"Hash Table Insert", "Hash Table Lookup"},
    "heap": {"Min Heap Insert", "Min Heap Extract"},
    "tree": {"BST Insert", "BST Search", "Red Black Tree Insert", "Red Black Tree Search"},
    "graph": {"BFS", "DFS", "Dijkstra", "Prim MST", "Kruskal MST"},
}

COLORS = [
    "#1f77b4",
    "#d62728",
    "#2ca02c",
    "#9467bd",
    "#ff7f0e",
    "#17becf",
]


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Plot SMART FOOD DELIVERY algorithm benchmark CSVs.")
    parser.add_argument(
        "--input",
        type=Path,
        default=Path("results/csv/algorithm_runs.csv"),
        help="Input CSV path. Default: results/csv/algorithm_runs.csv",
    )
    parser.add_argument(
        "--output-dir",
        type=Path,
        default=Path("results/graphs"),
        help="Output directory for SVG graphs. Default: results/graphs",
    )
    return parser.parse_args()


def read_averages(input_file: Path) -> dict[str, dict[int, float]]:
    grouped: dict[tuple[str, int], list[float]] = defaultdict(list)
    with input_file.open("r", encoding="utf-8", newline="") as file:
        reader = csv.DictReader(file)
        for row in reader:
            algorithm = row["algorithm_name"]
            size = int(row["input_size"])
            elapsed_ns = float(row["execution_time_ns"])
            grouped[(algorithm, size)].append(elapsed_ns)

    averages: dict[str, dict[int, float]] = defaultdict(dict)
    for (algorithm, size), values in grouped.items():
        averages[algorithm][size] = sum(values) / len(values)
    return averages


def write_group_graph(output_dir: Path, group_name: str, algorithms: set[str], averages: dict[str, dict[int, float]]) -> bool:
    present_algorithms = [name for name in sorted(algorithms) if name in averages]
    if not present_algorithms:
        return False

    sizes = sorted({size for name in present_algorithms for size in averages[name]})
    max_y = max(averages[name][size] for name in present_algorithms for size in averages[name])
    max_y = max(max_y, 1.0)

    width = 960
    height = 540
    left = 80
    right = 220
    top = 50
    bottom = 80
    plot_width = width - left - right
    plot_height = height - top - bottom

    def x_pos(size: int) -> float:
        if len(sizes) == 1:
            return left + plot_width / 2
        return left + sizes.index(size) * (plot_width / (len(sizes) - 1))

    def y_pos(value: float) -> float:
        return top + plot_height - (value / max_y) * plot_height

    lines = [
        '<?xml version="1.0" encoding="UTF-8"?>',
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="{height}" viewBox="0 0 {width} {height}">',
        '<rect width="100%" height="100%" fill="#ffffff"/>',
        f'<text x="{width / 2}" y="28" text-anchor="middle" font-family="Arial" font-size="20" font-weight="700">{title(group_name)} Performance</text>',
        f'<line x1="{left}" y1="{top}" x2="{left}" y2="{top + plot_height}" stroke="#333"/>',
        f'<line x1="{left}" y1="{top + plot_height}" x2="{left + plot_width}" y2="{top + plot_height}" stroke="#333"/>',
        f'<text x="{left + plot_width / 2}" y="{height - 24}" text-anchor="middle" font-family="Arial" font-size="14">Input size</text>',
        f'<text x="22" y="{top + plot_height / 2}" text-anchor="middle" font-family="Arial" font-size="14" transform="rotate(-90 22 {top + plot_height / 2})">Average runtime (ns)</text>',
    ]

    for size in sizes:
        x = x_pos(size)
        lines.append(f'<line x1="{x:.2f}" y1="{top + plot_height}" x2="{x:.2f}" y2="{top + plot_height + 6}" stroke="#333"/>')
        lines.append(f'<text x="{x:.2f}" y="{top + plot_height + 24}" text-anchor="middle" font-family="Arial" font-size="12">{size}</text>')

    for tick in range(5):
        value = max_y * tick / 4
        y = y_pos(value)
        lines.append(f'<line x1="{left - 6}" y1="{y:.2f}" x2="{left}" y2="{y:.2f}" stroke="#333"/>')
        lines.append(f'<line x1="{left}" y1="{y:.2f}" x2="{left + plot_width}" y2="{y:.2f}" stroke="#e5e5e5"/>')
        lines.append(f'<text x="{left - 10}" y="{y + 4:.2f}" text-anchor="end" font-family="Arial" font-size="11">{int(value)}</text>')

    for index, algorithm in enumerate(present_algorithms):
        color = COLORS[index % len(COLORS)]
        points = [(x_pos(size), y_pos(averages[algorithm][size])) for size in sizes if size in averages[algorithm]]
        point_text = " ".join(f"{x:.2f},{y:.2f}" for x, y in points)
        lines.append(f'<polyline points="{point_text}" fill="none" stroke="{color}" stroke-width="2.5"/>')
        for x, y in points:
            lines.append(f'<circle cx="{x:.2f}" cy="{y:.2f}" r="4" fill="{color}"/>')
        legend_y = top + index * 24
        lines.append(f'<rect x="{left + plot_width + 30}" y="{legend_y - 10}" width="14" height="14" fill="{color}"/>')
        lines.append(f'<text x="{left + plot_width + 52}" y="{legend_y + 2}" font-family="Arial" font-size="13">{algorithm}</text>')

    lines.append("</svg>")
    output_dir.mkdir(parents=True, exist_ok=True)
    output_file = output_dir / f"{group_name}.svg"
    output_file.write_text("\n".join(lines), encoding="utf-8")
    return True


def title(value: str) -> str:
    return " ".join(part.capitalize() for part in value.split("_"))


def main() -> int:
    args = parse_args()
    if not args.input.exists():
        print(f"Input CSV not found: {args.input}")
        print("Run console option 19 first, or provide --input.")
        return 2

    averages = read_averages(args.input)
    written = 0
    for group_name, algorithms in GROUPS.items():
        if write_group_graph(args.output_dir, group_name, algorithms, averages):
            written += 1

    print(f"Wrote {written} graph files to {args.output_dir}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
