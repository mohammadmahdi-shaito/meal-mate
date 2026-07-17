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

    public PantryItem addPantryItem(PantryItem pantryItem) {
        return pantryRepository.save(pantryItem);
    }

    public PantryItem updatePantryItem(
        Long id,
        PantryItem updatedItem
) {
    PantryItem existingItem = pantryRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Pantry item not found")
            );

    existingItem.setName(updatedItem.getName());
    existingItem.setQuantity(updatedItem.getQuantity());
    existingItem.setUnit(updatedItem.getUnit());

    return pantryRepository.save(existingItem);
}

    public void deletePantryItem(Long id) {
        pantryRepository.deleteById(id);
    }
}