package com.mealmate.backend.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}