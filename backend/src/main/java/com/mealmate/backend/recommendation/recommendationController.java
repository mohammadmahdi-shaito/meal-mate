package com.mealmate.backend.recommendation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealmate.backend.recommendation.dto.RecommendationResponse;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService
    ) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<RecommendationResponse> getRecommendations() {
        return recommendationService.getRecommendations();
    }

    @PostMapping("/{recipeId}/add-missing-to-grocery")
    public List<String> addMissingIngredientsToGrocery(
            @PathVariable Long recipeId
    ) {
        return recommendationService
                .addMissingIngredientsToGrocery(recipeId);
    }
}