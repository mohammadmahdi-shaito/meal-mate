package com.mealmate.backend.config;

import com.mealmate.backend.recipe.Recipe;
import com.mealmate.backend.recipe.RecipeIngredient;
import com.mealmate.backend.recipe.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeDataSeeder implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    public RecipeDataSeeder(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) {

        // Prevent recipes from being inserted again after every restart
        if (recipeRepository.count() > 0) {
            return;
        }

        Recipe chickenRiceBowl = new Recipe(
                "Chicken Rice Bowl",
                "Mediterranean",
                600,
                45,
                60,
                18,
                "Cook the rice. Grill the chicken. Chop the vegetables. "
                        + "Place everything in a bowl and serve."
        );

        chickenRiceBowl.addIngredient(
                new RecipeIngredient("chicken breast", 300.0, "grams")
        );
        chickenRiceBowl.addIngredient(
                new RecipeIngredient("rice", 150.0, "grams")
        );
        chickenRiceBowl.addIngredient(
                new RecipeIngredient("tomato", 1.0, "piece")
        );
        chickenRiceBowl.addIngredient(
                new RecipeIngredient("onion", 0.5, "piece")
        );

        Recipe lentilSoup = new Recipe(
                "Lebanese Lentil Soup",
                "Lebanese",
                350,
                18,
                55,
                7,
                "Cook the lentils with onion and spices. Blend partially "
                        + "and serve with lemon."
        );

        lentilSoup.addIngredient(
                new RecipeIngredient("lentils", 200.0, "grams")
        );
        lentilSoup.addIngredient(
                new RecipeIngredient("onion", 1.0, "piece")
        );
        lentilSoup.addIngredient(
                new RecipeIngredient("cumin", 1.0, "teaspoon")
        );
        lentilSoup.addIngredient(
                new RecipeIngredient("lemon", 1.0, "piece")
        );

        Recipe tomatoPasta = new Recipe(
                "Healthy Tomato Pasta",
                "Italian",
                500,
                20,
                75,
                12,
                "Boil the pasta. Cook tomato and garlic into a sauce. "
                        + "Combine and serve."
        );

        tomatoPasta.addIngredient(
                new RecipeIngredient("whole wheat pasta", 150.0, "grams")
        );
        tomatoPasta.addIngredient(
                new RecipeIngredient("tomato", 2.0, "pieces")
        );
        tomatoPasta.addIngredient(
                new RecipeIngredient("garlic", 2.0, "cloves")
        );
        tomatoPasta.addIngredient(
                new RecipeIngredient("olive oil", 1.0, "tablespoon")
        );

        recipeRepository.saveAll(
                List.of(
                        chickenRiceBowl,
                        lentilSoup,
                        tomatoPasta
                )
        );

        System.out.println("Initial recipes added to the database.");
    }
}