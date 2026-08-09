# Database Setup

SMART FOOD DELIVERY uses SQLite through JDBC.

SQLite is file-based, so teammates do not need to install or run a separate database server. The application creates and uses this local database file:

```text
database/smart_delivery.db
```

This file is ignored by Git because every developer can generate it locally.

## Requirements

- Java 17 or newer.
- Maven 3.9 or newer.
- The project dependencies from `pom.xml`.

The SQLite JDBC driver is downloaded automatically by Maven:

```xml
org.xerial:sqlite-jdbc
```

## How The Connection Works

The default connection is defined in `DatabaseConnection`:

```java
jdbc:sqlite:database/smart_delivery.db
```

When the application opens a connection, it also creates the `database/` folder if it does not already exist.

The schema used by the application is stored here:

```text
src/main/resources/database/schema.sql
```

The project also keeps a copy here for documentation/submission:

```text
database/schema.sql
```

## First-Time Local Setup

From the project root, run:

```bash
mvn clean test
```

Then start the console app:

```bash
mvn exec:java
```

In the menu:

1. Choose `1. Initialize Database`.
2. Choose `2. Import CSV Seed Data`.
3. Choose `3. View Database Summary`.

After this, `database/smart_delivery.db` should exist locally and contain the CSV seed data.

## CSV Import Order

The importer loads files in dependency order:

1. `data/locations.csv`
2. `data/roads.csv`
3. `data/restaurants.csv`
4. `data/customers.csv`
5. `data/riders.csv`
6. `data/orders.csv`
7. `data/algorithm_runs.csv`

Locations must exist before roads, restaurants, customers, riders and orders can reference them.

## Resetting The Local Database

To reset the database, stop the app and delete:

```text
database/smart_delivery.db
```

Then run the console app again and choose:

1. `Initialize Database`
2. `Import CSV Seed Data`

Do not commit the `.db` file.

## Common Problems

If `mvn exec:java` fails because dependencies are missing, run:

```bash
mvn clean test
```

If import fails with a foreign-key error, check that IDs in the CSV files match existing records. For example, an order's `restaurant_id`, `customer_id`, `source_location_id`, and `destination_location_id` must already exist.

If PowerShell has trouble with manual Exec plugin arguments, use the configured command:

```powershell
mvn exec:java
```
