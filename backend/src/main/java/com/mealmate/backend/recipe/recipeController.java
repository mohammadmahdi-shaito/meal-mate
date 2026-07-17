package com.mealmate.backend.recipe;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    
    private final RecipeRepository recipeRepository;
    
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public Iterable<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id) {
        return "Get recipe by ID endpoint is working for recipe with ID: " + id;
    }

    @PostMapping
    public String addRecipe() {
        return "Add recipe endpoint is working!";
    }

    @PutMapping("/{id}")
    public String updateRecipe(@PathVariable Long id) {
        return "Update recipe endpoint is working for recipe with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        return "Delete recipe endpoint is working for recipe with ID: " + id;
    }

}
