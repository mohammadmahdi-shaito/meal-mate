package com.mealmate.backend.grocery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryRepository
        extends JpaRepository<GroceryItem, Long> {
}