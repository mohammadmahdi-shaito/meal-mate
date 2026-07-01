# Agent Guidelines

Meal Mate is intended to be a clean, beginner-friendly, professional full-stack learning project. Future Codex sessions should build it phase by phase and keep the codebase understandable.

## Core Rules

- Keep code beginner-friendly, readable, and consistent.
- Explain important technical decisions in plain language.
- Avoid over-engineering and premature abstractions.
- Build the project phase by phase.
- Prefer small, testable commits.
- Never silently introduce technologies that are not listed in the README or documentation.
- Add comments only where they help learning or clarify non-obvious logic.
- Update documentation when architecture, data models, APIs, or folder structure changes.
- After each task, summarize changed files and explain how to test the result.

## Intended Stack

- Backend: Java, Spring Boot, Spring Data JPA, PostgreSQL
- Frontend: React
- Machine learning later: Python, pandas, scikit-learn
- Deployment later: Docker and Docker Compose
- Authentication later: Spring Security

If a task appears to require a new technology, ask first or clearly document why it is needed before adding it.

## Current Project Phase

The repository is currently in the documentation and foundation phase.

- Do not generate application code unless the user explicitly asks for implementation.
- Do not add package managers, dependencies, generated project scaffolds, Docker files, or build systems unless requested.
- Keep placeholder files limited to documentation or directory markers.

## Development Style

- Prefer simple class, function, and file names.
- Keep methods short and focused.
- Favor explicit code over clever code.
- Add tests with each meaningful backend or frontend behavior once implementation begins.
- Keep commits focused on one clear task.

## Documentation Expectations

Update the relevant file in `docs/` when making changes:

- Product or feature scope: `docs/PRODUCT_REQUIREMENTS.md`
- System structure: `docs/ARCHITECTURE.md`
- Entities or relationships: `docs/DATABASE_DESIGN.md`
- Backend endpoints or modules: `docs/API_DESIGN.md`
- Recommendation logic or data science: `docs/ML_PLAN.md`
- Build order or milestones: `docs/DEVELOPMENT_ROADMAP.md`

## Task Completion Checklist

Before finishing a task:

1. Check `git status`.
2. Confirm no unintended files were changed.
3. Run relevant tests or explain why tests were not run.
4. Summarize changed files.
5. Explain how the user can test or inspect the result.
