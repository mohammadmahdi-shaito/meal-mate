package com.mealmate.backend.pantry;

import java.util.List;
public class PantryService {
    
    private final PantryRepository pantryRepository;

    public PantryService(PantryRepository pantryRepository) {
        this.pantryRepository = pantryRepository;
    }

    public List<PantryItem> getAllPantryItems() {
        return pantryRepository.findAll();
    }
}