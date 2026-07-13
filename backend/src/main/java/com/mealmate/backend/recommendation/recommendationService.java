package com.mealmate.backend.recommendation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealmate.backend.grocery.GroceryService;
import com.mealmate.backend.pantry.PantryItem;
import com.mealmate.backend.pantry.PantryService;

@Service
public class RecommendationService {

    private final PantryService pantryService;
    private final GroceryService groceryService;

    public RecommendationService(
            PantryService pantryService,
            GroceryService groceryService
    ) {
        this.pantryService = pantryService;
        this.groceryService = groceryService;
    }

    private List<String> findMissingIngredients(Long recipeId) {
        return List.of("onion", "tomato");
    }

    public void addMissingIngredientsToGrocery(Long recipeId) {
        List<String> missingIngredients =
                findMissingIngredients(recipeId);

        groceryService.addIngredients(missingIngredients);
    }

    public String getRecommendations() {
        List<PantryItem> pantryItems =
                pantryService.getAllPantryItems();

        return "Recommendations based on "
                + pantryItems.size()
                + " items in your pantry.";
    }
}