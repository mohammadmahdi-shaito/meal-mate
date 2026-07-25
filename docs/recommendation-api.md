# Recommendation API

The Recommendation API suggests recipes based on the ingredients available in a user's pantry.

The current implementation provides an early rule-based recommendation system. It compares pantry ingredient names with recipe ingredient names, calculates an ingredient match, and identifies missing ingredients.

> **Status:** In development
> The current recommendation system is a prototype and does not yet represent the complete Meal Mate recommendation MVP.

---

## Current Features

The recommendation system currently supports:

* Loading available recipes
* Comparing recipe ingredients with pantry ingredients
* Calculating a basic ingredient match
* Identifying missing recipe ingredients
* Ranking recipes using pantry overlap
* Adding missing ingredients to the grocery table

The recommendation system does not currently support:

* Secure user-specific recommendations
* Ingredient quantity comparison
* Calorie-goal matching
* Protein-goal matching
* Cuisine preferences
* Dietary restrictions
* Ingredient expiration
* Machine-learning personalization

---

## Recommendation Flow

The current recommendation process follows this general flow:

```text
Load pantry ingredients
        ↓
Load available recipes
        ↓
Compare pantry and recipe ingredient names
        ↓
Calculate ingredient match percentage
        ↓
Identify missing ingredients
        ↓
Rank and return recipes
```

For example, suppose the user's pantry contains:

```text
chicken breast
rice
tomato
olive oil
```

A recipe requires:

```text
chicken breast
rice
tomato
yogurt
```

The system identifies:

```text
Available ingredients:
- chicken breast
- rice
- tomato

Missing ingredients:
- yogurt
```

The ingredient match is:

```text
3 matched ingredients ÷ 4 required ingredients = 75%
```

---

# Target API Contract

The following endpoints describe the intended API contract for the Meal Mate MVP.

The exact paths may need to be adjusted to match the current controller mappings.

---

## Get Recommendations

```http
GET /api/recommendations
```

Returns recipe recommendations for the authenticated user.

The backend should determine the user from the authentication token. The client should not submit another user's ID.

### Required authentication

```http
Authorization: Bearer <token>
```

### Example request

```http
GET /api/recommendations
Authorization: Bearer eyJhbGciOi...
```

### Example successful response

```json
[
  {
    "recipeId": 12,
    "recipeName": "Healthy Chicken Rice Bowl",
    "cuisine": "Mediterranean",
    "matchPercentage": 75.0,
    "matchedIngredients": [
      "chicken breast",
      "rice",
      "tomato"
    ],
    "missingIngredients": [
      "yogurt"
    ]
  },
  {
    "recipeId": 8,
    "recipeName": "Tomato Rice",
    "cuisine": "Middle Eastern",
    "matchPercentage": 66.7,
    "matchedIngredients": [
      "rice",
      "tomato"
    ],
    "missingIngredients": [
      "onion"
    ]
  }
]
```

### Successful status

```http
200 OK
```

---

## Recommendation Response Fields

| Field                | Type           | Description                                  |
| -------------------- | -------------- | -------------------------------------------- |
| `recipeId`           | `Long`         | Unique identifier of the recommended recipe  |
| `recipeName`         | `String`       | Name of the recipe                           |
| `cuisine`            | `String`       | Cuisine associated with the recipe           |
| `matchPercentage`    | `Double`       | Percentage of required ingredients available |
| `matchedIngredients` | `List<String>` | Required ingredients found in the pantry     |
| `missingIngredients` | `List<String>` | Required ingredients not found in the pantry |

A future response should also include the individual scoring components used to calculate the recommendation.

---

## Add Missing Ingredients to Grocery List

The current implementation contains an operation similar to:

```http
POST /recommendations/{recipeId}/add-missing-to-grocery
```

This operation finds the ingredients required by the selected recipe that are not available in the user's pantry and adds them to the grocery list.

### Path parameter

| Parameter  | Type   | Description               |
| ---------- | ------ | ------------------------- |
| `recipeId` | `Long` | ID of the selected recipe |

### Example request

```http
POST /recommendations/12/add-missing-to-grocery
Authorization: Bearer eyJhbGciOi...
```

### Example successful response

```json
{
  "recipeId": 12,
  "createdItems": [
    {
      "ingredient": "yogurt",
      "quantity": 200,
      "unit": "g"
    }
  ],
  "alreadyPresent": []
}
```

### Suggested successful status

Use:

```http
201 Created
```

when new grocery items are created.

Use:

```http
200 OK
```

when existing grocery items are merged or no new items are required.

### Design note

This operation changes the grocery list rather than only retrieving recommendations. It may eventually be moved to a grocery-specific endpoint:

```http
POST /api/grocery/from-recipe/{recipeId}
```

---

# Ingredient Match Calculation

The basic ingredient match can be calculated as:

```text
match percentage =
matched required ingredients
──────────────────────────── × 100
total required ingredients
```

For example:

```text
Matched ingredients = 3
Required ingredients = 4

Match percentage = 3 / 4 × 100 = 75%
```

Before comparison, ingredient names should be normalized.

Example normalization:

```text
" Tomato " → "tomato"
"TOMATO"   → "tomato"
```

However, string normalization alone cannot reliably determine that:

```text
tomato
tomatoes
```

refer to the same ingredient.

The stable Meal Mate model should therefore use a normalized `Ingredient` entity and compare ingredient IDs rather than free-form names.

---

# Target MVP Scoring

The complete recommendation MVP should consider more than ingredient presence.

A possible initial scoring model is:

```text
Final recommendation score:

50% pantry ingredient match
20% calorie-goal fit
15% protein-goal fit
10% cuisine preference
 5% expiration-usefulness bonus
```

These weights are initial product rules and can be adjusted later.

## Example target response

```json
{
  "recipeId": 12,
  "recipeName": "Healthy Chicken Rice Bowl",
  "totalScore": 86.5,
  "scoreBreakdown": {
    "pantryMatchScore": 92.0,
    "calorieFitScore": 80.0,
    "proteinFitScore": 90.0,
    "cuisinePreferenceScore": 100.0,
    "expirationBonus": 20.0
  },
  "matchedIngredients": [
    "chicken breast",
    "rice",
    "tomato"
  ],
  "missingIngredients": [
    {
      "ingredient": "yogurt",
      "requiredQuantity": 200,
      "availableQuantity": 0,
      "missingQuantity": 200,
      "unit": "g"
    }
  ]
}
```

Returning a score breakdown makes the recommendation explainable to the user and easier to test.

---

# User Ownership and Security

Recommendations must use only the authenticated user's pantry.

The following behavior is unsafe:

```text
Load every pantry item from the database
→ Use all items to calculate recommendations
```

The correct behavior is:

```text
Identify the authenticated user
→ Load only that user's pantry
→ Calculate recommendations using that pantry
```

Conceptually, the repository should use a user-scoped query:

```java
List<PantryItem> findAllByUserId(Long userId);
```

The recommendation service should receive the current user from Spring Security rather than trusting a user ID sent by the client.

---

# Error Responses

The API should return a consistent error format.

## Recipe not found

```http
404 Not Found
```

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Recipe with ID 12 was not found",
  "path": "/api/recommendations/12/add-missing-to-grocery"
}
```

## User is not authenticated

```http
401 Unauthorized
```

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Authentication is required",
  "path": "/api/recommendations"
}
```

## Invalid recipe data

```http
400 Bad Request
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "The recipe contains invalid ingredient data",
  "path": "/api/recommendations"
}
```

---

# Important Edge Cases

The recommendation service should handle:

* An empty pantry
* A recipe with no ingredients
* Duplicate ingredients in one recipe
* Null or blank ingredient names
* Different capitalization and surrounding spaces
* A complete ingredient match
* No ingredient matches
* Missing ingredient quantities
* Two recipes with equal scores
* Missing ingredients already present in the grocery list
* Two users with different pantry contents

Repeatedly adding missing ingredients should not create duplicate grocery rows.

---

# Current Limitations

The current recommendation implementation has several known limitations:

1. Recommendations are based primarily on ingredient-name presence.
2. Pantry data is not reliably isolated by authenticated user.
3. Ingredient quantities are not compared.
4. Nutrition goals are not included in scoring.
5. Cuisine preferences are not included in scoring.
6. Duplicate or null recipe ingredients may produce incorrect match percentages.
7. Free-form ingredient names can create inconsistent matches.
8. Repeated grocery additions may create duplicate rows.
9. Recommendation behavior has little automated test coverage.

These limitations should be addressed before treating the Recommendation API as complete.

---

# Planned Improvements

The recommended development order is:

1. Implement authentication and user-specific pantry access.
2. Introduce a normalized `Ingredient` entity.
3. Validate and deduplicate recipe ingredients.
4. Calculate missing ingredient quantities.
5. Add calorie and protein scoring.
6. Add cuisine preferences and dietary restrictions.
7. Return an explainable score breakdown.
8. Prevent duplicate grocery entries.
9. Add unit, controller, and repository tests.
10. Introduce machine-learning personalization only after collecting reliable interaction data.

---

# API Status Summary

| Capability                     | Status                   |
| ------------------------------ | ------------------------ |
| Pantry ingredient overlap      | Partially implemented    |
| Basic recipe ranking           | Partially implemented    |
| Missing ingredient detection   | Partially implemented    |
| Add missing items to grocery   | Partially implemented    |
| User-specific recommendations  | Not securely implemented |
| Quantity-aware matching        | Not implemented          |
| Calorie-goal scoring           | Not implemented          |
| Protein-goal scoring           | Not implemented          |
| Cuisine preference scoring     | Not implemented          |
| Dietary restrictions           | Not implemented          |
| Explainable score breakdown    | Not implemented          |
| ML personalization             | Deferred                 |
| Automated recommendation tests | Mostly missing           |
