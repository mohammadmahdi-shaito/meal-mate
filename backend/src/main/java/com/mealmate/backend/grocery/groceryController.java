package com.mealmate.backend.grocery;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grocery")
public class groceryController {
    
    @GetMapping("/items")
    public String getGroceryItems() {
        return "Grocery endpoint is working!";
    }

    @PostMapping("/items")
    public String addGroceryItem() {
        return "Add grocery item endpoint is working!";
    }

    @PutMapping("/{id}")
    public String updateGroceryItem(@PathVariable Long id) {
        return "Update grocery item endpoint is working for item with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteGroceryItem(@PathVariable Long id) {
        return "Delete grocery item endpoint is working for item with ID: " + id;
    }
}
