# Final Submission Checklist

Project: SMART FOOD DELIVERY

Use this as the last guide for finishing the group submission.

## Technical Lead

- Run `mvn clean test` and confirm all tests pass.
- Run the console demo path in `README.md`.
- Confirm the final smoke-test guide in `docs/final-console-smadd oke-test.md`.
- Confirm student-ID parameters using `docs/index-number-parameters.md`.
- Run final report performance mode if the performance team is ready.

## Dataset Team

- Confirm dataset counts meet the brief:
  - 50+ locations
  - 100+ roads
  - 300+ orders
  - 30+ riders
  - 30+ algorithm runs
- Use `docs/dataset-validation-report.md` and `docs/dataset-evidence.md`.
- Make sure the final CSV files in `data/` are clean and consistent.

## Database Team

- Review `docs/database-setup.md`.
- Confirm `database/schema.sql` matches the application schema.
- Confirm the app can initialize and import the seed data.
- Capture database summary evidence from the console.

## Testing And Performance Team

- Run `mvn clean test`.
- Run `Performance Lab -> Run Final Report Experiments`.
- Generate graphs using the command in `docs/performance-plan.md`.
- Save CSVs and graphs from `results/csv/` and `results/graphs-report/`.
- Record the machine specification used for performance runs.

## Report Team

- Use the following docs as source material:
  - `docs/project-overview.md`
  - `docs/problem-statement.md`
  - `docs/architecture.md`
  - `docs/algorithms.md`
  - `docs/data-structures.md`
  - `docs/final-trace-evidence.md`
  - `docs/performance-plan.md`
- Include the student-ID parameter explanation.
- Include AI assistance acknowledgement.
- Keep screenshots and trace tables clear.

## Presentation And Demo Team

- Follow the demo path in `README.md`.
- Use `docs/final-console-smoke-test.md` as the rehearsal checklist.
- Make sure every speaker knows their assigned part.
- Prepare short answers for DSA, database, testing and performance questions.

## Final Packaging

- Include source code, docs, database schema, seed data, tests, report, slides and performance results.
- Do not include local generated files such as `database/smart_delivery.db` unless specifically requested.
- Confirm the repo builds from a fresh clone.
- Final check: one person runs the full demo before submission.
