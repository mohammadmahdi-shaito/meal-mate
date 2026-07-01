# Machine Learning Plan

## Goal

Meal Mate will eventually use machine learning to personalize recipe recommendations. The ML system should learn from user ratings, saved recipes, cuisine preferences, pantry data, nutrition goals, and interaction history.

## Start Without ML

The MVP should use rules-based recommendations first. This is easier to understand, test, and debug.

Initial scoring can use:

- Pantry ingredient match percentage.
- Number of missing ingredients.
- Calorie goal distance.
- Cuisine preference match.
- Dietary restriction filtering.

## Data To Collect First

Before ML is useful, the app should collect:

- Recipe views.
- Saved recipes.
- Recipe ratings.
- Grocery list additions.
- Pantry ingredients.
- Cuisine preferences.
- Nutrition goals.

## Future ML Inputs

Potential input features:

- User preferred cuisines.
- User disliked cuisines or ingredients.
- Average rating by cuisine.
- Pantry ingredient overlap with recipe ingredients.
- Recipe calories and macronutrients.
- Prep time.
- Save and view history.
- Rating history.

## Future ML Outputs

The ML system may output:

- A personalized recipe score.
- Ranked recommendation lists.
- Cuisine suggestions.
- Ingredient-based recipe clusters.

## Possible Approaches

### Phase 1: Rules-Based Scoring

Implement in the Spring Boot backend. No Python service is needed yet.

### Phase 2: Offline Analysis

Use Python, pandas, and scikit-learn in `ml-service/` to explore stored data.

Examples:

- Analyze popular recipes.
- Find common cuisine preferences.
- Test simple content-based recommendation models.

### Phase 3: Personalization Model

Use scikit-learn to train a simple model or ranking function.

Possible methods:

- Content-based filtering.
- Similarity scoring.
- Classification or regression using ratings as labels.

### Phase 4: Service Integration

Integrate ML results with the backend after the model proves useful.

Options:

- Batch-generate recommendation scores and store them in PostgreSQL.
- Add a Python service that the backend calls when recommendations are requested.

## What To Build First

Do not build ML first. Build the backend entities and collect useful interaction data first. The ML service should begin as documentation and exploratory scripts only after the application has meaningful data.
