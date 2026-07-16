package com.mealmate.backend.pantry;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PantryService {
    
    private final PantryRepository pantryRepository;

    public PantryService(PantryRepository pantryRepository) {
        this.pantryRepository = pantryRepository;
    }

    public List<PantryItem> getAllPantryItems() {
        return pantryRepository.findAll();
    }
}