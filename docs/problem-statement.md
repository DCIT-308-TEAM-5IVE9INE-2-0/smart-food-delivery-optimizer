# Problem Statement

Food delivery services around Legon campus and nearby communities must handle many orders, riders, locations and route decisions while dealing with urgency, traffic, rider availability and delivery deadlines.

SMART FOOD DELIVERY will model this operational problem as a console-based Java system. The system will store delivery records in a SQLite database, load records into custom data structures, and apply data-structures-and-algorithms techniques to schedule orders, search records, sort requests, assign riders, find routes and measure performance.

## Inputs

- Local locations and coordinates.
- Road connections with distance, travel time and road-condition weights.
- Restaurants or vendors.
- Customers with fictional generated details.
- Riders and their availability.
- Food orders with urgency, deadlines and status.
- Experiment sizes and algorithm choices.

## Outputs

- Next order under FIFO or priority-based rules.
- Assigned rider for an order.
- Sorted order lists.
- Search results for orders, riders, restaurants or locations.
- Shortest delivery route and total cost.
- Reachable locations from a selected point.
- Minimum connection network.
- Dynamic-programming order selection result.
- Runtime and memory experiment records.

## Assumptions

- Customer information is fictional and does not expose real personal data in any way.
- Road distances and travel times are realistic estimates for academic demonstration.
- SQLite is used to make the project portable for all members.
- The console interface is sufficient because the assessment focuses on DSA, testing, database integration and performance evidence.
