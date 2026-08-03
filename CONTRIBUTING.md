# Contributing

This project uses a simple team workflow so every member's contribution can be reviewed and recorded.

## Branches

- `main`: stable approved work only.
- `develop`: integrated milestone work.
- `feature/name`: new code, data or documentation tasks.
- `fix/name`: bug fixes.
- `docs/name`: report, documentation and meeting updates.

Examples:

```text
feature/database-schema
feature/order-queue
docs/problem-statement
test/binary-search-cases
```

## Commit Messages

Use clear commit messages:

```text
feat: add rider model
data: add location CSV template
test: add stack boundary tests
docs: update system scope
fix: handle empty queue dequeue
```

Avoid vague messages such as `update`, `changes`, `work` or `final`.

## Pull Request Checklist

Every pull request should explain:

- Task completed
- Files changed
- How it was tested
- Expected result
- Screenshots, tables or evidence where relevant
- Known limitations

Do not merge work that does not compile, breaks tests, uses prohibited built-in structures for assessed logic, or overwrites another member's work without discussion.

## Definition of Done

A task is complete only when the required file or code exists, expected behavior works, normal/boundary/invalid cases are considered, documentation is updated, and the contribution log is updated.
