# Data Dictionary

This document will be expanded by the Database and Dataset teams.

## locations

Stores local delivery network nodes.

| Field | Meaning |
| --- | --- |
| location_id | Unique location identifier |
| name | Location name |
| area | Broad area or community |
| type | Hostel, restaurant area, customer zone, junction, dispatch point, etc. |
| latitude | Estimated latitude |
| longitude | Estimated longitude |

## roads

Stores weighted road edges.

| Field | Meaning |
| --- | --- |
| road_id | Unique road identifier |
| from_location_id | Starting location |
| to_location_id | Ending location |
| distance_km | Estimated road distance |
| travel_time_minutes | Estimated travel time |
| road_condition_weight | Weight for traffic or poor road condition |
| is_bidirectional | 1 if road can be used both ways |

## orders

Stores food delivery service requests.

| Field | Meaning |
| --- | --- |
| order_id | Unique order identifier |
| restaurant_id | Restaurant/vendor fulfilling the order |
| customer_id | Fictional customer placing the order |
| source_location_id | Pickup location |
| destination_location_id | Delivery location |
| category | Food or order category |
| urgency | Priority score |
| time_submitted | Order submission time |
| deadline | Delivery deadline |
| status | Pending, assigned, delivered, cancelled, etc. |
| estimated_distance | Estimated delivery distance |
| assigned_rider_id | Rider assigned to the order, if any |

`status` is constrained to `PENDING`, `ASSIGNED` or `DISPATCHED` (see `database/schema.sql`). `DELIVERED`/`CANCELLED` are not yet supported by the application or schema — see the open decision in `docs/meetings/meeting-05.md`, section 3.3.

## restaurants

Stores food vendors that fulfil orders.

| Field | Meaning |
| --- | --- |
| restaurant_id | Unique restaurant identifier |
| name | Restaurant/vendor name |
| location_id | Location where the restaurant is based (pickup point for its orders) |
| opening_time | Daily opening time (`HH:MM`) |
| closing_time | Daily closing time (`HH:MM`) |
| status | `OPEN` or `CLOSED` |

## customers

Stores fictional customers placing orders. No real personal data is used.

| Field | Meaning |
| --- | --- |
| customer_id | Unique customer identifier |
| name | Fictional customer name |
| phone | Fictional phone number (sequential placeholder pattern, not a real contact) |
| default_location_id | Customer's usual delivery location |

## riders

Stores delivery riders (the project brief's "resources").

| Field | Meaning |
| --- | --- |
| rider_id | Unique rider identifier |
| name | Fictional rider name |
| home_location_id | Rider's home/dispatch base location |
| vehicle_type | `Motorbike`, `Bicycle` or `Tricycle` |
| capacity | Number of concurrent orders the rider can carry (must be positive) |
| availability_status | `AVAILABLE` or `BUSY` |
| current_location_id | Rider's last known location |

## algorithm_runs

Stores one row per timed algorithm experiment trial, used for the performance analysis report (see `docs/performance-plan.md`).

| Field | Meaning |
| --- | --- |
| run_id | Unique run identifier |
| algorithm_name | Algorithm under test (e.g. `linear_search`, `binary_search`, `merge_sort`) |
| input_size | Number of records/elements used in the trial |
| execution_time_ns | Measured execution time in nanoseconds |
| memory_kb | Measured memory usage in kilobytes |
| trial_number | Trial index for repeated runs at the same input size (must be positive) |
| date_run | Date the trial was recorded |

## audit_events

Stores an append-only log of state-changing actions (dispatch, assignment) for the console app's audit/undo evidence.

| Field | Meaning |
| --- | --- |
| event_id | Unique event identifier |
| event_type | Action recorded (e.g. `ORDER_DISPATCHED`, `ORDER_ASSIGNED`, `RIDER_STATUS_UPDATED`) |
| entity_type | Table the event relates to (e.g. `orders`, `riders`) |
| entity_id | Primary key of the affected row |
| previous_value | Snapshot of the relevant fields before the change |
| new_value | Snapshot of the relevant fields after the change |
| event_time | Timestamp the event was recorded |
