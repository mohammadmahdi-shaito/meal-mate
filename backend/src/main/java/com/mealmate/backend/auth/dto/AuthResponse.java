package com.mealmate.backend.auth.dto;

public record AuthResponse(
        String token,
        String message
) {
}
