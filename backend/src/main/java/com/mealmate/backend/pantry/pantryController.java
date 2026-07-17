package com.mealmate.backend.pantry;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pantry")
public class PantryController {
    
    private final PantryService pantryService;

    public PantryController(PantryService pantryService) {
        this.pantryService = pantryService;
    }

    @GetMapping
    public Iterable<PantryItem> getPantryItems() {
        return pantryService.getAllPantryItems();
    }

    @PostMapping
    public PantryItem addPantryItem(@RequestBody PantryItem pantryItem) {
        return pantryService.addPantryItem(pantryItem);
    }

   @GetMapping("/{id}")
public PantryItem getPantryItemById(@PathVariable Long id) {
    return pantryService.getAllPantryItems()
            .stream()
            .filter(item -> item.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pantry item not found"
            ));
}

    @PutMapping("/{id}") 
    public PantryItem updatePantryItem(@PathVariable Long id, @RequestBody PantryItem updatedItem) {
        return pantryService.updatePantryItem(id, updatedItem);
    }

    @DeleteMapping("/{id}")
    public void deletePantryItem(@PathVariable Long id) {
        pantryService.deletePantryItem(id);
    }
}