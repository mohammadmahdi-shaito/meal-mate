package com.mealmate.backend.recommendation.dto;
import java.util.List;

public record RecommendationResponse(
        Long recipeId,
        String recipeName,
        int availableIngredients,
        int totalIngredients,
        double matchPercentage,
        List<String> missingIngredients
) {
}