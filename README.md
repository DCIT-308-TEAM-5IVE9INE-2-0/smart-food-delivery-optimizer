# SMART FOOD DELIVERY

SMART FOOD DELIVERY is a Java-based food delivery and rider dispatch optimizer for the University of Ghana DCIT 204/308 Joint Data Structures and Algorithms Semester Project.

The system models food delivery operations around Legon and nearby communities. It stores delivery data in SQLite, reloads it into custom-built data structures, and uses those structures to support dispatching, searching, sorting, route finding, optimization, testing and performance analysis.

## Technologies

- Java 17
- Maven
- SQLite and JDBC
- JUnit 5
- Console application
- CSV seed data and CSV performance exports

## Core Features

- Manage locations, roads, restaurants, customers, riders and orders.
- Process normal orders using FIFO dispatch.
- Process urgent orders using priority-based dispatch.
- Search and sort delivery records.
- Find shortest routes and reachable delivery locations using graph algorithms.
- Generate minimum connection networks.
- Assign riders using a greedy strategy.
- Select order combinations using dynamic programming.
- Record audit events and algorithm performance runs.

## Quick Start

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

## Database Setup

The database schema is stored in `database/schema.sql` and mirrored in `src/main/resources/database/schema.sql` for application loading.

For local setup, use the console app:

1. `Data Setup -> Initialize Database`
2. `Data Setup -> Import Default CSV Seed Data`
3. `Data Setup -> View Database Summary`
4. `Data Setup -> Show Dataset Requirement Status`

Detailed database instructions are in [docs/database-setup.md](docs/database-setup.md).

## Demo Guide

For the live demo, screen recording or examiner walkthrough, follow [docs/final-console-smoke-test.md](docs/final-console-smoke-test.md).

Recommended high-level demo order:

1. Initialize and import the database.
2. Show dataset requirement status.
3. Show student-ID driven parameters.
4. Browse locations, riders and orders.
5. Run FIFO and priority dispatch.
6. Run search, sort, graph, greedy and dynamic programming features.
7. Show audit events and persisted performance results.

Performance run and graph instructions are in [docs/performance-plan.md](docs/performance-plan.md) and [scripts/plot-results/README.md](scripts/plot-results/README.md).

## Documentation Guide

| Document | Purpose |
| --- | --- |
| [docs/project-overview.md](docs/project-overview.md) | Project summary and intended system behaviour |
| [docs/problem-statement.md](docs/problem-statement.md) | Problem being solved and project motivation |
| [docs/system-scope.md](docs/system-scope.md) | System boundaries and accepted feature scope |
| [docs/architecture.md](docs/architecture.md) | Application structure and major components |
| [docs/data-structures.md](docs/data-structures.md) | Custom data structures used in the project |
| [docs/algorithms.md](docs/algorithms.md) | Algorithms and how they support delivery operations |
| [docs/database-setup.md](docs/database-setup.md) | Local database setup and reset instructions |
| [docs/data-dictionary.md](docs/data-dictionary.md) | Dataset fields and meanings |
| [docs/index-number-parameters.md](docs/index-number-parameters.md) | How student IDs are used as project parameters |
| [docs/final-console-smoke-test.md](docs/final-console-smoke-test.md) | Final live-demo checklist |
| [docs/final-trace-evidence.md](docs/final-trace-evidence.md) | Requirement trace and evidence checklist |
| [docs/performance-plan.md](docs/performance-plan.md) | Performance testing plan and graph workflow |
| [docs/final-submission-checklist.md](docs/final-submission-checklist.md) | Final submission checklist for the group |
| [docs/team-structure.md](docs/team-structure.md) | Full team structure and assigned groups |
| [docs/contribution-log.md](docs/contribution-log.md) | Contribution tracking and member evidence |

Meeting records are kept in [docs/meetings](docs/meetings).

## Dataset Minimums

The final dataset must include at least:

- 50 locations
- 100 road connections
- 300 food orders or service requests
- 30 riders or delivery resources
- 30 algorithm-run records

Dataset evidence is tracked in [docs/dataset-evidence.md](docs/dataset-evidence.md) and [docs/dataset-validation-report.md](docs/dataset-validation-report.md).

## Project Structure

```text
docs/        Project planning, architecture, evidence and meeting notes
database/    SQL schema, seed files and report queries
data/        CSV templates and seed datasets
results/     Raw experiment outputs, CSVs, graphs and screenshots
scripts/     Data-generation and plotting helpers
src/         Java source code and tests
submission/  Final report, presentation, video and export materials
```

## Team

Group 39: Team_5ive9ine_2.0, 17 members

Technical Lead and Lead Java Developer: Dzah Solomon Sampson

### Full Team List

| # | Student ID | Full Name | Role / Course |
| ---: | --- | --- | --- |
| 1 | 22020618 | Adom Bempong Franklin | Group Leader, DCIT 308 |
| 2 | 22012447 | Dzah Solomon Sampson | Technical Lead and Lead Java Developer, DCIT 308 |
| 3 | 22166686 | Asante Emmanuella Baaba | Member |
| 4 | 22146249 | Mubarack Jibriel | Member, DCIT 308 |
| 5 | 22106332 | Ofori Richard | Member, DCIT 308 |
| 6 | 22042260 | Akplu Kelvin Mawuli | Member, DCIT 308 |
| 7 | 22042713 | Adzraku Prosper Awoenam | Member, DCIT 308 |
| 8 | 22370501 | Obeng Richard | DCIT 204 |
| 9 | 22411093 | Tieku Henry Ebo | DCIT 204 |
| 10 | 22399487 | Amaniampong Samuel Kwarteng | DCIT 204 |
| 11 | 22262272 | Kodjoh-Kpakpassou Enam Antoine-Marie | DCIT 204 |
| 12 | 22306912 | Otchere Ernest Atta | DCIT 204 |
| 13 | 22308781 | Osafo Kimathi Christian | DCIT 204 |
| 14 | 22382964 | Nyame Ebenezer | DCIT 204 |
| 15 | 22413798 | Freeman Isaac Kweku | DCIT 204 |
| 16 | 22402374 | Normanyo Leslie Dela | DCIT 204 |
| 17 | 22408680 | Okoe Anthonia Holisede | DCIT 204 |

The working-team structure and role assignments are maintained in [docs/team-structure.md](docs/team-structure.md). Contribution evidence is tracked in [docs/contribution-log.md](docs/contribution-log.md).

## Academic Integrity

All assessed data structures and algorithms must be implemented by the team. Built-in Java structures such as `HashMap`, `TreeMap`, `PriorityQueue`, `Stack` and `ArrayDeque` must not be used for assessed core logic.

AI assistance must be acknowledged in the final report, and every member must be able to explain and modify their assigned data structure, algorithm and contribution.
