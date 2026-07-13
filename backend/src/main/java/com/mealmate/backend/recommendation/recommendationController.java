package com.mealmate.backend.recommendation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/recommendation")
public class RecommendationController {
    
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public String getRecommendations(){
        return recommendationService.getRecommendations();
    }

    @PostMapping("/{recipeId}/add-missing-to-grocery")
    public String addMissingIngredients(@PathVariable Long recipeId) {
        recommendationService.addMissingIngredientsToGrocery(recipeId);
        return "Missing ingredients added to grocery list";
    }
}
