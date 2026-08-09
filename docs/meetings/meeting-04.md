# Meeting 04

DCIT 204/308 Joint Data Structures and Algorithms Semester Project

Meeting title: TA Clarification and Dataset Alignment Mini Meeting

Official project title: SMART FOOD DELIVERY

Group: Group 39, Team_5ive9ine_2.0

Confirmed group size: 17 members

Meeting type: Mini Technical Clarification Meeting

## Meeting Details

Date: 4 August 2026

Time: 8:00 PM

Venue / Platform: Google Meet

Invited Teaching Assistant: Joseph Acheampong

Chairperson: Adom Bempong Franklin

Technical and Development Lead: Dzah Solomon Sampson

Database Team Lead: Kodjoh-Kpakpassou Enam Antoine-Marie

Minutes Recorded By: ____________________________

## Purpose

Meeting 4 is a short clarification meeting with one of the course Teaching Assistants.

The purpose is to:

- Briefly present the current SMART FOOD DELIVERY project structure.
- Confirm that the project entities properly represent the requirements in the project brief.
- Clarify database and dataset matters not directly answered in the brief.
- Confirm how bidirectional roads and graph edges should be counted.
- Confirm how locally constructed and generated records should be documented.
- Clarify how member index numbers should be used to derive algorithm parameters.
- Ensure that the Database Team and Dataset Team understand their different responsibilities.
- Agree on a practical dataset preparation and validation process.
- Assign immediate tasks and deadlines.

## Already Confirmed By The Project Brief

These items are already stated in the main project brief and do not require further clarification:

- Java is the recommended programming language.
- SQLite, MySQL and PostgreSQL are permitted databases.
- CSV files may be used to seed the database.
- The final program must read from and write to the database.
- A console menu or simple graphical interface may be used.
- Built-in Java utilities may be used for JDBC, file reading, testing and plotting support.
- Assessed data structures must be implemented by the students.
- The project must use a Ghanaian local context.
- The dataset may be constructed from local knowledge without exposing personal data.
- The dataset must include realistic local names, routes, urgency, traffic, distance, operating hours and limited resources.

Minimum dataset requirements:

- 50 locations.
- 100 roads or edges.
- 300 service requests.
- 30 resources.
- 30 algorithm-run records.

## Pre-Meeting Requirements

### Technical And Development Lead

Prepare:

- Current repository structure.
- Current database schema.
- Current CSV templates.
- Proposed entity mapping.
- Small sample of location, road, rider and order records.
- Proposed graph-weight calculation.
- Proposed index-number parameter calculation.

### Database Team

Prepare:

- Review of existing tables and fields.
- Review of primary and foreign keys.
- Relationship checks between tables.
- Fields that should be added, removed or renamed.
- Check that CSV templates match the database schema.
- Concerns needing Teaching Assistant guidance.

### Dataset Team

Prepare:

- Proposed service area.
- Small sample of locations and roads.
- Possible sources of public location and route information.
- Plan for creating the required 300 orders.
- Assumptions for travel time, distance, urgency and road condition.

### Minutes Recorder

Prepare:

- Decision record.
- Teaching Assistant response log.
- Assigned tasks, responsible members and deadlines.

## Agenda

### 1. Opening And Introduction

Estimated time: 3 minutes

Activities:

- Welcome the invited Teaching Assistant.
- Introduce the group and project.
- Introduce the meeting chairperson and project leads.
- Explain the purpose of the mini meeting.
- Confirm the person recording the minutes.

Expected outcome:

- The Teaching Assistant understands the purpose and focus of the meeting.

### 2. Brief Project Overview

Estimated time: 5 minutes

Presented by: Dzah Solomon Sampson

Project summary:

- Project title: SMART FOOD DELIVERY.
- Domain: Courier and Food Delivery Service.
- Application type: Console application.
- Programming language: Java 17.
- Project management: Maven.
- Database: SQLite.
- Database connection: JDBC.
- Testing framework: JUnit 5.
- Repository: `smart-food-delivery-optimizer`.
- Dataset format: CSV files imported into SQLite.
- Performance graphs: Python or Excel.

Main system functions:

- Store locations, roads, restaurants, customers, riders and orders.
- Process orders using FIFO.
- Prioritise urgent orders.
- Assign riders to orders.
- Search and sort records.
- Find shortest delivery routes.
- Determine reachable locations.
- Generate minimum connection networks.
- Select orders under time or capacity restrictions.
- Record algorithm performance.
- Save and reload records through the database.

Expected outcome:

- The Teaching Assistant understands how the food delivery context maps to the project requirements.

### 3. Domain Entity Mapping Clarification

Estimated time: 4 minutes

Questions and notes:

- Can the `riders` table officially represent the required `resources` entity, provided it includes capacity, home location and availability status?  
  Response: Yes.
- Can the `orders` table officially represent the required `service_requests` entity, provided it includes source, destination, urgency, time submitted, deadline and status?  
  Response: Yes.
- Is it better to retain the required table names, such as `service_requests` and `resources`, or can the group use the more domain-specific names `orders` and `riders`?
- Are the additional domain-specific tables suitable?
  - Restaurants.
  - Customers.
  - Riders.
  - Orders.

Expected outcome:

- The group receives approval for the mapping between the project brief and the food delivery entities.

### 4. Dataset Construction Clarification

Estimated time: 8 minutes

Questions and notes:

- What level of detail should be included in the required dataset evidence note?  
  Note: Reference the source of the data, whether from people, online/public sources or generated from a script.
- Is it acceptable to use real Ghanaian place names?
- Is it acceptable to use publicly available road and location information?
- Is it acceptable to use estimated distances and travel times?
- Is it acceptable to use fictional customers and riders?
- Is it acceptable to use generated food orders?
- Can the group prepare a smaller set of carefully designed orders and use a Java or Python script to generate the remaining records using documented rules?  
  Response: Yes.
- If a generation script is used, what evidence should be submitted?
- Should the group record the source or assumption used for every individual record, or is one documented methodology for each dataset sufficient?
- Is it acceptable for the initial development dataset to contain fewer orders before it is expanded to the required 300 records?

Expected outcome:

- The group understands how the dataset may be constructed, generated and documented.

### 5. Road And Graph Dataset Clarification

Estimated time: 7 minutes

Questions and notes:

- For the required 100 roads or edges, how should a bidirectional connection be counted?
  - As one road record marked as bidirectional.
  - As two directed-edge records.
  - Note recorded: one edge.
- Should the main road network be fully connected, with disconnected graphs used only in separate test cases?  
  Response: It should be fully connected to allow possible movement between routes.
- Can disconnected locations intentionally appear in the main dataset, provided they are documented?
- Can the group use one road dataset for BFS, DFS, Dijkstra, Prim and Kruskal?
- For Dijkstra’s algorithm, can the group define a combined weight using:
  - Distance.
  - Travel time.
  - Road-condition weight.
- Should Prim and Kruskal use the same combined edge weight, or should they use a separate connection-cost value?
- Should the formula used to calculate the final road weight be shown in the source code and report?

Proposed combined weight:

```text
Final road weight = distance weight + travel-time weight + road-condition penalty
```

Expected outcome:

- The group receives a clear method for counting road records and calculating graph weights.

### 6. Algorithm Parameter Clarification

Estimated time: 5 minutes

Questions and notes:

- Must all 17 members’ index numbers be included in the calculation?  
  Response: Yes. Include the entire list of IDs as parameter generation input.
- Can the group calculate combined values from the index numbers and use them for:
  - Hash-table size.
  - Priority weight.
  - Route penalty.
  - Random-data seed.
  - Budget or capacity constraint.
- Can one index-number formula use all members, while another uses selected digits from each member?
- Should the formulas and calculated values appear in:
  - Source code.
  - Technical report.
  - Trace tables.
  - Oral demonstration.
- Is it acceptable to use the index-derived parameters consistently throughout the system?

Expected outcome:

- The group receives an approved method for creating and documenting the required parameters.

### 7. Experimental Dataset Clarification

Estimated time: 4 minutes

Questions:

- Must all performance experiments use only the main food delivery dataset?
- Can additional generated records be used to reach experiment sizes such as 1,000, 5,000, 10,000 or 20,000 records?
- Must the generated performance datasets follow the same schema and rules as the main project dataset?
- Should performance datasets be stored permanently, or may they be generated when the experiment is run?
- Should all raw trial results be included in the final CSV files, together with calculated averages?

Expected outcome:

- The group understands how larger benchmark datasets should be created and presented.

### 8. Database Team Alignment

Estimated time: 4 minutes

Database Team responsibilities:

- Review and approve the database schema.
- Confirm table names and fields.
- Define primary and foreign keys.
- Define table relationships.
- Add appropriate constraints.
- Update `database/schema.sql`.
- Prepare an entity relationship diagram.
- Review CSV headings.
- Test database imports.
- Check foreign-key relationships.
- Prepare validation queries.
- Support database screenshots and report evidence.
- Work with the Technical Lead on JDBC integration.

Database Team deliverables:

- Approved database schema.
- Entity relationship diagram.
- Updated `schema.sql`.
- CSV-to-database mapping.
- Database constraints and validation rules.
- Import-test results.
- Database approval note.

### 9. Dataset Team Alignment

Estimated time: 4 minutes

Dataset Team responsibilities:

- Prepare at least 50 local locations.
- Prepare at least 100 road or edge records.
- Prepare restaurant records.
- Prepare fictional customer records.
- Prepare at least 30 rider records.
- Prepare at least 300 food-order records.
- Apply realistic traffic, urgency, operating-hour and capacity constraints.
- Ensure all IDs match related records.
- Remove duplicates.
- Check missing values.
- Ensure no real personal information is included.
- Prepare the dataset evidence note.
- Work with the Database Team to ensure CSV files match the approved schema.

Dataset Team deliverables:

- `locations.csv`.
- `roads.csv`.
- `restaurants.csv`.
- `customers.csv`.
- `riders.csv`.
- `orders.csv`.
- Dataset evidence note.
- Dataset validation report.

### 10. Final Decisions And Task Assignment

Estimated time: 5 minutes

Decision record:

| Decision | Teaching Assistant Clarification |
| --- | --- |
| Can riders represent resources? | Yes |
| Can orders represent service requests? | Yes |
| Should required or domain-specific table names be used? | To be confirmed |
| What should the dataset evidence note contain? | Record sources/assumptions/methodology |
| Can generated orders be used? | Yes |
| What evidence is required for generated records? | To be confirmed |
| How should bidirectional roads be counted? | One edge noted |
| Should the main graph be connected? | Yes |
| What weight should Dijkstra use? | To be confirmed |
| What weight should Prim and Kruskal use? | To be confirmed |
| How should index-number parameters be calculated? | Use all 17 member IDs |
| Can generated data be used for performance experiments? | To be confirmed |

Expected outcome:

- All important Teaching Assistant clarifications are recorded.
- Immediate tasks and deadlines are agreed.

### 11. Closing

Estimated time: 2 minutes

Activities:

- Summarise main decisions.
- Confirm assigned tasks.
- Confirm deadlines.
- Thank the Teaching Assistant.
- Confirm whether any question requires later follow-up.

## Dataset Preparation Process

### Stage 1: Approve The Schema

The Database Team should confirm:

- Table names.
- Column names.
- Data types.
- Primary keys.
- Foreign keys.
- Required fields.
- Allowed status values.
- Validation rules.

Dataset preparation should not begin fully until the CSV headings have been approved.

### Stage 2: Define The Service Area

Proposed service areas:

- University of Ghana.
- Legon.
- East Legon.
- Madina.
- Haatso.
- Atomic.
- Shiashie.
- Okponglo.
- Adenta.
- Nearby communities.

The locations should form a realistic delivery network rather than being selected randomly across Ghana.

### Stage 3: Prepare Locations

The Dataset Team should prepare at least 50 locations.

Each location should contain:

- Location ID.
- Location name.
- Area.
- Location type.
- Latitude and longitude or approved coordinate values.

Suggested location types:

- Community.
- University facility.
- Hostel.
- Restaurant.
- Shopping centre.
- Hospital.
- Junction.
- Transport station.
- Residential area.
- Landmark.

### Stage 4: Prepare Roads

After locations are approved, the Dataset Team should prepare at least 100 road or edge records.

Each road should contain:

- Road ID.
- Starting location ID.
- Destination location ID.
- Distance.
- Estimated travel time.
- Road-condition weight.
- Direction or bidirectional status.

The road records should only reference existing locations.

### Stage 5: Prepare Restaurants, Customers And Riders

Restaurants should include:

- Restaurant ID.
- Restaurant name.
- Location ID.
- Opening time.
- Closing time.
- Availability status.

Customers should include:

- Customer ID.
- Fictional name.
- Default delivery location.

Riders should include:

- Rider ID.
- Fictional name.
- Current location.
- Home location.
- Vehicle type.
- Capacity.
- Availability status.

### Stage 6: Prepare Orders

The Dataset Team should first prepare a smaller manually reviewed order sample.

Recommended first development dataset: 50 carefully reviewed orders.

Each order should include:

- Order ID.
- Restaurant ID.
- Customer ID.
- Source location.
- Destination location.
- Category.
- Urgency.
- Submission time.
- Deadline.
- Status.
- Estimated distance.
- Assigned rider, where applicable.

After the first sample is validated, the team may expand it to the required 300 records using the method approved by the Teaching Assistant.

### Stage 7: Validate The Dataset

The Database and Dataset Teams should jointly check:

- Unique IDs.
- Missing values.
- Duplicate records.
- Valid foreign keys.
- Correct data types.
- Valid source and destination locations.
- Valid urgency values.
- Valid status values.
- Deadlines later than submission times.
- Rider capacity greater than zero.
- Distances and travel times greater than zero.
- No real private customer or rider information.

### Stage 8: Import And Test

After validation:

- Import CSV files into SQLite.
- Test foreign keys.
- Test basic queries.
- Confirm record counts.
- Confirm that Java can load records.
- Confirm that model objects can be added to custom data structures.
- Record errors and corrections.

## Immediate Action Register

| Task | Responsible Team / Member | Evidence Required | Deadline |
| --- | --- | --- | --- |
| Record the Teaching Assistant’s clarifications | Minutes Recorder | Completed Meeting 4 minutes | __________ |
| Update entity mapping | Database and Technical Teams | Updated schema notes | __________ |
| Finalise the database schema | Database Team | Revised `database/schema.sql` | __________ |
| Prepare entity relationship diagram | Database Team | ER diagram | __________ |
| Approve all CSV headings | Database and Dataset Teams | Approved CSV templates | __________ |
| Prepare at least 50 locations | Dataset Team | `data/locations.csv` | __________ |
| Prepare at least 100 roads or edges | Dataset Team | `data/roads.csv` | __________ |
| Prepare restaurant records | Dataset Team | Restaurant CSV | __________ |
| Prepare fictional customer records | Dataset Team | Customer CSV | __________ |
| Prepare at least 30 riders | Dataset Team | Rider CSV | __________ |
| Prepare the first 50 orders | Dataset Team | `data/orders.csv` | __________ |
| Prepare dataset evidence note | Dataset and Documentation Teams | Dataset methodology document | __________ |
| Prepare index-number calculation | Technical and Documentation Teams | Calculation note and source-code plan | __________ |
| Validate all relationships | Database Team | Validation report | __________ |
| Update CSV and database loaders | Technical Lead | Updated code and tests | __________ |
| Update project documentation | Documentation Team | Updated decision records | __________ |

## Expected Outcomes

Meeting 4 should not end until:

- The mapping of riders to resources has been clarified.
- The mapping of orders to service requests has been clarified.
- The preferred database table names have been agreed.
- The required dataset evidence has been explained.
- The method for constructing or generating orders has been clarified.
- The method for counting bidirectional roads has been clarified.
- The main graph connectivity approach has been agreed.
- The graph-weight calculation has been clarified.
- The index-number parameter method has been clarified.
- The use of generated benchmark data has been clarified.
- The Database Team understands its deliverables.
- The Dataset Team understands its deliverables.
- Immediate tasks and deadlines have been assigned.
- All Teaching Assistant responses have been recorded.

## Attendance Record

| # | Student ID | Full Name | Present / Absent | Team | Notes |
| --- | --- | --- | --- | --- | --- |
| 1 | 22020618 | Adom Bempong Franklin | | | |
| 2 | 22012447 | Dzah Solomon Sampson | | | |
| 3 | 22166686 | Asante Emmanuella Baaba | | | |
| 4 | 22146249 | Mubarack Jibriel | | | |
| 5 | 22106332 | Ofori Richard | | | |
| 6 | 22042260 | Akplu Kelvin Mawuli | | | |
| 7 | 22042713 | Adzraku Prosper Awoenam | | | |
| 8 | 22370501 | Obeng Richard | | | |
| 9 | 22411093 | Tieku Henry Ebo | | | |
| 10 | 22399487 | Amaniampong Samuel Kwarteng | | | |
| 11 | 22262272 | Kodjoh-Kpakpassou Enam Antoine-Marie | | | |
| 12 | 22306912 | Otchere Ernest Atta | | | |
| 13 | 22308781 | Osafo Kimathi Christian | | | |
| 14 | 22382964 | Nyame Ebenezer | | | |
| 15 | 22413798 | Freeman Isaac Kweku | | | |
| 16 | 22402374 | Normanyo Leslie Dela | | | |
| 17 | 22408680 | Okoe Anthonia Holisede | | | |

## Teaching Assistant Clarification Notes

### Domain Entity Mapping

To be completed from final minutes.

### Dataset Construction And Evidence

To be completed from final minutes.

### Road And Graph Dataset

To be completed from final minutes.

### Index-Number Parameters

To be completed from final minutes.

### Performance Experiment Data

To be completed from final minutes.

### Additional Guidance

To be completed from final minutes.

## Decisions Made

To be completed from final minutes.

## Next Meeting

Next meeting date: __________________________________

Next meeting time: __________________________________

Venue / Platform: __________________________________

Main purpose of the next meeting:

- Review the revised database schema.
- Review the entity relationship diagram.
- Review the approved CSV templates.
- Review the location and road datasets.
- Review restaurant, customer and rider records.
- Review the first 50 development orders.
- Review the dataset evidence note.
- Test CSV imports into SQLite.
- Confirm that Java can load imported records.
- Begin using approved data in custom structures and algorithms.

## Closing Statement

Meeting 4 is intended to resolve only interpretation issues not already answered in the main project brief.

The Database Team will be responsible for designing and validating the database structure. The Dataset Team will be responsible for creating clean, realistic, connected and properly documented Ghanaian food delivery data.

All Teaching Assistant clarifications must be recorded and used to update the repository, database schema, CSV templates, dataset methodology and implementation plan.
