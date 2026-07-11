package com.mealmate.backend.pantry;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pantry")
public class PantryController {
    
    @GetMapping
    public String getPantryItems() {
        return "Pantry endpoint is working!";
    }

    @PostMapping
    public String addPantryItem() {
        return "Add pantry item endpoint is working!";
    }

    @PutMapping("/{id}")
    public String updatePantryItem(@PathVariable Long id) {
        return "Update pantry item endpoint is working for item with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deletePantryItem(@PathVariable Long id) {
        return "Delete pantry item endpoint is working for item with ID: " + id;
    }
}