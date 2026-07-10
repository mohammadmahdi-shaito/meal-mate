package com.mealmate.backend.pantry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface pantryRepository extends JpaRepository<pantryItem, Long> {
}