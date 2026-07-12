package com.mealmate.backend.recommendation.dto;
import java.util.List;

public record RecommendationResponse(
        Long recipeId,
        String recipeName,
        double score,
        double matchPercentage,
        int availableIngredients,
        int totalIngredients,
        List<String> missingIngredients
) {
}