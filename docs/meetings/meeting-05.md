# Meeting 05

DCIT 204/308 Joint Data Structures and Algorithms Semester Project

Meeting title: Final Requirements Gap Review and Compliance Plan

Official project title: SMART FOOD DELIVERY

Group: Group 39, Team_5ive9ine_2.0

Meeting type: Requirements Completion and Evidence Planning Meeting

## Purpose

Meeting 5 is to compare the current SMART FOOD DELIVERY project against the official project brief and agree on the remaining work required for full submission readiness.

The meeting must focus on:

- Dataset scale and validation.
- Student-ID-derived algorithm parameters.
- Database read/write compliance.
- Interactive console readiness.
- Performance experiment sizes and graphs.
- Required trace tables, screenshots and report evidence.
- Individual oral-defence responsibility.
- Final submission package.

## Current Status Summary

Already implemented:

- Java 17 Maven console project.
- SQLite database schema and JDBC loading.
- CSV import workflow.
- Custom data structures and algorithms.
- Interactive console submenus.
- Database-backed dispatch and rider assignment updates.
- Audit events for dispatch and assignment changes.
- Unit tests above the minimum requirement.
- Performance experiment service.
- CSV performance export.
- SVG graph generation script.
- Meeting documents 1 to 4.

Main remaining compliance risks:

- Current seed dataset is still below the required final size.
- Student-ID-derived parameters are not yet implemented in source code.
- Student-ID parameter formula is not yet documented in a final evidence note.
- Performance experiment sizes currently include small demo sizes, not all required brief sizes.
- Final report, screenshots, trace tables and oral-defence packs are not complete.
- Dataset evidence note and validation report are not complete.
- Some Meeting 4 clarification fields still say "To be confirmed".

## Official Brief Requirements Still Needing Work

### 1. Dataset Size

Current development CSV counts:

| Entity | Brief Minimum | Current CSV Count | Status |
| --- | --- | --- | --- |
| Locations | 50 | 6 | Not complete |
| Roads / Edges | 100 | 6 | Not complete |
| Service Requests / Orders | 300 | 5 | Not complete |
| Resources / Riders | 30 | 3 | Not complete |
| Algorithm Runs | 30, generated runs available | 2 seed rows, needs final generated evidence | Not complete |

Meeting decision needed:

- Confirm whether the generated OpenStreetMap landmark workflow will be used for locations.
- Confirm who will validate the 50 locations and 100 roads.
- Confirm whether generated orders will be used to reach 300 records.
- Confirm whether generated fictional riders/customers are acceptable for the final dataset.

Immediate action:

- Dataset Team must produce final CSV files meeting the required counts.
- Database Team must validate IDs, missing values, duplicate records and foreign keys.

### 2. Student-ID-Derived Algorithm Parameters

Official requirement:

- At least three algorithm parameters must be derived from member index numbers.
- Meeting 4 clarification says all 17 member IDs should be included as parameter-generation input.
- The report must include team-specific traces generated from the team dataset and index-number parameters.

Confirmed member IDs:

```text
22020618, 22012447, 22166686, 22146249, 22106332, 22042260,
22042713, 22370501, 22411093, 22399487, 22262272, 22306912,
22308781, 22382964, 22413798, 22402374, 22408680
```

Proposed parameters to implement:

| Parameter | Used For | Possible Formula |
| --- | --- | --- |
| Priority Weight | Priority queue / urgent order dispatch | (sum of all ID digits % 5) + 1 |
| Route Penalty | Dijkstra / MST edge weighting | (sum of last two digits of each ID % 7) + 1 |
| Hash Table Initial Size | Hash table experiment and indexing | Next prime after (sum of all IDs % 50) + 50 |
| Random Data Seed | Dataset/performance generation | Sum of all full IDs modulo 1,000,000 |
| DP Capacity | Dynamic programming order selection | (sum of final ID digits % 20) + 10 |

Meeting decision needed:

- Approve exactly three to five parameters.
- Decide where they will be used in the console.
- Decide whether formulas should be displayed in the application, report or both.

Immediate action:

- Technical Lead implements `StudentIdParameterService` or equivalent.
- Documentation Team prepares `docs/index-number-parameters.md`.
- Testing Team adds tests for deterministic parameter calculation.

### 3. Database Read/Write Compliance

Official requirement:

- CSV may seed the database.
- The final program must read from and write to the database.
- The database must be part of the running system, not only storage.

Current status:

- Import reads CSV into SQLite.
- Console reads locations, roads, restaurants, customers, riders, orders and algorithm runs from SQLite.
- Dispatch now updates order status to `DISPATCHED`.
- Assignment now updates order status to `ASSIGNED`, assigned rider ID, rider status and rider current location.
- Audit events are recorded.

Remaining work:

- Add a final demo script showing database state before and after dispatch/assignment.
- Capture database screenshots or console output for report evidence.
- Add validation queries to the `database/` or `docs/` folders.
- Decide whether delivered/cancelled status transitions are needed for the final demo.

### 4. Interactive Console Readiness

Official requirement:

- The program must include a console menu or simple GUI that allows an examiner to run demonstrations without editing source code.

Current status:

- The console now has task-based submenus.
- Users can choose records and algorithms interactively.
- Route and optimisation flows no longer depend on hardcoded source/destination/order counts.
- Dispatch and assignment ask before database updates.

Remaining work:

- Add stronger cancel/back behaviour inside long operations.
- Make performance experiment sizes selectable.
- Add live stack-based undo/audit demo.
- Update README with a final live-demo sequence.
- Run a manual full-demo rehearsal.

### 5. Performance Experiment Compliance

Official required sizes:

- Search: 100, 500, 1,000, 5,000 and 10,000 records.
- Sorting: 100, 500, 1,000, 5,000 and 10,000 requests.
- Hash table: 100 to 20,000 keys with different table sizes.
- BST vs balanced tree: multiple insert/search sizes.
- Heap dispatch: 100 to 20,000 requests.
- Graph algorithms: 50, 100, 200 and 500 locations/edges.
- Each experiment must run at least three times.
- Report must include average runtime.
- Machine specification must be stated.
- Raw CSV and line graphs must be included.

Current status:

- Experiment code exists.
- CSV export exists.
- SVG graph generator exists.
- Default demo sizes are 50, 100 and 200.

Remaining work:

- Add report-size experiment mode.
- Export averages or prepare an average table for the report.
- Run final experiments on one machine.
- Record machine specification.
- Interpret graphs in the report.

### 6. Correctness And Testing Evidence

Official requirements:

- At least 40 unit tests.
- At least six trace tables: binary search, insertion sort, merge sort or quicksort, Dijkstra, Kruskal or Prim, dynamic programming.
- At least three proof sketches.
- At least two counterexamples.
- Edge cases including empty structure, single element, duplicate keys, disconnected graph, unreachable path, queue full/empty and hash collision.

Current status:

- Test count is above 40.
- Correctness documents and proof notes exist.
- Greedy and binary-search counterexamples are documented.
- Edge-case tests exist.

Remaining work:

- Generate final trace tables from the final dataset and student-ID parameters.
- Add trace table screenshots or formatted tables to the report.
- Ensure every trace includes input, output and explanation.

### 7. Data Structure Evidence

Official evidence still needing report packaging:

- Dynamic array resize trace.
- Linked list diagram and iterator demo.
- Stack undo/audit trace.
- Queue front/rear trace.
- Circular queue wrap-around trace.
- Deque urgent request example.
- Priority queue dispatch trace.
- BST search path and inorder output.
- Red-black tree rotation and height discussion.
- B-tree node split/search trace.
- Hash collision statistics.
- Set/map membership use case.
- Disjoint set Kruskal trace.
- Graph adjacency list/matrix evidence.

Current status:

- Implementations exist.
- Tests exist for most structures.
- Some trace helpers exist.

Remaining work:

- Convert evidence into report-ready diagrams, tables and screenshots.
- Assign one member to explain each structure during oral defence.

### 8. Final Report And Submission Package

Official final report sections:

- Cover page with title, team members, Ghanaian context and selected problem.
- Problem statement, assumptions, input-output definitions and system boundaries.
- Dataset description, data dictionary and database schema.
- System architecture and module design.
- Data-structure explanations, diagrams and selected code snippets.
- Algorithm explanations, pseudocode and selected code snippets.
- Correctness evidence.
- Performance analysis.
- Database integration evidence.
- Responsible algorithm selection.
- Individual contribution statement.
- AI assistance acknowledgement.
- References and appendices.

Submission items:

- Source code or repository export.
- Database scripts and seed data.
- Technical report in PDF and DOCX.
- Performance CSV files and graphs.
- 5-8 minute demonstration video.
- Oral-defence preparation.

Remaining work:

- Build the final report in `submission/report`.
- Prepare the presentation in `submission/presentation`.
- Record or plan the demo video in `submission/demo-video`.
- Add screenshots and database evidence.
- Add AI assistance acknowledgement.
- Add individual contribution statements.

## Agenda

### 1. Opening And Goal Confirmation

Estimated time: 3 minutes

Activities:

- Confirm that Meeting 5 is about final compliance, not new features.
- Confirm the official project title and current repository.
- Confirm that all members understand the remaining brief requirements.

Expected outcome:

- Members understand that the project is now moving towards completion and evidence preparation.

### 2. Brief Requirement Gap Review

Estimated time: 8 minutes

Presenter: Technical Lead

Review:

- Dataset gaps.
- Student-ID parameter gap.
- Performance experiment size gap.
- Report and evidence gaps.
- Oral-defence gaps.

Expected outcome:

- The team agrees on the exact remaining work.

### 3. Dataset Completion Plan

Estimated time: 10 minutes

Discussion:

- Who will produce the final 50 locations?
- Who will produce the final 100 roads?
- Who will produce the final 30 riders?
- Who will produce the final 300 orders?
- How will generated data be documented?
- Who will validate foreign keys and duplicate records?

Decision required:

- Final dataset deadline.
- Dataset validation owner.

Expected outcome:

- Clear ownership of every outstanding dataset requirement.

### 4. Student-ID Parameter Plan

Estimated time: 8 minutes

Discussion:

- Confirm the formulas.
- Confirm at least three parameters.
- Confirm where the parameters affect algorithms.
- Confirm documentation and report evidence.

Decision required:

- Approve final formulas.
- Assign implementation and documentation responsibilities.

Expected outcome:

- The group agrees on the exact index-number parameters that will be implemented.

### 5. Database And Console Compliance Review

Estimated time: 7 minutes

Discussion:

- Demonstrate database read/write behaviour.
- Confirm dispatch updates order status.
- Confirm assignment updates order and rider records.
- Confirm audit-event output.
- Identify any missing status transitions.

Decision required:

- Decide whether to add delivered/cancelled transitions.
- Decide the final live-demo path.

Expected outcome:

- Final database and console demonstration requirements are confirmed.

### 6. Performance Lab Completion Plan

Estimated time: 8 minutes

Discussion:

- Confirm the final experiment sizes.
- Decide between quick-demo mode and report-size mode.
- Assign graph generation and interpretation.
- Record the machine specification.

Decision required:

- Final performance-run owner.
- Final graph owner.

Expected outcome:

- A clear plan exists for producing final performance evidence.

### 7. Report, Evidence And Oral Defence Assignment

Estimated time: 12 minutes

Discussion:

- Assign report sections.
- Assign screenshots.
- Assign trace tables.
- Assign oral-defence one-page notes.
- Confirm each member's data structure and algorithm.

Decision required:

- Every member leaves with one evidence deliverable.

Expected outcome:

- All remaining documentation and presentation work is distributed among members.

### 8. Final Action Register And Deadlines

Estimated time: 5 minutes

The following should be recorded for every remaining task:

- Task.
- Owner.
- Evidence file or output.
- Deadline.

Expected outcome:

- Every outstanding requirement has an owner and deadline.

## Action Register

| Task | Owner / Team | Evidence Required | Deadline |
| --- | --- | --- | --- |
| Expand `locations.csv` to at least 50 records | Dataset Team (Enam) | Final CSV + source/methodology note | __________ |
| Expand `roads.csv` to at least 100 records | Dataset Team | Final CSV + connected graph check | __________ |
| Expand `riders.csv` to at least 30 records | Dataset Team | Final CSV | __________ |
| Expand `orders.csv` to at least 300 records | Dataset Team | Final CSV + generation rules | __________ |
| Validate final CSV foreign keys and duplicates | Database Team (Enam) | Validation report/screenshots | __________ |
| Implement student-ID parameter calculation | Technical Lead (Solomon) | Source code + tests | __________ |
| Document student-ID formulas | Documentation Team | `docs/index-number-parameters.md` | __________ |
| Apply ID parameters to at least three algorithms | Technical Lead | Console evidence + tests | __________ |
| Add report-size performance mode | Technical Lead | Updated performance service | __________ |
| Run final performance experiments | Testing/Performance Team | CSV + graphs + averages | __________ |
| Record machine specification | Testing/Performance Team | Report note | __________ |
| Generate final trace tables | Testing/Documentation Team | Trace tables for six algorithms | __________ |
| Capture database screenshots/run logs | Database/Documentation Team | Screenshots/logs | __________ |
| Draft final technical report | Documentation Team | DOCX/PDF draft | __________ |
| Prepare demo video script | Presentation Team | 5-8 minute script | __________ |
| Prepare oral-defence notes | All Members | One-page member notes | __________ |
| Add AI assistance acknowledgement | Documentation Team | Report section | __________ |

## Expected Outcomes

Meeting 5 should end with:

- A clear final dataset completion plan. DONE
- Approved student-ID parameter formulas. DONE
- Agreement on how the parameters affect algorithms. DONE
- Confirmation of database read/write demo behaviour. DONE
- Final performance experiment plan.
- Assigned report and evidence responsibilities. DONE
- Oral-defence preparation assignments.
- Deadlines for all remaining deliverables.

## Decision Record

| Decision | Final Record |
| --- | --- |
| Final dataset completion deadline | __________ |
| Dataset validation owner | __________ |
| Approved Priority Weight formula | __________ |
| Approved Route Penalty formula | __________ |
| Approved Hash Table Size formula | __________ |
| Additional ID-derived parameter | __________ |
| Final live-demo path | __________ |
| Delivered/cancelled transitions required? | __________ |
| Final performance-run owner | __________ |
| Final graph owner | __________ |
| Machine specification | __________ |
| Final report coordinator | __________ |
| Demo video coordinator | __________ |

## Team Progress Record

### Dataset Team

Current progress:

Remaining work:

Deadline:

### Database Team

Current progress:

Remaining work:

Deadline:

### Testing And Performance Team

Current progress:

Remaining work:

Deadline:

### Documentation Team

Current progress:

Remaining work:

Deadline:

### Presentation And Oral Defence Team

Current progress:

Remaining work:

Deadline:

## Risks And Blockers

| Risk / Blocker | Team Affected | Proposed Solution | Responsible Member | Deadline |
| --- | --- | --- | --- | --- |

## Next Meeting

Next meeting date: __________________________________

Next meeting time: __________________________________

Venue / Platform: __________________________________

Main purpose of the next meeting:

- Review the final dataset.
- Review the implemented student-ID parameters.
- Review the final performance results.
- Review trace tables and screenshots.
- Review the technical report draft.
- Review individual oral-defence notes.
- Conduct a partial final system demonstration.
- Confirm final submission readiness.

## Closing Statement

Meeting 5 marks the transition from implementation into final compliance, evidence preparation and submission readiness.

The focus of the group from this point should be on completing the remaining requirements rather than introducing unnecessary new features. Every team should provide evidence of its assigned work, and every member should understand the data structure, algorithm or technical area assigned to them for the final oral defence.

The project should only be considered complete when the final dataset, student-ID parameters, database evidence, performance experiments, trace tables, technical report, demonstration video and individual oral-defence preparation are all ready.
