# SMART FOOD DELIVERY

SMART FOOD DELIVERY is a Java-based food delivery and rider dispatch optimizer for the University of Ghana DCIT 204/308 Joint Data Structures and Algorithms Semester Project.

The system models a local courier/food delivery service around Legon and nearby communities. It stores locations, roads, restaurants, customers, riders, orders, algorithm runs and audit events in SQLite, then reloads that data into custom-built data structures for scheduling, searching, sorting, route finding, optimization, testing and performance analysis.

## Technologies

- Java 17
- Maven
- SQLite
- JDBC
- JUnit 5
- Console application
- CSV seed data and CSV performance exports

## Main Features

- Manage locations, roads, restaurants, riders and food orders.
- Process normal orders using FIFO rules.
- Process urgent orders using priority-based dispatch.
- Search and sort food delivery records.
- Find shortest delivery routes using graph algorithms.
- Display reachable delivery locations.
- Generate minimum connection networks.
- Assign riders using a greedy strategy.
- Select orders using dynamic programming.
- Record algorithm experiments for performance graphs.
- Keep audit events for important actions and undo evidence.

## Project Structure

```text
docs/        Project planning, data dictionary, architecture and meeting notes
database/    SQL schema, seed files and report queries
data/        CSV templates and seed datasets
results/     Raw experiment outputs, CSVs, graphs and screenshots
scripts/     Optional data-generation and plotting helpers
src/         Java source code and tests
submission/  Final report, presentation, video and export materials
```

## Setup

Requirements:

- Java 17 or newer
- Maven 3.9 or newer

Build the project:

```bash
mvn clean package
```

Run tests:

```bash
mvn test
```

Run the console application:

```bash
mvn exec:java
```

The Maven Exec plugin is already configured in `pom.xml`. If you need to pass the main class manually in PowerShell, wrap the property in quotes:

```powershell
mvn exec:java "-Dexec.mainClass=edu.ug.smartdelivery.Main"
```

After packaging, the application can also be run from the generated JAR:

```bash
java -jar target/smart-food-delivery-optimizer-0.1.0-SNAPSHOT.jar
```

## Database

The database schema is stored in `database/schema.sql` and mirrored in `src/main/resources/database/schema.sql` for application loading. The initial implementation uses SQLite through JDBC.

Detailed local database setup instructions are in `docs/database-setup.md`.

The final dataset must include at least:

- 50 locations
- 100 road connections
- 300 food orders/service requests
- 30 riders/resources
- 30 algorithm-run records

## Final Demo Path

Use this path for the live demo, screen recording or examiner walkthrough.

Start the console app:

```bash
mvn exec:java
```

Recommended sequence:

1. `Data Setup -> Initialize Database`
2. `Data Setup -> Import Default CSV Seed Data`
3. `Data Setup -> View Database Summary`
4. `Data Setup -> Show Dataset Requirement Status`
5. `Student-ID Parameters`
6. `Browse Delivery Data -> Locations`
7. `Browse Delivery Data -> Riders`
8. `Browse Delivery Data -> Orders`
9. `Order Dispatch -> Process Orders Using FIFO`
10. `Order Dispatch -> Process Orders Using Priority`
11. `Search And Sort -> Search Order By ID`
12. `Search And Sort -> Sort Orders`
13. `Routes And Graphs -> Dijkstra Shortest Route`
14. `Routes And Graphs -> Prim Minimum Connection Network`
15. `Optimization -> Greedy Rider Assignment`
16. `Optimization -> Dynamic Programming Order Selection`
17. `Audit And Undo`
18. `Performance Lab -> Run Quick Demo Experiments`
19. `Performance Lab -> View Stored Results`
20. `Performance Lab -> Show Graph Commands`

For final report evidence, also run:

```text
Performance Lab -> Run Final Report Experiments
```

This creates report-scale CSV files:

```text
results/csv/algorithm_runs_report.csv
results/csv/algorithm_run_averages_report.csv
```

Generate final report graphs with:

```bash
python scripts/plot-results/plot_algorithm_runs.py --input results/csv/algorithm_runs_report.csv --output-dir results/graphs-report
```

The most important database-backed evidence to show during the demo:

- Dispatch changes order statuses from `PENDING` to `DISPATCHED`.
- Rider assignment changes orders to `ASSIGNED` and riders to `BUSY`.
- Audit events are recorded after dispatch and assignment actions.
- Performance runs are saved in the `algorithm_runs` table and exported as CSV.

The built-in `Guided Demo` menu can also run the setup steps quickly, but the sequence above gives more control for explaining each requirement.

## Team

Group 39: Team_5ive9ine_2.0, 17 members

### Leadership

| Role                                   | Member                               |
| -------------------------------------- | ------------------------------------ |
| Group Leader                           | Adom Bempong Franklin                |
| Technical Lead and Lead Java Developer | Dzah Solomon Sampson                 |
| Database and Dataset Lead              | Kodjoh-Kpakpassou Enam Antoine-Marie |
| Testing and Quality Assurance Lead     | Akplu Kelvin Mawuli                  |
| Documentation Lead                     | Normanyo Leslie Dela                 |
| Media and Presentation Lead            | Amaniampong Samuel Kwarteng          |
| Attendance and Minutes Officer         | Okoe Anthonia Holisede               |

### Working Teams

| Team   | Focus                                 | Members                                                                                                                                                                 |
| ------ | ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Team A | Technical Development and Integration | Dzah Solomon Sampson, Mubarack Jibriel, Tieku Henry Ebo, Otchere Ernest Atta, Osafo Kimathi Christian                                                                   |
| Team B | Database and Dataset                  | Kodjoh-Kpakpassou Enam Antoine-Marie, Normanyo Leslie Dela, Asante Emmanuella Baaba, Nyame Ebenezer, Okoe Anthonia Holisede, Amaniampong Samuel Kwarteng, Obeng Richard |
| Team C | Testing, Correctness and Performance  | Akplu Kelvin Mawuli, Ofori Richard, Adzraku Prosper Awoenam, Freeman Isaac Kweku, Amaniampong Samuel Kwarteng                                                           |
| Team D | Research, Documentation and Report    | Normanyo Leslie Dela, Asante Emmanuella Baaba, Ofori Richard, Akplu Kelvin Mawuli, Adzraku Prosper Awoenam                                                              |
| Team E | Presentation, Oral Defence and Media  | Amaniampong Samuel Kwarteng, Adzraku Prosper Awoenam, Okoe Anthonia Holisede, Adom Bempong Franklin                                                                     |

### Full Roster

|   # | Student ID | Member                               | Primary Area                                    |
| --: | ---------- | ------------------------------------ | ----------------------------------------------- |
|   1 | 22020618   | Adom Bempong Franklin                | Group leadership                                |
|   2 | 22012447   | Dzah Solomon Sampson                 | Technical development and integration           |
|   3 | 22166686   | Asante Emmanuella Baaba              | Dataset and documentation                       |
|   4 | 22146249   | Mubarack Jibriel                     | Technical development support                   |
|   5 | 22106332   | Ofori Richard                        | Testing and report support                      |
|   6 | 22042260   | Akplu Kelvin Mawuli                  | Testing and quality assurance                   |
|   7 | 22042713   | Adzraku Prosper Awoenam              | Testing, documentation and presentation support |
|   8 | 22370501   | Obeng Richard                        | Dataset support                                 |
|   9 | 22411093   | Tieku Henry Ebo                      | Dynamic programming support                     |
|  10 | 22399487   | Amaniampong Samuel Kwarteng          | Media, presentation and performance support     |
|  11 | 22262272   | Kodjoh-Kpakpassou Enam Antoine-Marie | Database and dataset                            |
|  12 | 22306912   | Otchere Ernest Atta                  | Graph traversal support                         |
|  13 | 22308781   | Osafo Kimathi Christian              | Graph algorithm support                         |
|  14 | 22382964   | Nyame Ebenezer                       | Dataset support                                 |
|  15 | 22413798   | Freeman Isaac Kweku                  | Testing and edge cases                          |
|  16 | 22402374   | Normanyo Leslie Dela                 | Documentation and report                        |
|  17 | 22408680   | Okoe Anthonia Holisede               | Minutes, dataset support and presentation       |

Detailed contribution tracking is maintained in `docs/contribution-log.md`.

## Academic Integrity

All data structures and assessed algorithms must be implemented by the team. Built-in Java structures such as `HashMap`, `TreeMap`, `PriorityQueue`, `Stack` and `ArrayDeque` must not be used for assessed core logic.

AI assistance must be acknowledged in the final report, and every member must be able to explain and modify their assigned data structure, algorithm and contribution.
