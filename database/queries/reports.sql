-- Report and validation queries for SMART FOOD DELIVERY.
--
-- These are reference queries for the technical report, database evidence
-- screenshots and oral defence — the console app does not run them. Use the
-- sqlite3 CLI against database/smart_delivery.db, e.g.:
--   sqlite3 database/smart_delivery.db < database/queries/reports.sql

-- ============================================================
-- Dataset shape (row counts vs brief minimums)
-- ============================================================

SELECT 'locations' AS entity, COUNT(*) AS row_count FROM locations
UNION ALL SELECT 'roads', COUNT(*) FROM roads
UNION ALL SELECT 'restaurants', COUNT(*) FROM restaurants
UNION ALL SELECT 'customers', COUNT(*) FROM customers
UNION ALL SELECT 'riders', COUNT(*) FROM riders
UNION ALL SELECT 'orders', COUNT(*) FROM orders
UNION ALL SELECT 'algorithm_runs', COUNT(*) FROM algorithm_runs
UNION ALL SELECT 'audit_events', COUNT(*) FROM audit_events;

-- ============================================================
-- Orders
-- ============================================================

-- Order status distribution (PENDING/ASSIGNED/DISPATCHED counts).
SELECT status, COUNT(*) AS order_count
FROM orders
GROUP BY status
ORDER BY order_count DESC;

-- Urgency distribution (1 = low, 5 = urgent).
SELECT urgency, COUNT(*) AS order_count
FROM orders
GROUP BY urgency
ORDER BY urgency;

-- Orders per restaurant, busiest first.
SELECT r.restaurant_id, r.name, COUNT(o.order_id) AS order_count
FROM restaurants r
LEFT JOIN orders o ON o.restaurant_id = r.restaurant_id
GROUP BY r.restaurant_id, r.name
ORDER BY order_count DESC;

-- Average estimated delivery distance per order status.
SELECT status, ROUND(AVG(estimated_distance), 2) AS avg_distance_km, COUNT(*) AS order_count
FROM orders
GROUP BY status
ORDER BY avg_distance_km DESC;

-- Orders whose deadline has already passed relative to submission time
-- (sanity check: should always be empty, since deadline > time_submitted
-- is enforced at generation time and validated separately).
SELECT order_id, time_submitted, deadline
FROM orders
WHERE deadline <= time_submitted;

-- ============================================================
-- Riders
-- ============================================================

-- Rider availability snapshot.
SELECT availability_status, COUNT(*) AS rider_count
FROM riders
GROUP BY availability_status;

-- Vehicle type mix.
SELECT vehicle_type, COUNT(*) AS rider_count, ROUND(AVG(capacity), 2) AS avg_capacity
FROM riders
GROUP BY vehicle_type
ORDER BY rider_count DESC;

-- Busiest riders by number of currently ASSIGNED/DISPATCHED orders.
SELECT r.rider_id, r.name, COUNT(o.order_id) AS active_orders
FROM riders r
JOIN orders o ON o.assigned_rider_id = r.rider_id
WHERE o.status IN ('ASSIGNED', 'DISPATCHED')
GROUP BY r.rider_id, r.name
ORDER BY active_orders DESC;

-- ============================================================
-- Locations and roads
-- ============================================================

-- Location type mix.
SELECT type, COUNT(*) AS location_count
FROM locations
GROUP BY type
ORDER BY location_count DESC;

-- Road network summary: distance and travel-time ranges.
SELECT
    COUNT(*) AS road_count,
    ROUND(MIN(distance_km), 2) AS min_distance_km,
    ROUND(MAX(distance_km), 2) AS max_distance_km,
    ROUND(AVG(distance_km), 2) AS avg_distance_km,
    MIN(travel_time_minutes) AS min_travel_time,
    MAX(travel_time_minutes) AS max_travel_time
FROM roads;

-- Road degree per location (how many roads touch each location), lowest
-- first — useful for spotting weakly connected nodes before running BFS/DFS.
SELECT l.location_id, l.name, COUNT(r.road_id) AS road_count
FROM locations l
LEFT JOIN roads r ON r.from_location_id = l.location_id OR r.to_location_id = l.location_id
GROUP BY l.location_id, l.name
ORDER BY road_count ASC
LIMIT 10;

-- ============================================================
-- Referential integrity / duplicate checks
-- (companion to scripts/generate-data/generate_dataset.py::validate)
-- ============================================================

-- Duplicate road pairs (should be empty).
SELECT from_location_id, to_location_id, COUNT(*) AS occurrences
FROM roads
GROUP BY from_location_id, to_location_id
HAVING COUNT(*) > 1;

-- Orders referencing a restaurant/customer/location/rider that does not
-- exist (should be empty; PRAGMA foreign_keys = ON already prevents this
-- at insert time, but useful as an explicit report query).
SELECT o.order_id
FROM orders o
LEFT JOIN restaurants r ON r.restaurant_id = o.restaurant_id
LEFT JOIN customers c ON c.customer_id = o.customer_id
LEFT JOIN locations src ON src.location_id = o.source_location_id
LEFT JOIN locations dst ON dst.location_id = o.destination_location_id
WHERE r.restaurant_id IS NULL
   OR c.customer_id IS NULL
   OR src.location_id IS NULL
   OR dst.location_id IS NULL;

-- PENDING orders that still carry an assigned rider, or ASSIGNED/DISPATCHED
-- orders with no rider — both should be empty.
SELECT order_id, status, assigned_rider_id
FROM orders
WHERE (status = 'PENDING' AND assigned_rider_id IS NOT NULL)
   OR (status IN ('ASSIGNED', 'DISPATCHED') AND assigned_rider_id IS NULL);

-- ============================================================
-- Audit trail
-- ============================================================

-- Most recent audit events (dispatch/assignment history), newest first.
SELECT event_id, event_type, entity_type, entity_id, previous_value, new_value, event_time
FROM audit_events
ORDER BY event_time DESC
LIMIT 20;
