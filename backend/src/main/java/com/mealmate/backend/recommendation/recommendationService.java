package com.mealmate.backend.recommendation;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mealmate.backend.grocery.GroceryService;
import com.mealmate.backend.pantry.PantryItem;
import com.mealmate.backend.pantry.PantryService;
import com.mealmate.backend.recipe.Recipe;
import com.mealmate.backend.recipe.RecipeIngredient;
import com.mealmate.backend.recipe.RecipeRepository;
import com.mealmate.backend.recommendation.dto.RecommendationResponse;

@Service
public class RecommendationService {

    private final PantryService pantryService;
    private final RecipeRepository recipeRepository;
    private final GroceryService groceryService;

    public RecommendationService(
            PantryService pantryService,
            RecipeRepository recipeRepository,
            GroceryService groceryService
    ) {
        this.pantryService = pantryService;
        this.recipeRepository = recipeRepository;
        this.groceryService = groceryService;
    }

    /*
     * Calculate recommendations whenever they are requested.
     * The scores are not stored in the database.
     */
    @Transactional(readOnly = true)
    public List<RecommendationResponse> getRecommendations() {

        Set<String> pantryIngredientNames = getNormalizedPantryNames();

        return recipeRepository.findAll()
                .stream()
                .map(recipe ->
                        createRecommendation(
                                recipe,
                                pantryIngredientNames
                        )
                )
                .sorted(
                        Comparator.comparingDouble(
                                RecommendationResponse::matchPercentage
                        ).reversed()
                )
                .toList();
    }

    /*
     * Find the missing ingredients for one recipe,
     * then save them through GroceryService.
     */
    @Transactional
    public List<String> addMissingIngredientsToGrocery(
            Long recipeId
    ) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Recipe not found"
                        )
                );

        Set<String> pantryIngredientNames =
                getNormalizedPantryNames();

        List<String> missingIngredients =
                findMissingIngredients(
                        recipe,
                        pantryIngredientNames
                );

        groceryService.addIngredients(missingIngredients);

        return missingIngredients;
    }

    private RecommendationResponse createRecommendation(
            Recipe recipe,
            Set<String> pantryIngredientNames
    ) {
        List<RecipeIngredient> requiredIngredients =
                recipe.getIngredients();

        if (requiredIngredients == null
                || requiredIngredients.isEmpty()) {

            return new RecommendationResponse(
                    recipe.getId(),
                    recipe.getName(),
                    0,
                    0,
                    0.0,
                    List.of()
            );
        }

        List<String> missingIngredients =
                findMissingIngredients(
                        recipe,
                        pantryIngredientNames
                );

        int totalIngredients =
                requiredIngredients.size();

        int matchedIngredients =
                totalIngredients - missingIngredients.size();

        double matchPercentage =
                matchedIngredients * 100.0 / totalIngredients;

        // Keep one digit after the decimal point
        matchPercentage =
                Math.round(matchPercentage * 10.0) / 10.0;

        return new RecommendationResponse(
                recipe.getId(),
                recipe.getName(),
                matchedIngredients,
                totalIngredients,
                matchPercentage,
                missingIngredients
        );
    }

    private List<String> findMissingIngredients(
            Recipe recipe,
            Set<String> pantryIngredientNames
    ) {
        return recipe.getIngredients()
                .stream()
                .map(RecipeIngredient::getIngredientName)
                .filter(Objects::nonNull)
                .filter(ingredientName ->
                        !pantryIngredientNames.contains(
                                normalize(ingredientName)
                        )
                )
                .distinct()
                .toList();
    }

    private Set<String> getNormalizedPantryNames() {
        return pantryService.getAllPantryItems()
                .stream()
                .map(PantryItem::getName)
                .filter(Objects::nonNull)
                .map(this::normalize)
                .collect(Collectors.toSet());
    }

    private String normalize(String ingredientName) {
        return ingredientName
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", " ");
    }
}