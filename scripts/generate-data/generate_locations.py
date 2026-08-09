#!/usr/bin/env python3
"""
Generate data/locations.csv from OpenStreetMap-style landmark JSON.

The script supports two workflows:
1. Convert an existing landmarks.json file like the old UGNavigate format.
2. Fetch named landmarks from OpenStreetMap through the Overpass API, cache the
   raw response, then convert it to the project CSV format.

No third-party Python packages are required.
"""

from __future__ import annotations

import argparse
import csv
import json
import sys
import urllib.parse
import urllib.request
from pathlib import Path
from typing import Any


DEFAULT_BBOX = "5.6200,-0.2150,5.6750,-0.1450"
OVERPASS_URL = "https://overpass-api.de/api/interpreter"
USER_AGENT = "smart-food-delivery-optimizer/1.0 (student academic project)"


TYPE_BY_TAG_VALUE = {
    "fast_food": "Restaurant Area",
    "restaurant": "Restaurant",
    "cafe": "Restaurant",
    "food_court": "Restaurant Area",
    "school": "Academic",
    "university": "Academic",
    "college": "Academic",
    "library": "Academic",
    "bus_stop": "Transport Stop",
    "station": "Transport Stop",
    "parking": "Transport Stop",
    "hospital": "Health",
    "clinic": "Health",
    "pharmacy": "Health",
    "bank": "Service",
    "atm": "Service",
    "marketplace": "Market",
    "supermarket": "Market",
    "convenience": "Market",
    "hostel": "Hostel",
    "dormitory": "Hostel",
    "residential": "Residential Area",
    "sports_centre": "Recreation",
}


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Generate SMART FOOD DELIVERY locations.csv from OSM landmark data."
    )
    parser.add_argument(
        "--input",
        type=Path,
        help="Existing landmarks JSON file to convert.",
    )
    parser.add_argument(
        "--output",
        type=Path,
        default=Path("data/locations.csv"),
        help="CSV output path. Default: data/locations.csv",
    )
    parser.add_argument(
        "--fetch-overpass",
        action="store_true",
        help="Fetch landmarks from Overpass API before writing CSV.",
    )
    parser.add_argument(
        "--bbox",
        default=DEFAULT_BBOX,
        help="Bounding box as south,west,north,east. Default covers UG/Legon area.",
    )
    parser.add_argument(
        "--cache-json",
        type=Path,
        default=Path("data/landmarks.generated.json"),
        help="Path for fetched raw landmark cache. Default: data/landmarks.generated.json",
    )
    parser.add_argument(
        "--area",
        default="Legon Area",
        help="Fallback area name when coordinates do not match a known zone.",
    )
    parser.add_argument(
        "--limit",
        type=int,
        default=0,
        help="Maximum number of rows to write. 0 means all valid landmarks.",
    )
    return parser.parse_args()


def build_overpass_query(bbox: str) -> str:
    return f"""
[out:json][timeout:60];
(
  node["name"]["amenity"]({bbox});
  way["name"]["amenity"]({bbox});
  relation["name"]["amenity"]({bbox});
  node["name"]["shop"]({bbox});
  way["name"]["shop"]({bbox});
  relation["name"]["shop"]({bbox});
  node["name"]["tourism"]({bbox});
  way["name"]["tourism"]({bbox});
  relation["name"]["tourism"]({bbox});
  node["name"]["highway"="bus_stop"]({bbox});
  node["name"]["public_transport"]({bbox});
  node["name"]["building"]({bbox});
  way["name"]["building"]({bbox});
  relation["name"]["building"]({bbox});
  node["name"]["leisure"]({bbox});
  way["name"]["leisure"]({bbox});
  relation["name"]["leisure"]({bbox});
);
out center tags;
""".strip()


def fetch_overpass_landmarks(bbox: str) -> dict[str, Any]:
    query = build_overpass_query(bbox).encode("utf-8")
    request = urllib.request.Request(
        OVERPASS_URL,
        data=urllib.parse.urlencode({"data": query.decode("utf-8")}).encode("utf-8"),
        headers={
            "Content-Type": "application/x-www-form-urlencoded",
            "User-Agent": USER_AGENT,
        },
        method="POST",
    )

    with urllib.request.urlopen(request, timeout=90) as response:
        return json.loads(response.read().decode("utf-8"))


def load_landmarks(path: Path) -> list[dict[str, Any]]:
    raw = json.loads(path.read_text(encoding="utf-8"))

    if isinstance(raw, dict) and "elements" in raw:
        return [normalise_overpass_element(item) for item in raw["elements"]]

    if isinstance(raw, dict):
        return [normalise_named_landmark(value, key) for key, value in raw.items()]

    if isinstance(raw, list):
        return [normalise_named_landmark(value, None) for value in raw]

    raise ValueError("Unsupported JSON format. Expected object, list, or Overpass response.")


def normalise_overpass_element(element: dict[str, Any]) -> dict[str, Any]:
    tags = element.get("tags") or {}
    center = element.get("center") or {}
    return {
        "name": tags.get("name") or element.get("name"),
        "lat": element.get("lat", center.get("lat")),
        "lon": element.get("lon", center.get("lon")),
        "id": element.get("id"),
        "osm_type": element.get("type"),
        "tags": tags,
    }


def normalise_named_landmark(item: Any, fallback_name: str | None) -> dict[str, Any]:
    if not isinstance(item, dict):
        raise ValueError(f"Invalid landmark entry for {fallback_name!r}.")

    tags = item.get("tags") or {}
    return {
        "name": item.get("name") or tags.get("name") or fallback_name,
        "lat": item.get("lat") or item.get("latitude"),
        "lon": item.get("lon") or item.get("longitude"),
        "id": item.get("id") or item.get("osm_id"),
        "osm_type": item.get("osm_type") or item.get("type"),
        "tags": tags,
    }


def infer_type(tags: dict[str, Any]) -> str:
    for key in ("amenity", "shop", "tourism", "highway", "public_transport", "building", "leisure"):
        value = tags.get(key)
        if value in TYPE_BY_TAG_VALUE:
            return TYPE_BY_TAG_VALUE[value]

    if tags.get("name"):
        return "Landmark"

    return "Other"


def infer_area(lat: float, lon: float, fallback: str) -> str:
    if 5.628 <= lat <= 5.661 and -0.202 <= lon <= -0.176:
        return "University of Ghana"
    if 5.635 <= lat <= 5.663 and -0.176 < lon <= -0.140:
        return "East Legon"
    if 5.655 <= lat <= 5.690 and -0.180 <= lon <= -0.135:
        return "Madina"
    if 5.660 <= lat <= 5.690 and -0.230 <= lon <= -0.190:
        return "Haatso"
    return fallback


def build_rows(landmarks: list[dict[str, Any]], fallback_area: str, limit: int) -> list[dict[str, Any]]:
    rows = []
    seen_names: set[str] = set()

    for landmark in sorted(landmarks, key=lambda item: str(item.get("name") or "").casefold()):
        name = clean_text(landmark.get("name"))
        lat = to_float(landmark.get("lat"))
        lon = to_float(landmark.get("lon"))

        if not name or lat is None or lon is None:
            continue

        dedupe_key = name.casefold()
        if dedupe_key in seen_names:
            continue
        seen_names.add(dedupe_key)

        tags = landmark.get("tags") or {}
        rows.append(
            {
                "location_id": len(rows) + 1,
                "name": name,
                "area": infer_area(lat, lon, fallback_area),
                "type": infer_type(tags),
                "latitude": f"{lat:.7f}",
                "longitude": f"{lon:.7f}",
            }
        )

        if limit > 0 and len(rows) >= limit:
            break

    return rows


def clean_text(value: Any) -> str:
    if value is None:
        return ""
    return " ".join(str(value).strip().split())


def to_float(value: Any) -> float | None:
    try:
        return float(value)
    except (TypeError, ValueError):
        return None


def write_csv(path: Path, rows: list[dict[str, Any]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    fieldnames = ["location_id", "name", "area", "type", "latitude", "longitude"]
    with path.open("w", encoding="utf-8", newline="") as file:
        writer = csv.DictWriter(file, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)


def main() -> int:
    args = parse_args()

    input_path = args.input
    should_fetch = args.fetch_overpass

    if input_path is None and not args.cache_json.exists():
        should_fetch = True

    if should_fetch:
        print(f"Fetching OSM landmarks for bbox {args.bbox}...")
        raw = fetch_overpass_landmarks(args.bbox)
        args.cache_json.parent.mkdir(parents=True, exist_ok=True)
        args.cache_json.write_text(json.dumps(raw, indent=2), encoding="utf-8")
        input_path = args.cache_json
        print(f"Saved raw cache to {args.cache_json}")

    if input_path is None and args.cache_json.exists():
        input_path = args.cache_json
        print(f"Using cached landmarks from {input_path}")

    if input_path is None:
        print("No input JSON or cache file available.", file=sys.stderr)
        return 2

    landmarks = load_landmarks(input_path)
    rows = build_rows(landmarks, args.area, args.limit)

    if not rows:
        print("No valid landmarks found. Check the input JSON format.", file=sys.stderr)
        return 1

    write_csv(args.output, rows)
    print(f"Wrote {len(rows)} locations to {args.output}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
