# Meal Mate

Meal Mate is a healthy meal recommendation platform. Users will be able to create an account, track ingredients they already have, receive healthy recipe recommendations, add missing ingredients to a grocery list, and later rate recipes so recommendations can improve over time.

## Intended Stack

- Backend: Java, Spring Boot, Spring Data JPA, PostgreSQL
- Frontend: React
- Machine learning: Python, pandas, scikit-learn
- Deployment: Docker and Docker Compose
- Authentication: Spring Security

## Repository Structure

```text
meal-mate/
  README.md
  AGENTS.md
  docs/
    PRODUCT_REQUIREMENTS.md
    ARCHITECTURE.md
    DATABASE_DESIGN.md
    API_DESIGN.md
    ML_PLAN.md
    DEVELOPMENT_ROADMAP.md
  backend/
  frontend/
  ml-service/
```

## Product Goals

Meal Mate should help users answer a practical daily question: "What healthy meal can I make with what I already have?"

The platform should:

- Recommend recipes based on pantry ingredients.
- Support calorie and nutrition goals.
- Include recipes from multiple cultures and cuisines.
- Show which ingredients are available and which are missing.
- Help users build a grocery list.
- Learn from user ratings, saved recipes, preferences, and interaction history over time.

## MVP Features

- User account model planned, with authentication added in a later phase.
- Pantry ingredient tracking.
- Recipe browsing and basic search.
- Recipe recommendations based on available ingredients and nutrition goals.
- Grocery list for missing ingredients.
- Basic recipe ratings planned after the first recommendation flow works.

## Future Features

- Spring Security authentication and authorization.
- Personalized ML recommendations.
- Cuisine preferences and dietary restrictions.
- Saved recipes and meal plans.
- Nutrition dashboards.
- Dockerized local development.
- Deployment-ready production configuration.

## Documentation

Start with these planning files:

- [Product Requirements](docs/PRODUCT_REQUIREMENTS.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Database Design](docs/DATABASE_DESIGN.md)
- [API Design](docs/API_DESIGN.md)
- [ML Plan](docs/ML_PLAN.md)
- [Development Roadmap](docs/DEVELOPMENT_ROADMAP.md)

## MVP Plan

The first implementation task should be the backend foundation: a minimal Spring Boot project with PostgreSQL configuration, health check endpoint, and clear package structure. After that, add the first database entity and repository tests before building the React frontend.

Currently implementing a basic data pipeline for a machine learning module.
