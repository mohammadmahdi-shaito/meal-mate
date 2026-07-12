package com.mealmate.backend.pantry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryRepository extends JpaRepository<PantryItem, Long> {
}