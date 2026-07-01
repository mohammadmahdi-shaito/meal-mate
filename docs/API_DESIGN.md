# API Design

## API Style

The backend should expose REST APIs from the Spring Boot application. Keep endpoints simple, predictable, and beginner-friendly.

Suggested base path:

```text
/api
```

## Backend API Modules

### Health Module

Purpose: confirm the backend is running.

Possible endpoint:

- `GET /api/health`

### User Module

Purpose: manage user profile data before full authentication exists.

Possible endpoints:

- `GET /api/users/{userId}`
- `POST /api/users`
- `PUT /api/users/{userId}/profile`

### Pantry Module

Purpose: manage ingredients the user currently has.

Possible endpoints:

- `GET /api/users/{userId}/pantry`
- `POST /api/users/{userId}/pantry`
- `PUT /api/users/{userId}/pantry/{pantryItemId}`
- `DELETE /api/users/{userId}/pantry/{pantryItemId}`

### Recipe Module

Purpose: store and read recipe information.

Possible endpoints:

- `GET /api/recipes`
- `GET /api/recipes/{recipeId}`
- `POST /api/recipes`

### Recommendation Module

Purpose: return recipes that fit pantry ingredients and nutrition goals.

Possible endpoints:

- `GET /api/users/{userId}/recommendations`

Initial recommendation logic can score recipes by:

- Number of available ingredients.
- Number of missing ingredients.
- Calorie target fit.
- Cuisine preference match.

### Grocery Module

Purpose: manage missing ingredients the user wants to buy.

Possible endpoints:

- `GET /api/users/{userId}/grocery-list`
- `POST /api/users/{userId}/grocery-list`
- `PUT /api/users/{userId}/grocery-list/{itemId}`
- `DELETE /api/users/{userId}/grocery-list/{itemId}`

### Rating Module

Purpose: collect feedback for future personalization.

Possible endpoints:

- `POST /api/users/{userId}/recipes/{recipeId}/ratings`
- `GET /api/users/{userId}/ratings`

## Frontend Pages

Planned React pages:

- Home or dashboard page.
- Pantry page.
- Recipe recommendations page.
- Recipe detail page.
- Grocery list page.
- Profile and goals page.
- Login and registration pages later, when Spring Security is added.

## What To Build First

The first API should be:

- `GET /api/health`

After that, add simple recipe and ingredient read APIs before implementing recommendation logic.
