#!/usr/bin/env python3
"""
Generate the final SMART FOOD DELIVERY seed dataset for data/*.csv.

Reads the already-fetched real OpenStreetMap landmark data at
scripts/generate-data/data/locations.csv (produced by generate_locations.py)
and derives locations, roads, restaurants, customers, riders and orders from
it. Customer/rider names and phone numbers are entirely fictional.

Determinism: every random choice in this script is driven by a single seed
derived from the team's 17 student index numbers (sum of all full IDs modulo
1,000,000), matching the "Random Data Seed" parameter proposed for the
project in Meeting 5 (docs/meetings/meeting-05.md, section 3.2). This makes
the dataset reproducible without hand-tuning a magic number.

Usage:
    python scripts/generate-data/generate_dataset.py

Only Python standard-library modules are used.
"""

from __future__ import annotations

import csv
import math
import random
from dataclasses import dataclass
from datetime import datetime, timedelta
from pathlib import Path

# ---------------------------------------------------------------------------
# Configuration
# ---------------------------------------------------------------------------

REPO_ROOT = Path(__file__).resolve().parents[2]
SOURCE_LOCATIONS_CSV = Path(__file__).resolve().parent / "data" / "locations.csv"
DATA_DIR = REPO_ROOT / "data"

TARGET_LOCATIONS = 80
TARGET_ROADS = 150
TARGET_RESTAURANTS_MIN = 12
TARGET_CUSTOMERS = 70
TARGET_RIDERS = 40
TARGET_ORDERS = 350

# Team member index numbers (docs/contribution-log.md / meeting-05).
MEMBER_INDEX_NUMBERS = [
    22020618, 22012447, 22166686, 22146249, 22106332, 22042260,
    22042713, 22370501, 22411093, 22399487, 22262272, 22306912,
    22308781, 22382964, 22413798, 22402374, 22408680,
]
RANDOM_DATA_SEED = sum(MEMBER_INDEX_NUMBERS) % 1_000_000

# Location types that best represent restaurant/vendor sites, customer
# delivery zones and rider dispatch/home bases, in priority order.
RESTAURANT_TYPES = ["Restaurant", "Restaurant Area"]
RESTAURANT_FALLBACK_TYPES = ["Market"]  # only used if primary types are too few
CUSTOMER_ZONE_TYPES = ["Hostel", "Residential Area", "Academic", "Market"]
RIDER_BASE_TYPES = ["Hostel", "Transport Stop", "Residential Area"]

CUISINES = [
    "Waakye", "Jollof Rice", "Banku and Tilapia", "Fufu and Light Soup",
    "Kelewele", "Red-Red", "Fried Rice", "Shawarma", "Pizza",
    "Burger and Fries", "Local Dishes", "Rice and Stew",
]

FIRST_NAMES = [
    "Kofi", "Ama", "Kwame", "Akosua", "Yaw", "Efua", "Kojo", "Abena",
    "Kwabena", "Adjoa", "Kwaku", "Akua", "Yaa", "Fiifi", "Esi", "Nana",
    "Selorm", "Elikem", "Dela", "Mawuli", "Sena", "Edem", "Enam", "Delali",
    "Nii", "Naa", "Tetteh", "Adjeley", "Odartey", "Korkor", "Kwesi", "Aba",
]
LAST_NAMES = [
    "Mensah", "Asante", "Boateng", "Owusu", "Appiah", "Osei", "Agyeman",
    "Sarpong", "Ofori", "Adjei", "Amponsah", "Darko", "Frimpong", "Gyasi",
    "Kufuor", "Nkrumah", "Quaye", "Tetteh", "Kodjoh", "Akplu", "Dzah",
    "Normanyo", "Amaniampong", "Otchere", "Osafo", "Freeman", "Okoe",
]

VEHICLE_PROFILE = [
    ("Motorbike", 0.6, (2, 3)),
    ("Bicycle", 0.3, (1, 1)),
    ("Tricycle", 0.1, (3, 4)),
]

URGENCY_WEIGHTS = {1: 15, 2: 30, 3: 30, 4: 15, 5: 10}
STATUS_SHARE = {"DISPATCHED": 0.10, "ASSIGNED": 0.20, "PENDING": 0.70}
RIDERS_HELD_IN_RESERVE = 15  # kept AVAILABLE for the live dispatch/assign demo

DATASET_END_DATE = datetime(2026, 8, 10, 9, 0)
DATASET_WINDOW_DAYS = 7


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------

def haversine_km(lat1: float, lon1: float, lat2: float, lon2: float) -> float:
    radius_km = 6371.0
    p1, p2 = math.radians(lat1), math.radians(lat2)
    d_phi = math.radians(lat2 - lat1)
    d_lambda = math.radians(lon2 - lon1)
    a = math.sin(d_phi / 2) ** 2 + math.cos(p1) * math.cos(p2) * math.sin(d_lambda / 2) ** 2
    return radius_km * 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))


def weighted_choice(rng: random.Random, weights: dict) -> object:
    keys = list(weights.keys())
    values = [weights[k] for k in keys]
    return rng.choices(keys, weights=values, k=1)[0]


def write_csv(path: Path, fieldnames: list[str], rows: list[dict]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8", newline="") as file:
        writer = csv.DictWriter(file, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)


# ---------------------------------------------------------------------------
# Locations
# ---------------------------------------------------------------------------

def load_source_locations() -> list[dict]:
    with SOURCE_LOCATIONS_CSV.open(newline="", encoding="utf-8") as file:
        return list(csv.DictReader(file))


def pick_locations(rng: random.Random, source_rows: list[dict]) -> list[dict]:
    by_type: dict[str, list[dict]] = {}
    for row in source_rows:
        by_type.setdefault(row["type"], []).append(row)
    for bucket in by_type.values():
        rng.shuffle(bucket)

    type_targets = {
        "Academic": 10,
        "Hostel": 8,
        "Restaurant": 10,
        "Restaurant Area": 4,
        "Transport Stop": 8,
        "Market": 6,
        "Health": 5,
        "Service": 5,
        "Residential Area": 6,
    }

    selected: list[dict] = []
    for location_type, target in type_targets.items():
        bucket = by_type.get(location_type, [])
        selected.extend(bucket[:target])

    remaining_needed = TARGET_LOCATIONS - len(selected)
    landmark_pool = by_type.get("Landmark", [])[:]
    rng.shuffle(landmark_pool)
    selected.extend(landmark_pool[:remaining_needed])

    # Top up from any remaining unused rows if still short (small buckets).
    if len(selected) < TARGET_LOCATIONS:
        used_names = {row["name"] for row in selected}
        leftovers = [row for row in source_rows if row["name"] not in used_names]
        rng.shuffle(leftovers)
        selected.extend(leftovers[: TARGET_LOCATIONS - len(selected)])

    selected = selected[:TARGET_LOCATIONS]
    rng.shuffle(selected)

    locations = []
    for index, row in enumerate(selected, start=1):
        locations.append({
            "location_id": index,
            "name": row["name"],
            "area": row["area"],
            "type": row["type"],
            "latitude": row["latitude"],
            "longitude": row["longitude"],
        })
    return locations


# ---------------------------------------------------------------------------
# Roads
# ---------------------------------------------------------------------------

class DisjointSet:
    def __init__(self, size: int) -> None:
        self.parent = list(range(size))

    def find(self, item: int) -> int:
        while self.parent[item] != item:
            self.parent[item] = self.parent[self.parent[item]]
            item = self.parent[item]
        return item

    def union(self, a: int, b: int) -> bool:
        root_a, root_b = self.find(a), self.find(b)
        if root_a == root_b:
            return False
        self.parent[root_a] = root_b
        return True


def build_roads(rng: random.Random, locations: list[dict]) -> list[dict]:
    n = len(locations)
    coords = [(loc["location_id"], float(loc["latitude"]), float(loc["longitude"])) for loc in locations]

    candidate_edges = []
    for i in range(n):
        id_a, lat_a, lon_a = coords[i]
        for j in range(i + 1, n):
            id_b, lat_b, lon_b = coords[j]
            distance = haversine_km(lat_a, lon_a, lat_b, lon_b)
            candidate_edges.append((distance, id_a, id_b))
    candidate_edges.sort(key=lambda edge: edge[0])

    # Location IDs are 1..n; map to 0..n-1 for the disjoint set.
    dsu = DisjointSet(n)
    id_to_index = {loc["location_id"]: idx for idx, loc in enumerate(locations)}

    chosen_pairs: list[tuple[float, int, int]] = []
    used_pairs: set[tuple[int, int]] = set()

    # Pass 1: Kruskal-style minimum spanning tree for guaranteed connectivity.
    for distance, id_a, id_b in candidate_edges:
        if dsu.union(id_to_index[id_a], id_to_index[id_b]):
            chosen_pairs.append((distance, id_a, id_b))
            used_pairs.add((id_a, id_b))
        if len(chosen_pairs) == n - 1:
            break

    # Pass 2: add more short local roads (next-nearest neighbours) until the
    # target road count is reached, without duplicating an existing pair.
    for distance, id_a, id_b in candidate_edges:
        if len(chosen_pairs) >= TARGET_ROADS:
            break
        if (id_a, id_b) in used_pairs:
            continue
        chosen_pairs.append((distance, id_a, id_b))
        used_pairs.add((id_a, id_b))

    roads = []
    for road_id, (distance, id_a, id_b) in enumerate(chosen_pairs, start=1):
        speed_kmph = rng.uniform(12, 28)
        travel_time = max(1, round(distance / speed_kmph * 60 + rng.uniform(-1, 2)))
        condition_weight = round(rng.uniform(1.0, 1.6), 2)
        roads.append({
            "road_id": road_id,
            "from_location_id": id_a,
            "to_location_id": id_b,
            "distance_km": round(distance, 2),
            "travel_time_minutes": travel_time,
            "road_condition_weight": condition_weight,
            "is_bidirectional": 1,
        })
    return roads


# ---------------------------------------------------------------------------
# Restaurants, customers, riders
# ---------------------------------------------------------------------------

OPENING_HOURS = [("07:00", "21:00"), ("08:00", "20:00"), ("09:00", "22:00"), ("10:00", "23:00")]


def build_restaurants(rng: random.Random, locations: list[dict]) -> list[dict]:
    candidates = [loc for loc in locations if loc["type"] in RESTAURANT_TYPES]
    if len(candidates) < TARGET_RESTAURANTS_MIN:
        fallback = [loc for loc in locations if loc["type"] in RESTAURANT_FALLBACK_TYPES]
        rng.shuffle(fallback)
        candidates = candidates + fallback[: TARGET_RESTAURANTS_MIN - len(candidates)]
    rng.shuffle(candidates)
    candidates = candidates[: min(len(candidates), 18)]

    restaurants = []
    used_names: set[str] = set()
    for restaurant_id, location in enumerate(candidates, start=1):
        cuisine = rng.choice(CUISINES)
        name = f"{location['name']} - {cuisine}"
        suffix = 2
        while name in used_names:
            name = f"{location['name']} - {cuisine} ({suffix})"
            suffix += 1
        used_names.add(name)
        opening, closing = rng.choice(OPENING_HOURS)
        status = "CLOSED" if restaurant_id <= 2 else "OPEN"  # a couple closed for variety
        restaurants.append({
            "restaurant_id": restaurant_id,
            "name": name,
            "location_id": location["location_id"],
            "opening_time": opening,
            "closing_time": closing,
            "status": status,
            "_cuisine": cuisine,
            "_location": location,
        })
    return restaurants


def build_people_names(rng: random.Random, count: int) -> list[str]:
    names = set()
    while len(names) < count:
        candidate = f"{rng.choice(FIRST_NAMES)} {rng.choice(LAST_NAMES)}"
        names.add(candidate)
    return sorted(names)


def build_customers(rng: random.Random, locations: list[dict]) -> list[dict]:
    zone_locations = [loc for loc in locations if loc["type"] in CUSTOMER_ZONE_TYPES] or locations
    names = build_people_names(rng, TARGET_CUSTOMERS)
    customers = []
    for customer_id, name in enumerate(names, start=1):
        location = rng.choice(zone_locations)
        customers.append({
            "customer_id": customer_id,
            "name": name,
            "phone": f"0200{customer_id:06d}",
            "default_location_id": location["location_id"],
            "_location": location,
        })
    return customers


def build_riders(rng: random.Random, locations: list[dict]) -> list[dict]:
    base_locations = [loc for loc in locations if loc["type"] in RIDER_BASE_TYPES] or locations
    names = build_people_names(rng, TARGET_RIDERS)
    riders = []
    for rider_id, name in enumerate(names, start=1):
        vehicle_type = weighted_choice(rng, {v: w for v, w, _ in VEHICLE_PROFILE})
        capacity_range = next(cap for v, _, cap in VEHICLE_PROFILE if v == vehicle_type)
        capacity = rng.randint(*capacity_range)
        home = rng.choice(base_locations)
        riders.append({
            "rider_id": rider_id,
            "name": name,
            "home_location_id": home["location_id"],
            "vehicle_type": vehicle_type,
            "capacity": capacity,
            "availability_status": "AVAILABLE",
            "current_location_id": home["location_id"],
        })
    return riders


# ---------------------------------------------------------------------------
# Orders
# ---------------------------------------------------------------------------

def random_submission_time(rng: random.Random) -> datetime:
    day_offset = rng.randint(0, DATASET_WINDOW_DAYS - 1)
    base_day = DATASET_END_DATE - timedelta(days=day_offset)
    # Bias toward lunch (12-14) and dinner (18-21) hours.
    hour_pool = [7, 8, 9, 10, 11] + [12, 13, 14] * 3 + [15, 16, 17] + [18, 19, 20, 21] * 3 + [22]
    hour = rng.choice(hour_pool)
    minute = rng.randint(0, 59)
    return base_day.replace(hour=hour, minute=minute, second=0, microsecond=0)


def build_orders(rng: random.Random, restaurants: list[dict], customers: list[dict], riders: list[dict]) -> tuple[list[dict], list[dict]]:
    active_pool = riders[:len(riders) - RIDERS_HELD_IN_RESERVE]
    reserve_pool = riders[len(riders) - RIDERS_HELD_IN_RESERVE:]
    assert active_pool and reserve_pool

    raw_orders = []
    for _ in range(TARGET_ORDERS):
        restaurant = rng.choice(restaurants)
        customer = rng.choice(customers)
        urgency = weighted_choice(rng, URGENCY_WEIGHTS)
        submitted = random_submission_time(rng)
        deadline_minutes = max(15, rng.randint(20, 90) - urgency * 5)
        deadline = submitted + timedelta(minutes=deadline_minutes)

        source = restaurant["_location"]
        destination = customer["_location"]
        distance = haversine_km(
            float(source["latitude"]), float(source["longitude"]),
            float(destination["latitude"]), float(destination["longitude"]),
        )

        raw_orders.append({
            "restaurant": restaurant,
            "customer": customer,
            "source_location_id": source["location_id"],
            "destination_location_id": destination["location_id"],
            "category": restaurant["_cuisine"],
            "urgency": urgency,
            "time_submitted": submitted,
            "deadline": deadline,
            "estimated_distance": round(distance, 2),
        })

    # Older orders are more likely to have already progressed; newest orders
    # are still pending, giving a realistic operational snapshot.
    raw_orders.sort(key=lambda order: order["time_submitted"])

    dispatched_count = round(TARGET_ORDERS * STATUS_SHARE["DISPATCHED"])
    assigned_count = round(TARGET_ORDERS * STATUS_SHARE["ASSIGNED"])

    orders = []
    rider_cursor = 0
    for position, raw in enumerate(raw_orders, start=1):
        if position <= dispatched_count:
            status = "DISPATCHED"
        elif position <= dispatched_count + assigned_count:
            status = "ASSIGNED"
        else:
            status = "PENDING"

        assigned_rider_id = ""
        if status in ("ASSIGNED", "DISPATCHED"):
            rider = active_pool[rider_cursor % len(active_pool)]
            rider_cursor += 1
            assigned_rider_id = rider["rider_id"]
            rider["availability_status"] = "BUSY"
            rider["current_location_id"] = raw["destination_location_id"]

        orders.append({
            "order_id": position,
            "restaurant_id": raw["restaurant"]["restaurant_id"],
            "customer_id": raw["customer"]["customer_id"],
            "source_location_id": raw["source_location_id"],
            "destination_location_id": raw["destination_location_id"],
            "category": raw["category"],
            "urgency": raw["urgency"],
            "time_submitted": raw["time_submitted"].strftime("%Y-%m-%dT%H:%M"),
            "deadline": raw["deadline"].strftime("%Y-%m-%dT%H:%M"),
            "status": status,
            "estimated_distance": raw["estimated_distance"],
            "assigned_rider_id": assigned_rider_id,
        })

    # Restore natural order_id sequence by submission order but renumber 1..N
    # in submission order for readability (already true from the loop above).
    return orders, active_pool + reserve_pool


# ---------------------------------------------------------------------------
# Validation
# ---------------------------------------------------------------------------

def validate(locations, roads, restaurants, customers, riders, orders) -> list[str]:
    problems: list[str] = []

    location_ids = {loc["location_id"] for loc in locations}
    if len(location_ids) != len(locations):
        problems.append("Duplicate location_id values found.")

    road_pairs = set()
    for road in roads:
        pair = (road["from_location_id"], road["to_location_id"])
        if road["from_location_id"] == road["to_location_id"]:
            problems.append(f"Road {road['road_id']} is a self-loop.")
        if pair in road_pairs:
            problems.append(f"Duplicate road pair {pair}.")
        road_pairs.add(pair)
        if road["from_location_id"] not in location_ids or road["to_location_id"] not in location_ids:
            problems.append(f"Road {road['road_id']} references a missing location.")

    # Connectivity check (treat every road as bidirectional).
    adjacency: dict[int, set[int]] = {loc_id: set() for loc_id in location_ids}
    for road in roads:
        adjacency[road["from_location_id"]].add(road["to_location_id"])
        adjacency[road["to_location_id"]].add(road["from_location_id"])
    start = next(iter(location_ids))
    visited = {start}
    frontier = [start]
    while frontier:
        current = frontier.pop()
        for neighbour in adjacency[current]:
            if neighbour not in visited:
                visited.add(neighbour)
                frontier.append(neighbour)
    if len(visited) != len(location_ids):
        problems.append(f"Location graph is not fully connected: {len(visited)}/{len(location_ids)} reachable from location {start}.")

    restaurant_ids = {r["restaurant_id"] for r in restaurants}
    customer_ids = {c["customer_id"] for c in customers}
    rider_ids = {r["rider_id"] for r in riders}

    for restaurant in restaurants:
        if restaurant["location_id"] not in location_ids:
            problems.append(f"Restaurant {restaurant['restaurant_id']} references a missing location.")
    for customer in customers:
        if customer["default_location_id"] not in location_ids:
            problems.append(f"Customer {customer['customer_id']} references a missing location.")
    for rider in riders:
        if rider["home_location_id"] not in location_ids or rider["current_location_id"] not in location_ids:
            problems.append(f"Rider {rider['rider_id']} references a missing location.")
        if rider["capacity"] <= 0:
            problems.append(f"Rider {rider['rider_id']} has non-positive capacity.")

    valid_statuses = {"PENDING", "ASSIGNED", "DISPATCHED"}
    for order in orders:
        if order["restaurant_id"] not in restaurant_ids:
            problems.append(f"Order {order['order_id']} references a missing restaurant.")
        if order["customer_id"] not in customer_ids:
            problems.append(f"Order {order['order_id']} references a missing customer.")
        if order["source_location_id"] not in location_ids or order["destination_location_id"] not in location_ids:
            problems.append(f"Order {order['order_id']} references a missing location.")
        if order["status"] not in valid_statuses:
            problems.append(f"Order {order['order_id']} has invalid status '{order['status']}'.")
        if order["status"] == "PENDING" and order["assigned_rider_id"] != "":
            problems.append(f"Order {order['order_id']} is PENDING but has an assigned rider.")
        if order["status"] in ("ASSIGNED", "DISPATCHED"):
            if order["assigned_rider_id"] == "" or order["assigned_rider_id"] not in rider_ids:
                problems.append(f"Order {order['order_id']} is {order['status']} but has no valid assigned rider.")
        if order["estimated_distance"] < 0:
            problems.append(f"Order {order['order_id']} has a negative estimated_distance.")
        submitted = datetime.strptime(order["time_submitted"], "%Y-%m-%dT%H:%M")
        deadline = datetime.strptime(order["deadline"], "%Y-%m-%dT%H:%M")
        if deadline <= submitted:
            problems.append(f"Order {order['order_id']} has a deadline not after its submission time.")

    return problems


# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------

def main() -> int:
    rng = random.Random(RANDOM_DATA_SEED)

    source_rows = load_source_locations()
    locations = pick_locations(rng, source_rows)
    roads = build_roads(rng, locations)
    restaurants = build_restaurants(rng, locations)
    customers = build_customers(rng, locations)
    riders = build_riders(rng, locations)
    orders, riders = build_orders(rng, restaurants, customers, riders)
    riders.sort(key=lambda r: r["rider_id"])

    problems = validate(locations, roads, restaurants, customers, riders, orders)

    write_csv(DATA_DIR / "locations.csv",
              ["location_id", "name", "area", "type", "latitude", "longitude"], locations)
    write_csv(DATA_DIR / "roads.csv",
              ["road_id", "from_location_id", "to_location_id", "distance_km",
               "travel_time_minutes", "road_condition_weight", "is_bidirectional"], roads)
    write_csv(DATA_DIR / "restaurants.csv",
              ["restaurant_id", "name", "location_id", "opening_time", "closing_time", "status"],
              [{k: v for k, v in r.items() if not k.startswith("_")} for r in restaurants])
    write_csv(DATA_DIR / "customers.csv",
              ["customer_id", "name", "phone", "default_location_id"],
              [{k: v for k, v in c.items() if not k.startswith("_")} for c in customers])
    write_csv(DATA_DIR / "riders.csv",
              ["rider_id", "name", "home_location_id", "vehicle_type", "capacity",
               "availability_status", "current_location_id"], riders)
    write_csv(DATA_DIR / "orders.csv",
              ["order_id", "restaurant_id", "customer_id", "source_location_id",
               "destination_location_id", "category", "urgency", "time_submitted",
               "deadline", "status", "estimated_distance", "assigned_rider_id"], orders)

    print(f"Random data seed (sum of all 17 index numbers mod 1,000,000): {RANDOM_DATA_SEED}")
    print(f"Wrote {len(locations)} locations, {len(roads)} roads, {len(restaurants)} restaurants, "
          f"{len(customers)} customers, {len(riders)} riders, {len(orders)} orders.")

    if problems:
        print(f"\nVALIDATION FAILED ({len(problems)} issue(s)):")
        for problem in problems:
            print(f" - {problem}")
        return 1

    print("Validation passed: all foreign keys resolve, no duplicate IDs/roads, "
          "location graph is fully connected, order/rider status pairing is consistent.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
