package com.mealmate.backend.pantry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PantryRepository extends JpaRepository<PantryItem, Long> {
}