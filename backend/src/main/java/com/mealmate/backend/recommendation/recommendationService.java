package com.mealmate.backend.recommendation;

import java.util.List;
import com.mealmate.backend.pantry.PantryItem;
import com.mealmate.backend.pantry.PantryService;

public class RecommendationService {
    
    private final PantryService pantryService;

    public RecommendationService(PantryService pantryService) {
        this.pantryService = pantryService;
    }

    public String getRecommendations(){
        List<PantryItem> pantryItems = pantryService.getAllPantryItems();

        return "Recommendatoins based on " + 
        pantryItems.size() + 
        " items in your pantry.";
    }
}
