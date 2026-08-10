PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS locations (
    location_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    area TEXT NOT NULL,
    type TEXT NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS roads (
    road_id INTEGER PRIMARY KEY,
    from_location_id INTEGER NOT NULL,
    to_location_id INTEGER NOT NULL,
    distance_km REAL NOT NULL CHECK (distance_km >= 0),
    travel_time_minutes INTEGER NOT NULL CHECK (travel_time_minutes >= 0),
    road_condition_weight REAL NOT NULL CHECK (road_condition_weight >= 0),
    is_bidirectional INTEGER NOT NULL DEFAULT 1 CHECK (is_bidirectional IN (0, 1)),
    FOREIGN KEY (from_location_id) REFERENCES locations(location_id),
    FOREIGN KEY (to_location_id) REFERENCES locations(location_id)
);

CREATE TABLE IF NOT EXISTS restaurants (
    restaurant_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    location_id INTEGER NOT NULL,
    opening_time TEXT NOT NULL,
    closing_time TEXT NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('OPEN', 'CLOSED')),
    FOREIGN KEY (location_id) REFERENCES locations(location_id)
);

CREATE TABLE IF NOT EXISTS customers (
    customer_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    default_location_id INTEGER NOT NULL,
    FOREIGN KEY (default_location_id) REFERENCES locations(location_id)
);

CREATE TABLE IF NOT EXISTS riders (
    rider_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    home_location_id INTEGER NOT NULL,
    vehicle_type TEXT NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    availability_status TEXT NOT NULL CHECK (availability_status IN ('AVAILABLE', 'BUSY')),
    current_location_id INTEGER NOT NULL,
    FOREIGN KEY (home_location_id) REFERENCES locations(location_id),
    FOREIGN KEY (current_location_id) REFERENCES locations(location_id)
);

CREATE TABLE IF NOT EXISTS orders (
    order_id INTEGER PRIMARY KEY,
    restaurant_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    source_location_id INTEGER NOT NULL,
    destination_location_id INTEGER NOT NULL,
    category TEXT NOT NULL,
    urgency INTEGER NOT NULL CHECK (urgency >= 0),
    time_submitted TEXT NOT NULL,
    deadline TEXT NOT NULL,
    -- DELIVERED/CANCELLED are intentionally not yet allowed here. Add them once
    -- Meeting 5 agenda item 4.5 decides whether those transitions are needed.
    status TEXT NOT NULL CHECK (status IN ('PENDING', 'ASSIGNED', 'DISPATCHED')),
    estimated_distance REAL NOT NULL CHECK (estimated_distance >= 0),
    assigned_rider_id INTEGER,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (source_location_id) REFERENCES locations(location_id),
    FOREIGN KEY (destination_location_id) REFERENCES locations(location_id),
    FOREIGN KEY (assigned_rider_id) REFERENCES riders(rider_id)
);

CREATE TABLE IF NOT EXISTS algorithm_runs (
    run_id INTEGER PRIMARY KEY,
    algorithm_name TEXT NOT NULL,
    input_size INTEGER NOT NULL CHECK (input_size >= 0),
    execution_time_ns INTEGER NOT NULL CHECK (execution_time_ns >= 0),
    memory_kb REAL NOT NULL CHECK (memory_kb >= 0),
    trial_number INTEGER NOT NULL CHECK (trial_number > 0),
    date_run TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS audit_events (
    event_id INTEGER PRIMARY KEY,
    event_type TEXT NOT NULL,
    entity_type TEXT NOT NULL,
    entity_id INTEGER NOT NULL,
    previous_value TEXT,
    new_value TEXT,
    event_time TEXT NOT NULL
);
