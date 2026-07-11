package com.mealmate.backend.recipe;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/recipes")
public class recipeController {

    @GetMapping("/api/recipes")
    public String getRecipes() {
        return "Recipes endpoint is working!";
    }
    
    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id) {
        return "Get recipe by ID endpoint is working for recipe with ID: " + id;
    }

    @PostMapping("/api/recipes")
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
