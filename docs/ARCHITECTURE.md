# Architecture

## System Overview

Meal Mate will be a full-stack application with three main areas:

- Backend API: Java Spring Boot service for business logic, data access, and future authentication.
- Frontend app: React application for user interaction.
- ML service: Future Python service or scripts for personalized recommendations.

PostgreSQL will be the main database.

## High-Level Flow

```text
React frontend
  -> Spring Boot REST API
    -> PostgreSQL database
    -> Future ML service or recommendation module
```

## Backend Responsibilities

The backend should own:

- User profile data.
- Pantry ingredients.
- Recipes and recipe ingredients.
- Nutrition goals.
- Grocery lists.
- Ratings and saved recipes.
- Recommendation API responses.
- Future authentication and authorization.

## Frontend Responsibilities

The frontend should own:

- Page layout and navigation.
- Forms for pantry, goals, and grocery list updates.
- Displaying recipes and recommendations.
- Calling backend APIs.
- User-friendly loading and error states.

## ML Service Responsibilities

The ML service should be added later, after enough application data exists.

It may own:

- Training recommendation models.
- Scoring recipes for a user.
- Batch analysis of ratings and saved recipes.
- Experimentation with pandas and scikit-learn.

The first recommendation version should be rules-based inside the backend. This keeps the MVP simpler.

## Recommended Folder Structure

```text
meal-mate/
  backend/
    README.md
    src/
      main/
        java/
          com/mealmate/
            MealMateApplication.java
            user/
            pantry/
            recipe/
            grocery/
            recommendation/
            nutrition/
        resources/
      test/
  frontend/
    README.md
    src/
      components/
      pages/
      api/
      hooks/
      styles/
  ml-service/
    README.md
    notebooks/
    src/
    data/
  docs/
```

These folders are recommendations for future implementation. They should not be created with code until the relevant phase begins.

## Design Principles

- Keep modules organized by feature.
- Keep controllers, services, repositories, and data models easy to find.
- Start with simple REST APIs.
- Add authentication only after the core data flow is clear.
- Add ML only after ratings and interactions are stored.
- Keep documentation updated when the architecture changes.
