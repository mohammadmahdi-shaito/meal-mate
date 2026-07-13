package com.mealmate.backend.grocery;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public void addIngredients(List<String> ingredientNames) {
        for (String name : ingredientNames) {
            GroceryItem item = new GroceryItem();
            item.setName(name);

            groceryRepository.save(item);
        }
    }
}