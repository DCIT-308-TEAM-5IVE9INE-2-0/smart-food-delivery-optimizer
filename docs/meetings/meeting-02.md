# Meeting 02

Joint Semester Project Kick-Off Plan

Group: Group 39, Team_5ive9ine_2.0

Official project title: SMART FOOD DELIVERY

Document status: cleaned Markdown version migrated from the original meeting agenda PDF.

## Meeting Details

Date: __________________________

Time: __________________________

Venue / Platform: __________________________

Chairperson: Adom Bempong Franklin

Technical Lead: Dzah Solomon Sampson

Minutes Recorded By: __________________________

## Purpose

Meeting 2 converted the initial idea from Meeting 1 into a working project direction. The meeting confirmed the project title, technical baseline, working teams, expected deliverables and first development tasks.

## Confirmed Direction

| Area | Decision |
| --- | --- |
| Domain | Courier / food delivery service |
| Official title | SMART FOOD DELIVERY |
| Repository | `smart-food-delivery-optimizer` |
| Main language | Java |
| Build tool | Maven |
| Database | SQLite |
| Database access | JDBC |
| Testing | JUnit 5 |
| Interface | Interactive console application |
| Performance graphs | Python or Excel |

## Project Summary

SMART FOOD DELIVERY models a food delivery operation around the University of Ghana, Legon and nearby communities. The system stores locations, roads, restaurants, customers, riders and orders in a database, then uses custom data structures and algorithms to dispatch orders, assign riders, find routes, search/sort records and generate performance evidence.

## Working Teams

### Team A: Technical Development And Integration

Lead: Dzah Solomon Sampson

Focus:

- Java project setup.
- Custom data structures.
- Algorithm implementation.
- Console interface.
- GitHub integration.
- Technical support for other teams.

Members:

- Dzah Solomon Sampson
- Mubarack Jibriel
- Tieku Henry Ebo
- Otchere Ernest Atta
- Osafo Kimathi Christian

### Team B: Database And Dataset

Lead: Kodjoh-Kpakpassou Enam Antoine-Marie

Focus:

- SQLite schema review.
- CSV templates and seed data.
- Locations, roads, restaurants, customers, riders and orders.
- Data validation.
- Database screenshots and evidence.

Members:

- Kodjoh-Kpakpassou Enam Antoine-Marie
- Normanyo Leslie Dela
- Asante Emmanuella Baaba
- Nyame Ebenezer
- Okoe Anthonia Holisede
- Amaniampong Samuel Kwarteng
- Obeng Richard

### Team C: Testing, Correctness And Performance

Lead: Akplu Kelvin Mawuli

Focus:

- Unit test planning.
- Normal, boundary and invalid test cases.
- Trace tables.
- Correctness evidence.
- Runtime experiments.
- CSV results and graphs.

Members:

- Akplu Kelvin Mawuli
- Ofori Richard
- Adzraku Prosper Awoenam
- Freeman Isaac Kweku
- Amaniampong Samuel Kwarteng

### Team D: Research, Documentation And Report

Lead: Normanyo Leslie Dela

Focus:

- Problem statement.
- Ghanaian context description.
- System assumptions and boundaries.
- Data dictionary.
- Architecture notes.
- Pseudocode and flowcharts.
- Development log and report sections.

Members:

- Normanyo Leslie Dela
- Asante Emmanuella Baaba
- Ofori Richard
- Akplu Kelvin Mawuli
- Adzraku Prosper Awoenam

### Team E: Presentation, Oral Defence And Media

Lead: Amaniampong Samuel Kwarteng

Focus:

- Demonstration-video plan.
- Presentation structure.
- Oral-defence preparation.
- Screenshots and media evidence.

Members:

- Amaniampong Samuel Kwarteng
- Adzraku Prosper Awoenam
- Okoe Anthonia Holisede
- Adom Bempong Franklin

## Required Deliverables

- Working Java console system.
- Custom data-structure implementations.
- Search, sort, graph, greedy and dynamic-programming algorithms.
- SQLite database schema and CSV seed data.
- Database import and update workflow.
- Unit tests and edge-case tests.
- Trace tables and proof sketches.
- Performance CSV files and graphs.
- Technical report.
- Presentation and demo video.
- Individual oral-defence preparation.

## Initial Action Items

| Task | Owner / Team | Evidence |
| --- | --- | --- |
| Create GitHub repository | Technical Lead | Repository link |
| Set up Java/Maven project | Technical Lead | Project scaffold |
| Draft database schema | Database Team with Technical Lead | `database/schema.sql` |
| Prepare seed data plan | Dataset Team | CSV headings and sample records |
| Draft project overview/problem statement | Documentation Team | `/docs` drafts |
| Prepare testing plan | Testing Team | `docs/testing-plan.md` |
| Prepare demo/presentation outline | Presentation Team | Submission notes |
| Confirm individual oral-defence topics | All members | Team structure document |

## Decisions Carried Forward

- The project will remain a Java console application because the lecturer's marks focus on data structures, algorithms, testing, database integration and performance evidence.
- SQLite will be used because it is portable and simple for every team member to run.
- CSV files will seed the database, but the final app must read from and write to the database during execution.
- All assessed data structures must be implemented by the team rather than replaced by Java built-in collections.
- The project will use Ghanaian food delivery data and local place names.
- At least three algorithm parameters will be derived from member student IDs.

## Follow-Up For Meeting 3

- Confirm GitHub usernames and repository access.
- Review the first technical scaffold.
- Confirm team/member alignment.
- Review database schema and CSV headings.
- Assign concrete dataset, testing, documentation and presentation tasks.
