# Database Design

## Database

Meal Mate will use PostgreSQL as the primary database. Spring Data JPA will be used in the backend to map Java entities to database tables.

## Planned Entities

### User

Represents a person using the app.

Suggested fields:

- `id`
- `email`
- `displayName`
- `createdAt`
- `updatedAt`

Authentication fields should be added later when Spring Security is introduced.

### UserProfile

Stores preference and goal information.

Suggested fields:

- `id`
- `userId`
- `dailyCalorieGoal`
- `preferredCuisines`
- `dietaryRestrictions`
- `allergies`

### Ingredient

Represents a normalized ingredient.

Suggested fields:

- `id`
- `name`
- `category`
- `defaultUnit`

### PantryItem

Represents an ingredient the user currently has.

Suggested fields:

- `id`
- `userId`
- `ingredientId`
- `quantity`
- `unit`
- `expirationDate`

### Recipe

Represents a meal recommendation option.

Suggested fields:

- `id`
- `title`
- `description`
- `cuisine`
- `instructions`
- `calories`
- `proteinGrams`
- `carbGrams`
- `fatGrams`
- `prepTimeMinutes`

### RecipeIngredient

Connects recipes to required ingredients.

Suggested fields:

- `id`
- `recipeId`
- `ingredientId`
- `quantity`
- `unit`

### GroceryListItem

Represents an ingredient the user wants to buy.

Suggested fields:

- `id`
- `userId`
- `ingredientId`
- `recipeId`
- `quantity`
- `unit`
- `checked`

### RecipeRating

Stores user feedback for future personalization.

Suggested fields:

- `id`
- `userId`
- `recipeId`
- `rating`
- `notes`
- `createdAt`

### SavedRecipe

Stores recipes the user wants to keep.

Suggested fields:

- `id`
- `userId`
- `recipeId`
- `createdAt`

### InteractionEvent

Stores future recommendation signals.

Suggested fields:

- `id`
- `userId`
- `recipeId`
- `eventType`
- `createdAt`

Example event types: `VIEWED`, `SAVED`, `RATED`, `ADDED_TO_GROCERY_LIST`, `COOKED`.

## Main Relationships

- A user has one profile.
- A user has many pantry items.
- A pantry item references one ingredient.
- A recipe has many recipe ingredients.
- A recipe ingredient references one ingredient.
- A user has many grocery list items.
- A user can rate many recipes.
- A user can save many recipes.
- Interaction events connect users to recipes over time.

## What To Build First

Start with a small database model:

1. `Ingredient`
2. `Recipe`
3. `RecipeIngredient`

After those work, add pantry items and grocery list items.
