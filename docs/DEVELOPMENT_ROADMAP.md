# Development Roadmap

## Phase 0: Project Foundation

Status: current phase.

Goals:

- Create repository structure.
- Document product requirements.
- Document architecture.
- Plan database entities.
- Plan API modules.
- Plan ML direction.

## Phase 1: Backend Foundation

Goals:

- Create a minimal Spring Boot project in `backend/`.
- Configure PostgreSQL settings.
- Add a health check endpoint.
- Add basic testing setup.
- Keep the first commit small and easy to review.

Recommended first backend package areas:

- `health`
- `recipe`
- `ingredient`

## Phase 2: Core Data Model

Goals:

- Add `Ingredient`.
- Add `Recipe`.
- Add `RecipeIngredient`.
- Add repository tests.
- Add simple seed data only if requested.

## Phase 3: Pantry And Grocery Flow

Goals:

- Add pantry item management.
- Add grocery list item management.
- Detect missing ingredients for a recipe.
- Keep APIs simple and testable.

## Phase 4: Basic Recommendations

Goals:

- Implement rules-based recommendation scoring.
- Score by pantry match, missing ingredients, calorie fit, and cuisine preference.
- Return clear recommendation results to the frontend.

## Phase 5: Frontend Foundation

Goals:

- Create a React app in `frontend/`.
- Add routing.
- Add simple pages for dashboard, pantry, recommendations, recipe details, grocery list, and profile.
- Connect to the backend health check first.

## Phase 6: User Feedback

Goals:

- Add recipe ratings.
- Add saved recipes.
- Add interaction events.
- Prepare useful data for future ML work.

## Phase 7: Authentication

Goals:

- Add Spring Security.
- Add registration and login.
- Protect user-specific endpoints.
- Update frontend auth flow.

## Phase 8: ML Exploration

Goals:

- Use Python, pandas, and scikit-learn in `ml-service/`.
- Analyze recipe ratings and saved recipes.
- Prototype simple personalized recommendation models.

## Phase 9: Docker And Deployment

Goals:

- Add Dockerfiles when the app structure is stable.
- Add Docker Compose for backend, frontend, database, and optional ML service.
- Prepare deployment documentation.

## Recommended Next Small Task

Create the backend Spring Boot foundation only:

- Generate a minimal Spring Boot project.
- Add the planned package structure.
- Add `GET /api/health`.
- Add one basic test for the health endpoint.
- Do not add recipe, pantry, auth, frontend, or ML implementation yet.
