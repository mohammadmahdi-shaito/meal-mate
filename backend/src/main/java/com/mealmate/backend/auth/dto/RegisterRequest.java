package com.mealmate.backend.auth.dto;

public record RegisterRequest(
        String name,
        String email,
        String password,
        Integer calorieGoal,
        Integer proteinGoal
) {
}
