-- Performance experiment queries for SMART FOOD DELIVERY.
--
-- These read the algorithm_runs table populated by the console app's
-- Performance Lab (option 7) / ExperimentService, and back the performance
-- analysis section of the technical report. Not run by the app itself.
--   sqlite3 database/smart_delivery.db < database/queries/experiments.sql

-- ============================================================
-- Coverage check: every algorithm/input_size combination should have
-- at least 3 trials, per docs/performance-plan.md.
-- ============================================================

SELECT algorithm_name, input_size, COUNT(*) AS trial_count
FROM algorithm_runs
GROUP BY algorithm_name, input_size
HAVING COUNT(*) < 3
ORDER BY algorithm_name, input_size;

-- ============================================================
-- Average runtime and memory per algorithm/input_size
-- (the numbers that go into the report's performance tables/graphs).
-- ============================================================

SELECT
    algorithm_name,
    input_size,
    COUNT(*) AS trial_count,
    ROUND(AVG(execution_time_ns), 2) AS avg_execution_time_ns,
    MIN(execution_time_ns) AS min_execution_time_ns,
    MAX(execution_time_ns) AS max_execution_time_ns,
    ROUND(AVG(memory_kb), 2) AS avg_memory_kb
FROM algorithm_runs
GROUP BY algorithm_name, input_size
ORDER BY algorithm_name, input_size;

-- ============================================================
-- Per-algorithm summary across all input sizes tested so far.
-- ============================================================

SELECT
    algorithm_name,
    COUNT(DISTINCT input_size) AS input_sizes_tested,
    COUNT(*) AS total_trials,
    ROUND(AVG(execution_time_ns), 2) AS avg_execution_time_ns
FROM algorithm_runs
GROUP BY algorithm_name
ORDER BY algorithm_name;

-- ============================================================
-- Growth trend: average execution time as input_size increases, per
-- algorithm — the shape of this (linear/log/quadratic-looking) is what
-- the report's complexity discussion should point back to.
-- ============================================================

SELECT algorithm_name, input_size, ROUND(AVG(execution_time_ns), 2) AS avg_execution_time_ns
FROM algorithm_runs
GROUP BY algorithm_name, input_size
ORDER BY algorithm_name, input_size;

-- ============================================================
-- Most recent run per algorithm (sanity check after a fresh Performance
-- Lab run: confirms new rows actually landed).
-- ============================================================

SELECT algorithm_name, MAX(date_run) AS latest_run_date, COUNT(*) AS total_trials
FROM algorithm_runs
GROUP BY algorithm_name
ORDER BY latest_run_date DESC;
