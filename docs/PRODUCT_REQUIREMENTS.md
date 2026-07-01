# Product Requirements

## Product Summary

Meal Mate is a healthy meal recommendation platform. It helps users decide what to cook by using ingredients they already have, nutrition goals, cuisine preferences, and eventually learned behavior from ratings and interactions.

## Target Users

- People who want healthy meal ideas without wasting pantry ingredients.
- People tracking calories or nutrition goals.
- People interested in multicultural recipes.
- Beginners who want practical grocery planning support.

## Core Problem

Users often have ingredients at home but do not know what healthy meals they can make with them. They may also want recommendations that match calorie goals, dietary needs, and preferred cuisines.

## MVP Features

The first usable version should include:

- Basic user profile data.
- Pantry ingredient management.
- Recipe records with ingredients, cuisine, calories, and nutrition information.
- Basic recommendation logic using available pantry ingredients and nutrition goals.
- Missing ingredient detection.
- Grocery list management.
- Simple recipe rating support after recommendations are working.

Authentication is planned, but it should be added after the basic backend structure is stable.

## Future Advanced Features

- Full account registration and login with Spring Security.
- Personalized recipe recommendations using ratings, saved recipes, cuisine preferences, pantry data, and interaction history.
- Dietary restrictions, allergies, and disliked ingredients.
- Weekly meal planning.
- Nutrition summaries and progress tracking.
- Recipe import from external sources.
- Admin tools for recipe and ingredient management.
- Docker Compose setup for local development.
- Deployment configuration for production.

## Main User Flows

1. User creates or opens a profile.
2. User adds pantry ingredients.
3. User sets nutrition or calorie goals.
4. User requests recipe recommendations.
5. App shows recipes that use available ingredients and flags missing ingredients.
6. User adds missing ingredients to a grocery list.
7. User saves or rates recipes.
8. Future ML system improves recommendations based on behavior.

## What Should Be Built First

Build the backend foundation first:

1. Create a minimal Spring Boot backend project.
2. Configure PostgreSQL connection settings.
3. Add a health check endpoint.
4. Add the first simple entity and repository test.

This creates a stable base before frontend and ML work begin.
