# Dataset Evidence Note

This note documents the source, generation method and scale of the seed dataset in `data/*.csv`, as required by Meeting 4 (dataset evidence note) and tracked as an outstanding item in Meeting 5 (`docs/meetings/meeting-05.md`).

## Generation Script

All of `data/locations.csv`, `data/roads.csv`, `data/restaurants.csv`, `data/customers.csv`, `data/riders.csv` and `data/orders.csv` are produced by:

```bash
python scripts/generate-data/generate_dataset.py
```

The script is deterministic: every random choice (location sampling, road jitter, name/attribute selection, order timing) is driven by a single seed, so re-running it reproduces the same dataset.

`data/algorithm_runs.csv` is out of scope for this script — those rows come from running the console app's Performance Lab (see `docs/performance-plan.md`), owned by the Testing and Performance team.

## Sources

- **Locations** are real named landmarks around University of Ghana, Legon, East Legon, Madina and Haatso, fetched from OpenStreetMap through the Overpass API by `scripts/generate-data/generate_locations.py` (see that script's own README for fetch instructions). The raw fetch is cached at `scripts/generate-data/data/landmarks.generated.json` and converted to `scripts/generate-data/data/locations.csv` (1,244 candidate landmarks). `generate_dataset.py` selects a curated subset of these — it does not invent place names. This matches the Meeting 4 clarification that public OSM location data is acceptable.
- **Restaurants** reuse real landmark names typed `Restaurant` or `Restaurant Area` in the selected location subset, each suffixed with a randomly assigned Ghanaian cuisine (e.g. Waakye, Jollof Rice, Banku and Tilapia, Kelewele, Shawarma) to give every restaurant a `category`/cuisine for order generation.
- **Customers** and **riders** are entirely fictional: names are built by combining a pool of common Ghanaian first names and surnames, and customer phone numbers use an obviously synthetic `0200000001`-style sequential pattern. No real personal data is used, per the project brief's privacy requirement.
- **Roads** are not sourced from OSM road geometry. They are generated edges between the selected locations, weighted by real great-circle (haversine) distance between their actual coordinates.

## Generation Method

1. **Locations (80 of 80 required minimum: 50).** Stratified sampling from the 1,244 fetched landmarks by `type` (Academic, Hostel, Restaurant, Restaurant Area, Transport Stop, Market, Health, Service, Residential Area, Landmark), so the final set has a realistic mix of destinations rather than being dominated by one category. IDs are renumbered 1–80.
2. **Roads (150 of 100 required minimum).** Built in two passes over all candidate location pairs sorted by real haversine distance:
   - Pass 1 runs a Kruskal-style minimum spanning tree (using a disjoint-set/union-find) over the shortest edges first, guaranteeing the network is a single connected component — matching the Meeting 4 clarification that the main road graph should be fully connected.
   - Pass 2 adds further short, local next-nearest-neighbour edges until 150 roads exist, so the graph has realistic redundancy for BFS/DFS/Dijkstra/MST demonstrations, not just a bare tree.
   - Every road is treated as one bidirectional edge (`is_bidirectional = 1`), per the Meeting 4 decision to count a bidirectional connection as a single record.
   - `travel_time_minutes` is derived from `distance_km` and a randomised assumed speed (12–28 km/h, reflecting campus/urban traffic); `road_condition_weight` is a random value in [1.0, 1.6]. The combined edge weight used by Dijkstra/Prim/Kruskal in the application follows the Meeting 4 formula: distance weight + travel-time weight + road-condition penalty.
3. **Restaurants (14, minimum useful floor 12).** All available `Restaurant`/`Restaurant Area` locations in the selected 80 are used (no synthetic restaurant locations are invented). Two are marked `CLOSED` for status variety; the rest are `OPEN`.
4. **Customers (70)** and **Riders (40 of 30 required minimum).** Fictional names as above. Customer default locations are biased toward hostel/residential/academic/market-type locations (realistic delivery destinations). Rider home locations are biased toward hostel/transport-stop/residential locations (realistic rider bases). Riders' `vehicle_type` is weighted Motorbike 60% / Bicycle 30% / Tricycle 10%, with capacity ranges matching the vehicle.
5. **Orders (350 of 300 required minimum).** For each order: a restaurant and customer are chosen at random; `source_location_id` is the restaurant's location, `destination_location_id` is the customer's default location; `category` is the restaurant's cuisine; `urgency` (1–5) is weighted toward the middle of the range; `estimated_distance` is the real haversine distance between source and destination; `time_submitted` is randomised across the 7 days ending 2026-08-10, biased toward lunch (12:00–14:00) and dinner (18:00–21:00) hours; `deadline` is `time_submitted` plus 15–90 minutes, tightened for higher-urgency orders.
   - **Status snapshot.** Orders are sorted by `time_submitted` and the oldest ~10% are marked `DISPATCHED`, the next ~20% `ASSIGNED`, and the remaining ~70% `PENDING` — representing a realistic operational snapshot where older orders have already progressed further than newer ones, rather than every row being uniformly `PENDING`.
   - Every `ASSIGNED`/`DISPATCHED` order is given a real `assigned_rider_id`, and that rider's `availability_status` is set to `BUSY` with `current_location_id` updated to the order's destination — mirroring exactly what `DatabaseService.assignOrderToRider` does at runtime, so the seed data is internally consistent with the application's own state transitions.
   - 15 of the 40 riders are deliberately held out of the initial assignment pool and stay `AVAILABLE`, so the console's live dispatch/assignment demo always has real available riders to assign `PENDING` orders to.

## Random Data Seed

The script's single RNG seed is the sum of all 17 team member index numbers, modulo 1,000,000 — the "Random Data Seed" parameter proposed for the project in `docs/meetings/meeting-05.md` (section 3.2). This ties dataset generation to the team's own index numbers per the project brief's requirement that at least three algorithm/data parameters be index-number-derived, and makes the dataset fully reproducible from the source code alone.

## Reproducing Or Regenerating

```bash
python scripts/generate-data/generate_dataset.py
```

Re-running overwrites `data/locations.csv`, `data/roads.csv`, `data/restaurants.csv`, `data/customers.csv`, `data/riders.csv` and `data/orders.csv` with an identical dataset (the seed is fixed in the script), then prints a pass/fail validation summary — see `docs/dataset-validation-report.md` for the last recorded run.
