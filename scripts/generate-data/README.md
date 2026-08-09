# Generate Data

Optional helper scripts for generating fictional local datasets can be placed here.

## Locations From OpenStreetMap Landmarks

`generate_locations.py` converts OpenStreetMap-style landmark JSON into the
project's `data/locations.csv` format.

Default usage:

```bash
python scripts/generate-data/generate_locations.py
```

With no arguments, the script uses `data/landmarks.generated.json` if it already
exists. If it does not exist, the script fetches fresh landmark data from
OpenStreetMap through the Overpass API, saves that JSON cache, and writes
`data/locations.csv`.

Convert an existing `landmarks.json` file:

```bash
python scripts/generate-data/generate_locations.py --input path/to/landmarks.json
```

Fetch fresh named landmarks from OpenStreetMap through the Overpass API, cache
the raw JSON, and write `data/locations.csv`:

```bash
python scripts/generate-data/generate_locations.py --fetch-overpass
```

Useful options:

```bash
python scripts/generate-data/generate_locations.py --fetch-overpass --limit 50
python scripts/generate-data/generate_locations.py --input data/landmarks.generated.json --output data/locations.csv
python scripts/generate-data/generate_locations.py --fetch-overpass --bbox "5.6200,-0.2150,5.6750,-0.1450"
```

The default bounding box covers the University of Ghana, Legon, East Legon,
Madina and Haatso area. The script uses only Python standard-library modules.
