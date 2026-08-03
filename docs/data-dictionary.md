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
